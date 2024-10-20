package com.prgama.foodcourt_microservice.domain.exception;

public class AlreadyExistsByNameException extends RuntimeException {
    public AlreadyExistsByNameException(String message) {
        super(message);
    }
}
