package com.prgama.foodcourt_microservice.domain.constants;

public class ExceptionConstants {
    private ExceptionConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String ALREADY_EXISTS_BY_NIT_MESSAGE = "Restaurant already exists by nit.";
    public static final String NOT_OWNER_MESSAGE = "User isn't an owner.";
    public static final String ALREADY_EXISTS_BY_NAME_MESSAGE = "Dish already exists by name.";
    public static final String RESTAURANT_NOT_FOUND_MESSAGE = "Restaurant not found or doesn't exists.";
    public static final String CATEGORY_NOT_FOUND_MESSAGE = "Category not found or doesn't exists.";
    public static final String DISH_NOT_FOUND_MESSAGE = "Dish not found or doesn't exists.";
    public static final String INVALID_TOKEN_MESSAGE = "Invalid token.";
    public static final String INVALID_ROLE_MESSAGE = "Access denied. You don't have permission to access this resource.";
    public static final String UNAUTHORIZED_OWNER_MESSAGE = "You don't have permission to perform this action.";
    public static final String INVALID_SORT_DIRECTION_MESSAGE = "Sort direction must be 'asc' or 'desc'.";
    public static final String PAGE_NUMBER_MANDATORY_MESSAGE = "Page number must be provided.";
    public static final String INVALID_PAGE_NUMBER_MESSAGE = "Page number must be non-negative.";
    public static final String PAGE_SIZE_MANDATORY_MESSAGE = "Page size must be provided.";
    public static final String INVALID_PAGE_SIZE_MESSAGE = "Page size must be greater than zero.";
}