package org.springboot.userservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springboot.userservice.util.TokenType;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class JWT {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    @NotBlank(message = "token can't be null")
    @Column(nullable = false, unique = true)
    private String token;

    @ManyToOne
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


}
