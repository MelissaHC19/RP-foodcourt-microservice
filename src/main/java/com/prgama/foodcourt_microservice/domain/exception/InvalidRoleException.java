package com.prgama.foodcourt_microservice.domain.exception;

public class InvalidRoleException extends RuntimeException {
    public InvalidRoleException(String message) {
        super(message);
    }
}
