package com.prgama.foodcourt_microservice.domain.exception;

public class OrderNotPendingException extends RuntimeException {
    public OrderNotPendingException(String message) {
        super(message);
    }
}
