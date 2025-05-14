package org.springboot.userservice.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  int id;
    @Min(value = 8,message = "provide min 8 character ")
    @Max(value = 20,message = "max is 20 character")
    private String password;

    @NotBlank(message = "provide a valid username")
    private String username;

    @Email(message ="provide valid email")
    @NotBlank(message = "provide a valid email")
    private String email;


    @Column(nullable = false)
    private boolean enable = false;

    @OneToMany(mappedBy = "user")
    private List<OTP> otp;

    @OneToMany(mappedBy = "user")
    private List<JWT> jwt;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Users(int id, String password, String username, String email, boolean enable) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.email = email;
        this.enable = enable;
    }

    public Users() {
    }
}
