package com.prgama.foodcourt_microservice.domain.exception;

public class HasProcessingOrderException extends RuntimeException {
    public HasProcessingOrderException(String message) {
        super(message);
    }
}
