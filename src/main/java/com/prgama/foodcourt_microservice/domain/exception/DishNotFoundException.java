package com.prgama.foodcourt_microservice.domain.exception;

public class DishNotFoundException extends RuntimeException {
    public DishNotFoundException(String message) {
        super(message);
    }
}
