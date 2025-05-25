package org.springboot.userservice.service;

import org.springboot.userservice.dto.UserDto;
import org.springboot.userservice.entity.Users;
import org.springboot.userservice.exceptions.UserAlreadyExistsException;
import org.springboot.userservice.exceptions.UserNotFoundException;
import org.springboot.userservice.repository.OtpRepo;
import org.springboot.userservice.repository.UserRepo;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepo userRepo, OtpRepo otpRepo, @Lazy OtpService otpService, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
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

    public String forgetPassword(String username) {
        Optional<Users> userOpt = getUserByName(username);
        if (userOpt.isEmpty()) {
            return "User not found";
        }
        otpService.generateAndSendOtp(username);
        return "OTP sent to registered email";
    }

    public String resetPasswordWithOtp(String username, String otpCode, String newPassword) {
        Optional<Users> userOpt = getUserByName(username);
        if (userOpt.isEmpty()) {
            return "User not found";
        }
        boolean isOtpValid = otpService.verifyOtp(username, otpCode);
        if (!isOtpValid) {
            return "Invalid or expired OTP";
        }

        Users user = userOpt.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
        return "Password reset successful";
    }


    public UserDto getUserDtoById(int id) {
        Optional<Users> users = userRepo.findById(id);
        if (!users.isPresent()) {
            throw new UserNotFoundException("user not found");
        }
        UserDto userDto = new UserDto();
        userDto.setEmail(users.get().getEmail());
        userDto.setUsername(users.get().getUsername());
        userDto.setId(users.get().getId());
        return userDto;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Users> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        }
        throw new UsernameNotFoundException("User not found: " + username);
    }

    public String changePassword(String username, String oldPassword, String newPassword, String otpCode) {
        Optional<Users> userOpt = getUserByName(username);
        if (userOpt.isEmpty()) {
            return "User not found";
        }
        Users user = userOpt.get();
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return "Old password is incorrect";
        }
        boolean otpValid = otpService.verifyOtp(username, otpCode);
        if (!otpValid) {
            return "Invalid or expired OTP";
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
        return "Password changed successfully";
    }


}
