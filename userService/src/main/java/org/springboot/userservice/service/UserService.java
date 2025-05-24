package org.springboot.userservice.service;
import jakarta.servlet.http.HttpServletRequest;
import org.springboot.userservice.entity.OTP;
import org.springboot.userservice.entity.Users;
import org.springboot.userservice.exceptions.UserAlreadyExistsException;
import org.springboot.userservice.exceptions.UserNotFoundException;
import org.springboot.userservice.repository.OtpRepo;
import org.springboot.userservice.repository.UserRepo;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
@Service
public class UserService {
    private final UserRepo userRepo;
    private final JwtService jwtService;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepo userRepo, OtpRepo otpRepo, JwtService jwtService, @Lazy OtpService otpService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.jwtService = jwtService;
        this.otpService = otpService;
        this.passwordEncoder = passwordEncoder;
    }
    public Optional<Users> getUserByName(String username) {
        return userRepo.findByUsername(username);
    }
    public Users getUserById(int id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }
    public Users addUser(Users newUser) {
        Optional<Users> existingUser = userRepo.findByUsername(newUser.getUsername());

        if (existingUser.isPresent()) {
            if (!existingUser.get().isEnabled()) {
                throw new IllegalArgumentException("User must be verified");
            }
            throw new UserAlreadyExistsException("Username is already taken: " + newUser.getUsername());
        }

        return userRepo.save(newUser);
    }

    public void deleteUser(int id) {
        Users user = getUserById(id);
        userRepo.delete(user);
    }
    public Users updateUser(int id, Users updatedUser) {
        Users existingUser = getUserById(id);
        if (updatedUser.getUsername() != null) {
            existingUser.setUsername(updatedUser.getUsername());
        }
        if (updatedUser.getEmail() != null) {
            existingUser.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getPassword() != null) {
            existingUser.setPassword(updatedUser.getPassword());
        }
        return userRepo.save(existingUser);
    }
    public String forgetPassword(HttpServletRequest request) {
        String token = jwtService.getTokenFromHeader(request);
        if (token == null) {
            return"Token is missing";
        }
        if (jwtService.validateToken(token)) {
            String username = jwtService.getUsernameFromToken(token);
            Optional<Users> userOpt = getUserByName(username);
            if (userOpt.isEmpty()) {
                return "User not found";
            }
            otpService.generateAndSendOtp(username);
            return "OTP sent to registered email";
        }
        else return "not valid token";
    }
    public String changePassword(HttpServletRequest request, String providedOtp, String newPassword) {
        String token = jwtService.getTokenFromHeader(request);
        if (token == null) {
            return "Token is missing";
        }
        if (!jwtService.validateToken(token)) {
            return "Invalid token";
        }
        String username = jwtService.getUsernameFromToken(token);
        Optional<Users> userOpt = getUserByName(username);
        if (userOpt.isEmpty()) {
            return "User not found";
        }
        Users user = userOpt.get();

        Optional<OTP> otpOpt = otpService.findTopByUserOrderByIdDesc(user);
        if (otpOpt.isEmpty()) {
            return "No OTP found for this user";
        }
        OTP otp = otpOpt.get();
        if (!otp.getOtp().equals(providedOtp)) {
            return "Invalid OTP";
        }
        if (otp.isExpired()) {
            otpService.deleteOtp(otp);
            return "OTP has expired";
        }
        String encryptedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encryptedPassword);
        otpService.deleteOtp(otp);
        return "Password changed successfully";
    }

}
