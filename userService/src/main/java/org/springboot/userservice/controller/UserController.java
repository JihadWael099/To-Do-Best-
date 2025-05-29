package org.springboot.userservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springboot.userservice.dto.ChangePasswordDto;
import org.springboot.userservice.dto.LoginDto;
import org.springboot.userservice.dto.UserDto;
import org.springboot.userservice.exceptions.UserNotFoundException;
import org.springboot.userservice.service.OtpService;
import org.springboot.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;
    private final OtpService otpService;

    public UserController(UserService userService, OtpService otpService) {
        this.userService = userService;
        this.otpService = otpService;
    }

    @GetMapping("/activate")
    public ResponseEntity<String> activateUser(
            @RequestParam String username,
            @RequestHeader("otp") String otpCode) {
        try {
            String result = otpService.activateUser(username, otpCode);
            return ResponseEntity.ok(result);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Activation failed: " + e.getMessage());
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDto changePasswordDto,
                                            @RequestHeader("otp") String otp
    ) {
        if (!otpService.verifyOtp(changePasswordDto.getUsername(), otp)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid or expired OTP");
        }
        userService.changePassword(
                changePasswordDto.getUsername(),
                changePasswordDto.getOldPassword(),
                changePasswordDto.getNewPassword(),
                otp);
        return ResponseEntity.ok("Password changed successfully");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestParam String username,
                                                @RequestParam String otpCode,
                                                @RequestParam String newPassword) {
        String result = userService.resetPasswordWithOtp(username, otpCode, newPassword);
        if (result.equals("Password reset successful")) {
            return ResponseEntity.ok(result);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        }
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> forgetPassword(@RequestParam String username) {
        String response = userService.forgetPassword(username);
        if (response.equals("OTP sent to registered email")) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("")
    public ResponseEntity<UserDto> getUserByToken(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(userService.getUserByToken(httpServletRequest));
    }


    @DeleteMapping("")
    public ResponseEntity<String> deleteUser(@RequestBody LoginDto loginDto) {
        userService.deleteUser(loginDto);
        return ResponseEntity.ok("deletes");
    }


}
