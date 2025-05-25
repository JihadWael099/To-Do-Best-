package org.springboot.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
public class LoginDto {
    @NotBlank(message = "user name is required")
    private String username;
    @NotBlank(message = "password is required")
    private String password;

}
