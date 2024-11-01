package com.prgama.foodcourt_microservice.infrastructure.constants;

public class ControllerConstants {
    private ControllerConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String TIMESTAMP = "timestamp";
    public static final String ERRORS = "errors";

    public static final String RESTAURANT_CREATED_MESSAGE = "Restaurant created successfully.";
    public static final String EXTRACT_ERROR_MESSAGE_START = "\"message\":\"";
    public static final String EXTRACT_ERROR_MESSAGE_END = "\",";

    public static final String DISH_CREATED_MESSAGE = "Dish created successfully.";
    public static final String DISH_MODIFIED_MESSAGE = "Dish modified successfully.";

    public static final String ORDER_CREATED_MESSAGE = "Order created successfully.";

    public static final String ORDER_ASSIGNED_TO_EMPLOYEE_MESSAGE = "Order assigned to employee successfully.";

    public static final String ROLE_ADMIN = "Admin";
    public static final String ROLE_OWNER = "Owner";
    public static final String ROLE_CLIENT = "Client";
    public static final String ROLE_EMPLOYEE = "Employee";

    public static final String DEFAULT_SORT_DIRECTION = "asc";
}
