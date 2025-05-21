package org.springboot.userservice.controller;
import jakarta.servlet.http.HttpServletRequest;
import org.springboot.userservice.entity.Users;
import org.springboot.userservice.service.JwtService;
import org.springboot.userservice.service.OtpService;
import org.springboot.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public String activateUser(@RequestParam String username, @RequestHeader("otp") String otp) {
       return otpService.activateUser(username, otp);
    }
    @PostMapping("/forgetPassword")
    public String forgetPassword(HttpServletRequest request) {
        return userService.forgetPassword(request);
    }
}
