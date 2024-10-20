package com.prgama.foodcourt_microservice.domain.constants;

public class ExceptionConstants {
    private ExceptionConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String NOT_OWNER_MESSAGE = "User isn't an owner.";
    public static final String ALREADY_EXISTS_BY_NAME_MESSAGE = "Dish already exists by name.";
    public static final String RESTAURANT_NOT_FOUND_MESSAGE = "Restaurant not found or doesn't exists.";
    public static final String CATEGORY_NOT_FOUND_MESSAGE = "Category not found or doesn't exists.";
}