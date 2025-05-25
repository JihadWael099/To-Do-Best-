package org.springboot.userservice.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "otp can,t be null")
    @Column(nullable = false)
    private String otp;

    @Column(nullable = false)
    private LocalDateTime expiration_time;

    @ManyToOne
    @JoinColumn(name = "users_id", nullable = false)
    private Users user;

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiration_time);
    }

}
