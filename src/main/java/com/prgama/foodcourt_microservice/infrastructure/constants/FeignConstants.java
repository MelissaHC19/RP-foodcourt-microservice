package com.prgama.foodcourt_microservice.infrastructure.constants;

public class FeignConstants {
    private FeignConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String FEIGN_CLIENT_NAME = "users-microservice";
    public static final String FEIGN_CLIENT_URL = "localhost:8080/user";

    public static final String FEIGN_CLIENT_NAME_MESSAGING = "messaging-microservice";
    public static final String FEIGN_CLIENT_URL_MESSAGING = "localhost:8091/sms";

    public static final String FEIGN_CLIENT_NAME_TRACEABILITY = "traceability-microservice";
    public static final String FEIGN_CLIENT_URL_TRACEABILITY = "localhost:8095/traceability";
}
