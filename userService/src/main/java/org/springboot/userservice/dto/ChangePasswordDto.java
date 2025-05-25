package org.springboot.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class ChangePasswordDto {


    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank(message = "Old password must not be blank")
    @Size(min = 6, message = "Old password must be at least 6 characters")
    private String oldPassword;

    @NotBlank(message = "New password must not be blank")
    @Size(min = 8, message = "New password must be at least 8 characters")
    private String newPassword;

}
