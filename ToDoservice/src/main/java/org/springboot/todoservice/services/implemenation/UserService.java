package org.springboot.todoservice.services.implemenation;
import org.springboot.todoservice.entity.UserDto;
import org.springboot.todoservice.exception.TokenValidationException;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    private final RestTemplate restTemplate;

    public UserService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    String validateUrl = "http://localhost:8081/api/v1/user";
    public UserDto validateToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.trim());
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        System.out.println(headers);

        try {
            ResponseEntity<UserDto> response = restTemplate.exchange(
                    validateUrl,
                    HttpMethod.GET,
                    entity,
                    UserDto.class
            );

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new TokenValidationException("Token validation failed: Unexpected status");
            }

        } catch (HttpClientErrorException | HttpServerErrorException ex) {
            throw new TokenValidationException("Token validation failed: Unauthorized or error from user service");
        } catch (Exception ex) {
            throw new TokenValidationException("Token validation failed: Unexpected error");
        }
    }
}
