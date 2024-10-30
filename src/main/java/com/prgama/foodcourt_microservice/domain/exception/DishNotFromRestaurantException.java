package com.prgama.foodcourt_microservice.domain.exception;

public class DishNotFromRestaurantException extends RuntimeException {
    public DishNotFromRestaurantException(String message) {
        super(message);
    }
}
