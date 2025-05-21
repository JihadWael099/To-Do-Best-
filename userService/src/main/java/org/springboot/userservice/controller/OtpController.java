package org.springboot.userservice.controller;
import org.springboot.userservice.service.EmailService;
import org.springboot.userservice.service.OtpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/otp")
public class OtpController {
    private final OtpService otpService;
    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }
    @GetMapping("/send")
    public String sendOtpEmail(@RequestParam String username) {
        otpService.generateAndSendOtp(username);
        return "OTP email sent successfully!";
    }
}
