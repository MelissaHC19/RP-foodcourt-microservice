package com.prgama.foodcourt_microservice.application.constants;

public class RequestConstants {
    private RequestConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String NAME_MANDATORY_MESSAGE = "Name must be provided.";

    public static final String NIT_MANDATORY_MESSAGE = "NIT must be provided.";

    public static final String ADDRESS_MANDATORY_MESSAGE = "Address must be provided.";

    public static final String PHONE_NUMBER_MANDATORY_MESSAGE = "Phone number must be provided.";

    public static final String URL_LOGO_MANDATORY_MESSAGE = "Logo url must be provided.";

    public static final String OWNER_ID_MANDATORY_MESSAGE = "Owner's id must be provided.";
}
