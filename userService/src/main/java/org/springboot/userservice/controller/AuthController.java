package org.springboot.userservice.controller;

import jakarta.validation.Valid;
import org.springboot.userservice.dto.LoginDto;
import org.springboot.userservice.entity.Users;
import org.springboot.userservice.service.AuthService;
import org.springboot.userservice.service.JwtService;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtService jwtService;
    public AuthController(AuthService authService, JwtService jwtService) {
        this.authService = authService;
        this.jwtService = jwtService;
    }
    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginDto loginDto){
        return authService.login(loginDto);
    }
    @PostMapping ("/register")
    public String register(@Valid @RequestBody Users user){
        return authService.register(user);
    }
    @GetMapping("/tokenValidation/{token}")
    public boolean validToken(@PathVariable String token){
        return jwtService.validateToken(token);
    }
}
