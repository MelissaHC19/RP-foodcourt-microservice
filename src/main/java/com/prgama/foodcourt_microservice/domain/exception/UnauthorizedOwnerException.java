package com.prgama.foodcourt_microservice.domain.exception;

public class UnauthorizedOwnerException extends RuntimeException {
    public UnauthorizedOwnerException(String message) {
        super(message);
    }
}
