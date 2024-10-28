package com.prgama.foodcourt_microservice.domain.exception;

public class InvalidPageNumberException extends RuntimeException {
    public InvalidPageNumberException(String message) {
        super(message);
    }
}
