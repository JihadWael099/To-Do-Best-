package org.springboot.userservice.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springboot.userservice.repository.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    .withZone(ZoneId.systemDefault());

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleUserNotFoundException(UserNotFoundException ex, HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.NOT_FOUND.value(),
                "USER_NOT_FOUND",
                ex.getMessage(),
                request.getRequestURI(),
                formatter.format(Instant.now())
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleUserAlreadyExistsException(UserAlreadyExistsException ex, HttpServletRequest request) {
        ExceptionResponse response = new ExceptionResponse(
                HttpStatus.CONFLICT.value(),
                "USER_ALREADY_EXISTS",
                ex.getMessage(),
                request.getRequestURI(),
                formatter.format(Instant.now())
        );
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
    


}
