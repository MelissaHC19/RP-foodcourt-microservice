package com.prgama.foodcourt_microservice.domain.exception;

public class AlreadyExistsByNitException extends RuntimeException {
    public AlreadyExistsByNitException(String message) {
        super(message);
    }
}
