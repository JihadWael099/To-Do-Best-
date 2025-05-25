package org.springboot.userservice.service;

import org.springboot.userservice.dto.LoginDto;
import org.springboot.userservice.dto.LoginResponse;
import org.springboot.userservice.entity.Users;
import org.springboot.userservice.exceptions.UserAlreadyExistsException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginDto loginDto) {
        Users users = userService.getUserByName(loginDto.getUsername())
                .orElseThrow(() -> new BadCredentialsException("User not found"));
        if (!passwordEncoder.matches(loginDto.getPassword(), users.getPassword())) {
            throw new BadCredentialsException("Password or username does not match");
        }
        if (!users.isEnable()) {
            throw new IllegalArgumentException("User must be verified");
        }
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(jwtService.generateToken(users));
        loginResponse.setUsername(loginDto.getUsername());
        return loginResponse;
    }

    public String register(Users users) {
        Optional<Users> findUser = userService.getUserByName(users.getUsername());
        if (findUser.isPresent()) {
            throw new UserAlreadyExistsException("User is already registered");
        }
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        users.setEnable(false);
        userService.addUser(users);
        return jwtService.generateToken(users);
    }
}

