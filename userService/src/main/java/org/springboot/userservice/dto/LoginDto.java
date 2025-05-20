package org.springboot.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.validation.annotation.Validated;

@Validated
public class LoginDto {
    @NotBlank(message = "user name is required")
    private String username;
    @NotBlank(message = "password is required")
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginDto() {
    }
}
