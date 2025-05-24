package org.springboot.userservice.controller;

import jakarta.validation.Valid;
import org.springboot.userservice.dto.LoginDto;
import org.springboot.userservice.entity.Users;
import org.springboot.userservice.service.AuthService;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginDto loginDto){
        return authService.login(loginDto);
    }
    @PostMapping ("/register")
    public String register(@Valid @RequestBody Users user){
        return authService.register(user);
    }
}
