package org.springboot.userservice.controller;
import org.springboot.userservice.service.EmailService;
import org.springboot.userservice.service.OtpService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/otp")
public class OtpController {
    private final OtpService otpService;
    public OtpController(OtpService otpService) {
        this.otpService = otpService;
    }
    @GetMapping("/send")
    public ResponseEntity<String> sendOtpEmail(@RequestParam String username) {
        try {
            if (username == null || username.isEmpty()) {
                return ResponseEntity.badRequest().body("Username is required");
            }
            if (username.length() > 20) {
                return ResponseEntity.badRequest().body("Username too long (max 20 characters");
            }
            otpService.generateAndSendOtp(username);
            return ResponseEntity.ok("OTP email sent successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to send OTP: " + e.getMessage());
        }
    }

    @PostMapping("/api/v1/otp/verify")
    public ResponseEntity<?> verifyOtp(@RequestParam String username, @RequestParam String otp) {
        boolean isValid = otpService.verifyOtp(username, otp);
        if (isValid) {
            return ResponseEntity.ok("OTP verified successfully");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired OTP");
        }
    }
}
