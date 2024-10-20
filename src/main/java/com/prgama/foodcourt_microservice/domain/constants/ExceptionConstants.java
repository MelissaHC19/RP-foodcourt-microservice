package com.prgama.foodcourt_microservice.domain.constants;

public class ExceptionConstants {
    private ExceptionConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String NOT_OWNER_MESSAGE = "User isn't an owner.";
}