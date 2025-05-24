package org.springboot.userservice.controller;
import jakarta.servlet.http.HttpServletRequest;
import org.springboot.userservice.dto.UserDto;
import org.springboot.userservice.exceptions.UserNotFoundException;
import org.springboot.userservice.service.OtpService;
import org.springboot.userservice.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;
    private final OtpService otpService;
    public UserController(UserService userService, OtpService otpService) {
        this.userService = userService;
        this.otpService = otpService;
    }
    @GetMapping("/activate")
    public ResponseEntity<String> activateUser(
            @RequestParam String username,
            @RequestHeader("otp") String otpCode) {
        try {
            String result = otpService.activateUser(username, otpCode);
            return ResponseEntity.ok(result);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Activation failed: " + e.getMessage());
        }
    }
    @PostMapping("/forgetPassword")
    public String forgetPassword(HttpServletRequest request) {
        return userService.forgetPassword(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") int id){
        return ResponseEntity.ok(userService.getUserDtoById(id));
    }
}
