package org.springboot.userservice.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.apache.catalina.User;


@Entity
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank(message = "otp can,t be null")
    @Column(nullable = false)
    private String otp;

    @Column(nullable = false)
    private int expiration_time;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id",nullable = false)
    private Users user;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public int getExpiration_time() {
        return expiration_time;
    }

    public void setExpiration_time(int expiration_time) {
        this.expiration_time = expiration_time;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public OTP(int id, String otp, int expiration_time, Users user) {
        this.id = id;
        this.otp = otp;
        this.expiration_time = expiration_time;
        this.user = user;
    }

    public OTP() {
    }
}
