package com.prgama.foodcourt_microservice.domain.exception;

public class OrderNotPreparingException extends RuntimeException {
    public OrderNotPreparingException(String message) {
        super(message);
    }
}
