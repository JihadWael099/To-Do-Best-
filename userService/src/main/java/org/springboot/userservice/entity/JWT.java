package org.springboot.userservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.springboot.userservice.util.TokenType;

import java.time.LocalDateTime;

@Entity
public class JWT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotBlank(message = "token can,t be null")
    private String token;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users user;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_at;


    @Column(updatable = false)
    private LocalDateTime expiration_date;

    @Enumerated(EnumType.STRING)
    private TokenType type;


}
