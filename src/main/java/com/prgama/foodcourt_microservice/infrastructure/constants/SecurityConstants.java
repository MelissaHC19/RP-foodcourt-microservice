package com.prgama.foodcourt_microservice.infrastructure.constants;

public class SecurityConstants {
    private SecurityConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String SECRET_KEY_NAME = "secret_key";
    public static final String SECRET_KEY = System.getenv(SECRET_KEY_NAME);
    public static final String CLAIMS_ROLE = "role";
}
