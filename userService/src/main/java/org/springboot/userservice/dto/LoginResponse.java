package org.springboot.userservice.dto;

import lombok.Data;

@Data
public class LoginResponse {
    private String username;
    private String token;
}
