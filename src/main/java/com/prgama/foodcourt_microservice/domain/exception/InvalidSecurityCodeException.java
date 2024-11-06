package com.prgama.foodcourt_microservice.domain.exception;

public class InvalidSecurityCodeException extends RuntimeException {
    public InvalidSecurityCodeException(String message) {
        super(message);
    }
}
