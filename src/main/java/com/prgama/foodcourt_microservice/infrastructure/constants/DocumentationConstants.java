package com.prgama.foodcourt_microservice.infrastructure.constants;

public class DocumentationConstants {
    private DocumentationConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String CREATE_RESTAURANT_SUMMARY = "Create restaurant";
    public static final String CREATE_RESTAURANT_DESCRIPTION = "This endpoint allows the creation of a new restaurant by submitting required fields: name (it can contain numbers, but can't be numeric only), numeric only NIT, address, phone number (up to 13 characters, may include +), url logo, and the owner's id (it must be a user with owner role).";

    public static final String RESTAURANT_TAG = "Restaurant";

    public static final String CREATED_STATUS_CODE = "201";
    public static final String BAD_REQUEST_STATUS_CODE = "400";
    public static final String NOT_FOUND_STATUS_CODE = "404";

    public static final String CREATED_RESPONSE_CODE_DESCRIPTION = "Restaurant created successfully.";
    public static final String BAD_REQUEST_RESPONSE_CODE_DESCRIPTION = "Restaurant not created or user doesn't have owner role.";
    public static final String NOT_FOUND_RESPONSE_CODE_DESCRIPTION = "User not found with the provided id.";
}