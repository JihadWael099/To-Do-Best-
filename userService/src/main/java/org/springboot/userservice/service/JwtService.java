package org.springboot.userservice.service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springboot.userservice.entity.JWT;
import org.springboot.userservice.entity.Users;
import org.springboot.userservice.repository.JwtRepo;
import org.springboot.userservice.util.TokenType;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
@Service
public class JwtService {
    private final static String SECRET_KEY="gehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehad";
    private final static long EXPIRE_DATE=86400000;
    private final JwtParser jwtParser;
    private final SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));


    private final JwtRepo jwtRepo;
    public JwtService( JwtRepo jwtRepo) {
        this.jwtRepo = jwtRepo;
        this.jwtParser = Jwts.parser().setSigningKey(SECRET_KEY);
    }
    public String generateToken(Users users ){
      String token=Jwts.builder()
                .setSubject(users.getUsername())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_DATE))
                .setIssuedAt(new Date())
              .signWith(key, SignatureAlgorithm.HS256)
              .compact();
      saveToken(token,users);
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
    public Claims convertJwtClaims(String token){
        return jwtParser.parseClaimsJws(token).getBody();
    }
    public String getTokenFromHeader(HttpServletRequest request){
        return request.getHeader("Authorization");
    }
    public boolean validateToken(String token) {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expirationDate.before(new Date());
    }


}
