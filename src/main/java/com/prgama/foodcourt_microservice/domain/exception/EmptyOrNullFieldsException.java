package com.prgama.foodcourt_microservice.domain.exception;

public class EmptyOrNullFieldsException extends RuntimeException {
    public EmptyOrNullFieldsException(String message) {
        super(message);
    }
}
