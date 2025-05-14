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
}
