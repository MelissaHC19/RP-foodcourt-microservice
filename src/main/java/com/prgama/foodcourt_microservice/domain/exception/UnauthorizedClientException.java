package com.prgama.foodcourt_microservice.domain.exception;

public class UnauthorizedClientException extends RuntimeException {
    public UnauthorizedClientException(String message) {
        super(message);
    }
}
