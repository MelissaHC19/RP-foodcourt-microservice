package com.prgama.foodcourt_microservice.domain.exception;

public class InvalidNitException extends RuntimeException {
    public InvalidNitException(String message) {
        super(message);
    }
}
