package com.prgama.foodcourt_microservice.domain.constants;

public class ExceptionConstants {
    private ExceptionConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String NAME_MANDATORY_MESSAGE = "Name must be provided.";
    public static final String INVALID_NAME_MESSAGE = "Name can't be numeric only.";

    public static final String NIT_MANDATORY_MESSAGE = "NIT must be provided.";
    public static final String INVALID_NIT_MESSAGE = "NIT must be numeric only.";

    public static final String ADDRESS_MANDATORY_MESSAGE = "Address must be provided.";

    public static final String PHONE_NUMBER_MANDATORY_MESSAGE = "Phone number must be provided.";
    public static final String INVALID_PHONE_NUMBER_MESSAGE = "Invalid phone number.";

    public static final String URL_LOGO_MANDATORY_MESSAGE = "Logo url must be provided.";

    public static final String OWNER_ID_MANDATORY_MESSAGE = "Owner's id must be provided.";
    public static final String OWNER_NOT_FOUND_MESSAGE = "Owner not found.";
    public static final String NOT_OWNER_MESSAGE = "User isn't an owner.";
}