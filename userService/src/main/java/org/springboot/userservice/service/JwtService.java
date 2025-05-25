package org.springboot.userservice.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springboot.userservice.entity.JWT;
import org.springboot.userservice.entity.Users;
import org.springboot.userservice.repository.JwtRepo;
import org.springboot.userservice.repository.UserRepo;
import org.springboot.userservice.util.TokenType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class JwtService {

    private static final String SECRET_KEY = "gehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehad";
    private static final long EXPIRE_DATE = 86400000;
    private final JwtParser jwtParser;
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    private final JwtRepo jwtRepo;
    private final UserRepo userRepo;
    private static final Logger logger = LoggerFactory.getLogger(JwtService.class);

    public JwtService(JwtRepo jwtRepo, UserRepo userRepo) {
        this.jwtRepo = jwtRepo;
        this.userRepo = userRepo;
        this.jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
    }

    public String generateToken(Users users) {
        String token = Jwts.builder()
                .setSubject(users.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DATE))
                .setIssuedAt(new Date())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        saveToken(token, users);
        return token;
    }

    private void saveToken(String tokenValue, Users user) {
        JWT jwt = new JWT();
        jwt.setToken(tokenValue);
        jwt.setUser(user);
        jwt.setCreated_at(LocalDateTime.now());
        jwt.setExpiration_date(LocalDateTime.now().plus(Duration.ofMillis(EXPIRE_DATE)));
        jwt.setType(TokenType.JWT);
        jwtRepo.save(jwt);
    }

    public Claims convertJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String getTokenFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    @Transactional
    public boolean validateToken(String token) {
        try {
            jwtParser.parseClaimsJws(token);
            boolean exists = jwtRepo.existsByToken(token);
            logger.debug("Token exists in database: {}", exists);
            return exists;
        } catch (JwtException | IllegalArgumentException e) {
            logger.error("Invalid JWT: {}", e.getMessage(), e);
            return false;
        }
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expirationDate.before(new Date());
    }

    public String getUsernameFromToken(String token) {
        return jwtParser.parseClaimsJws(token).getBody().getSubject();
    }
}