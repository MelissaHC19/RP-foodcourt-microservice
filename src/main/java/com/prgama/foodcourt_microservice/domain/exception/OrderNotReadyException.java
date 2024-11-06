package com.prgama.foodcourt_microservice.domain.exception;

public class OrderNotReadyException extends RuntimeException {
    public OrderNotReadyException(String message) {
        super(message);
    }
}
