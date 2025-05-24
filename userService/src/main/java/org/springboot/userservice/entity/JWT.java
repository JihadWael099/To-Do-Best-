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


    @NotBlank(message = "token can't be null")
    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "users_id")
    private Users user;


    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime created_at;


    @Column(updatable = false)
    private LocalDateTime expiration_date;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TokenType type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(LocalDateTime expiration_date) {
        this.expiration_date = expiration_date;
    }

    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public JWT(int id, String token, Users user, LocalDateTime created_at, LocalDateTime expiration_date, TokenType type) {
        this.id = id;
        this.token = token;
        this.user = user;
        this.created_at = created_at;
        this.expiration_date = expiration_date;
        this.type = type;
    }

    public JWT() {
    }
}
