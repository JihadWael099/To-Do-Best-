package org.springboot.userservice.service;

import ch.qos.logback.core.subst.Token;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springboot.userservice.entity.JWT;
import org.springboot.userservice.entity.Users;
import org.springboot.userservice.repository.JwtRepo;
import org.springboot.userservice.util.TokenType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;

@Component
public class JwtService {

    private final static String SECRET_KEY="gehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehadgehad";
    private final static long EXPIRE_DATE=86400000;
    private final JwtParser jwtParser;

    private final JwtRepo jwtRepo;
    public JwtService(JwtRepo jwtRepo) {
        this.jwtRepo = jwtRepo;
        this.jwtParser = Jwts.parser().setSigningKey(SECRET_KEY);
    }

    public String generateToken(Users users , Map<String,Object>claims){
      String token=Jwts.builder()
                .setClaims(claims)
                .setSubject(users.getEmail())
                .setExpiration(new Date(System.currentTimeMillis()+EXPIRE_DATE))
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.ES256,SECRET_KEY)
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
    private Claims convertJwtClaims(String token){
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public String getTokenFromHeader(HttpServletRequest request){
        return request.getHeader("Authorization");
    }

    public boolean validateToken(String token) {

            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;

    }

    public boolean isTokenExpired(String token) {
        Date expirationDate = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expirationDate.before(new Date());
    }


}
