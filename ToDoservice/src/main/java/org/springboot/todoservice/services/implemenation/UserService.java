package org.springboot.todoservice.services.implemenation;
import org.springboot.todoservice.entity.UserDto;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    private RestTemplate restTemplate;
    private final String userUrl = "http://localhost:8081/api/v1/user/{id}";
    private final String validateUrl = "http://localhost:8081/api/v1/user/checkToken";

    public boolean validateToken(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        try
        {
            ResponseEntity<String> response = restTemplate.exchange(
                    validateUrl,
                    HttpMethod.GET,
                    entity,
                    String.class
            );
            return response.getStatusCode() == HttpStatus.OK;
        }
        catch (Exception ex) {
            return false;
        }
    }

    public UserDto getUserById(int id, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<UserDto> response = restTemplate.exchange(
                userUrl,
                HttpMethod.GET,
                entity,
                UserDto.class,
                id
        );
        return response.getBody();
    }
}
