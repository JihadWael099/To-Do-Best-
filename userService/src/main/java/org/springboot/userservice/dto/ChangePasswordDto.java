package org.springboot.userservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangePasswordDto {


    @NotBlank(message = "Username must not be blank")
    private String username;

    @NotBlank(message = "Old password must not be blank")
    @Size(min = 6, message = "Old password must be at least 6 characters")
    private String oldPassword;

    @NotBlank(message = "New password must not be blank")
    @Size(min = 8, message = "New password must be at least 8 characters")
    private String newPassword;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public ChangePasswordDto(String username, String oldPassword, String newPassword) {
        this.username = username;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public ChangePasswordDto() {
    }
}
