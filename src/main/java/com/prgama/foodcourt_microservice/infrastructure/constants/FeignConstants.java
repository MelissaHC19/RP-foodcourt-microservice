package com.prgama.foodcourt_microservice.infrastructure.constants;

public class FeignConstants {
    private FeignConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String FEIGN_CLIENT_NAME = "users-microservice";
    public static final String FEIGN_CLIENT_URL = "localhost:8080/user/owner";
}
