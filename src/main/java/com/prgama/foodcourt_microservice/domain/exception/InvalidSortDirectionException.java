package com.prgama.foodcourt_microservice.domain.exception;

public class InvalidSortDirectionException extends RuntimeException {
    public InvalidSortDirectionException(String message) {
        super(message);
    }
}
