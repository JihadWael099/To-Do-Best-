package org.springboot.userservice.dto;

import lombok.Data;

@Data
public class UserDto {
    private int id;
    private String username;
    private String email;
}
