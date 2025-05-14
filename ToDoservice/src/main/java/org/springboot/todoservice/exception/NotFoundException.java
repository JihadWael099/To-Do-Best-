package org.springboot.todoservice.exception;

public class NotFoundException extends Exception{
    public NotFoundException(String message) {
        super(message);
    }
}
