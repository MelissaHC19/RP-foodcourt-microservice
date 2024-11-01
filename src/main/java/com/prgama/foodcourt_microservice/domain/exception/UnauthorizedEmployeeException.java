package com.prgama.foodcourt_microservice.domain.exception;

public class UnauthorizedEmployeeException extends RuntimeException {
    public UnauthorizedEmployeeException(String message) {
        super(message);
    }
}
