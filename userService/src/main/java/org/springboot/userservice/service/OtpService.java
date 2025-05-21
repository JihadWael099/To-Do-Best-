package org.springboot.userservice.service;
import jakarta.servlet.http.HttpServletRequest;
import org.springboot.userservice.entity.OTP;
import org.springboot.userservice.entity.Users;
import org.springboot.userservice.exceptions.UserNotFoundException;
import org.springboot.userservice.repository.OtpRepo;
import org.springboot.userservice.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;
@Service
public class OtpService {

    private final UserService userService;
    private final  OtpRepo otpRepository;
    private final EmailService emailService;
    private final UserRepo userRepo;
    public OtpService(UserService userService, OtpRepo otpRepository, EmailService emailService, UserRepo userRepo) {
        this.userService = userService;
        this.otpRepository = otpRepository;
        this.emailService = emailService;
        this.userRepo = userRepo;
    }

    public void generateAndSendOtp(String username) {
        Optional<Users> user = userService.getUserByName(username);
        if(user.isPresent()) {
            String otpCode = String.format("%06d", new Random().nextInt(999999));
            OTP otp = new OTP();
            otp.setOtp(otpCode);
            otp.setExpiration_time(LocalDateTime.now().plusMinutes(5));
            otp.setUser(user.get());
            otpRepository.save(otp);
            emailService.sendOtpEmail(user.get().getEmail(), otpCode);
        }
        else  throw new UserNotFoundException("user with name " + username +"not found");
    }
    public String activateUser(String username, String otpCode) {
        Optional<Users> findUser = userService.getUserByName(username);
        if (findUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        Optional<OTP> otpOpt =  otpRepository.findByUserAndOtp(findUser.get(), otpCode);
        if (otpOpt.isEmpty()) {
            return "Invalid OTP";
        }
        OTP otp = otpOpt.get();
        if (otp.isExpired()) {
            otpRepository.delete(otp);
            return "OTP expired";
        }
        findUser.get().setEnable(true);
        userRepo.save(findUser.get());
        otpRepository.delete(otp);
        return "User is activated";
    }
    public Optional<OTP> findTopByUserOrderByIdDesc(Users user){
        return otpRepository.findTopByUserOrderByIdDesc(user);
    }
    public void deleteOtp(OTP otp){
        otpRepository.delete(otp);
    }




}
