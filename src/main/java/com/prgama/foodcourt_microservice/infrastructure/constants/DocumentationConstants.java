package com.prgama.foodcourt_microservice.infrastructure.constants;

public class DocumentationConstants {
    private DocumentationConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String CREATE_RESTAURANT_SUMMARY = "Create restaurant";
    public static final String CREATE_RESTAURANT_DESCRIPTION = "This endpoint allows the creation of a new restaurant by submitting required fields: name (it can contain numbers, but can't be numeric only), numeric only NIT, address, phone number (up to 13 characters, may include +), url logo, and the owner's id (it must be a user with owner role).";

    public static final String CREATE_DISH_SUMMARY = "Create dish";
    public static final String CREATE_DISH_DESCRIPTION = "This endpoint allows the creation of a new dish for a restaurant by submitting required fields: name, description, price (it must be integers greater than zero), url image, restaurant's id, and category's id.";

    public static final String UPDATE_DISH_SUMMARY = "Update dish";
    public static final String UPDATE_DISH_DESCRIPTION = "This endpoint allows the modification of an existing dish's description and price.";

    public static final String RESTAURANT_TAG = "Restaurant";
    public static final String DISH_TAG = "Dish";

    public static final String CREATED_STATUS_CODE = "201";
    public static final String BAD_REQUEST_STATUS_CODE = "400";
    public static final String NOT_FOUND_STATUS_CODE = "404";
    public static final String CONFLICT_STATUS_CODE = "409";
    public static final String OK_STATUS_CODE = "200";

    public static final String CREATED_RESPONSE_CODE_DESCRIPTION = "Restaurant created successfully.";
    public static final String BAD_REQUEST_RESPONSE_CODE_DESCRIPTION = "Restaurant not created or user doesn't have owner role.";
    public static final String NOT_FOUND_RESPONSE_CODE_DESCRIPTION = "User not found with the provided id.";
    public static final String CONFLICT_RESPONSE_CODE_DESCRIPTION = "Restaurant already exists.";

    public static final String CREATED_RESPONSE_CODE_DESCRIPTION_DISH = "Dish created successfully.";
    public static final String BAD_REQUEST_RESPONSE_CODE_DESCRIPTION_DISH = "Dish not created.";
    public static final String NOT_FOUND_RESPONSE_CODE_DESCRIPTION_DISH = "Restaurant or category not found with the provided id's.";
    public static final String CONFLICT_RESPONSE_CODE_DESCRIPTION_DISH = "Dish already exists.";

    public static final String OK_RESPONSE_CODE_DESCRIPTION_UPDATE_DISH = "Dish modified successfully.";
    public static final String NOT_FOUND_RESPONSE_CODE_DESCRIPTION_UPDATE_DISH = "Dish not found with the provided id.";
}