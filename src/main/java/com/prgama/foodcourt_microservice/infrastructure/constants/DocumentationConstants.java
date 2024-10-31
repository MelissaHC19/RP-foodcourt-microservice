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
    public static final String UPDATE_DISH_DESCRIPTION = "This endpoint allows the modification of an existing dish's description, price, and status (true to active a dish and false to deactivate it).";

    public static final String LIST_RESTAURANTS_SUMMARY = "List restaurants";
    public static final String LIST_RESTAURANTS_DESCRIPTION = "This endpoint retrieves a paginated list of restaurants by providing page number, page size (number of elements per page) and sort direction (ascendant or descendant).";

    public static final String LIST_DISHES_SUMMARY = "List dishes per restaurant";
    public static final String LIST_DISHES_DESCRIPTION = "This endpoint retrieves a paginated list of dishes from an specific restaurant by providing page number, page size (number of elements per page) and sort direction (ascendant or descendant). It has an optional filter by category when providing the category id.";

    public static final String CREATE_ORDER_SUMMARY = "Create order";
    public static final String CREATE_ORDER_DESCRIPTION = "This endpoint allows the creation of new order. The dishes to order must be from the same restaurant and the client can only place one order at a time.";

    public static final String GET_RESTAURANT_BY_ID_SUMMARY = "Get restaurant id";
    public static final String GET_RESTAURANT_BY_ID_DESCRIPTION = "This endpoint returns true when the user is the owner of the restaurant and returns false when the restaurant doesn't exist or the user isn't the owner of the restaurant.";

    public static final String LIST_ORDERS_SUMMARY = "List orders per restaurant and order status";
    public static final String LIST_ORDERS_DESCRIPTION = "This endpoint retrieves a paginated list of orders from an specific restaurant by providing page number, page size (number of elements per page) and sort direction (ascendant or descendant). It has a filter by order status which include: Pending, Preparing, Ready, Delivered and Canceled. You must specify which order status you want to filter by.";

    public static final String RESTAURANT_TAG = "Restaurant";
    public static final String DISH_TAG = "Dish";
    public static final String ORDER_TAG = "Order";

    public static final String CREATED_STATUS_CODE = "201";
    public static final String BAD_REQUEST_STATUS_CODE = "400";
    public static final String NOT_FOUND_STATUS_CODE = "404";
    public static final String CONFLICT_STATUS_CODE = "409";
    public static final String OK_STATUS_CODE = "200";
    public static final String FORBIDDEN_STATUS_CODE = "403";
    public static final String UNAUTHORIZED_STATUS_CODE = "401";

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

    public static final String FORBIDDEN_RESPONSE_CODE_DESCRIPTION = "You don't have permission to perform this action.";
    public static final String UNAUTHORIZED_RESPONSE_CODE_DESCRIPTION = "Invalid token.";

    public static final String BAD_REQUEST_RESPONSE_CODE_DESCRIPTION_PAGINATION = "Invalid parameters provided.";
    public static final String OK_RESPONSE_CODE_DESCRIPTION_PAGINATION_RESTAURANTS = "Successfully retrieved restaurants.";
    public static final String OK_RESPONSE_CODE_DESCRIPTION_PAGINATION_DISHES = "Successfully retrieved dishes from restaurant.";

    public static final String CREATED_RESPONSE_CODE_DESCRIPTION_ORDER = "Order created successfully.";
    public static final String CONFLICT_RESPONSE_CODE_DESCRIPTION_ORDER = "User already has processing orders.";
    public static final String NOT_FOUND_RESPONSE_CODE_DESCRIPTION_ORDER = "Restaurant not found or doesn't exist.";
    public static final String BAD_REQUEST_RESPONSE_CODE_DESCRIPTION_ORDER = "Order not created.";
    public static final String OK_RESPONSE_CODE_DESCRIPTION_PAGINATION_ORDERS = "Successfully retrieved orders from restaurant.";

    public static final String OK_RESPONSE_CODE_DESCRIPTION_RESTAURANT = "The request was successful, and the restaurant's validation is returned.";

    public static final String PAGE_NUMBER_PARAMETER = "Page number to retrieve (starting from 0)";
    public static final String PAGE_SIZE_PARAMETER_RESTAURANTS = "Number of restaurants per page";
    public static final String PAGE_SIZE_PARAMETER_DISHES = "Number of dishes per page";
    public static final String PAGE_SIZE_PARAMETER_ORDERS = "Number of orders per page";
    public static final String SORT_DIRECTION_PARAMETER = "Sorting order 'asc' or 'desc'";
    public static final String CATEGORY_ID_PARAMETER = "(Optional) If specified, filter dishes by the category id. If omitted, all dishes from the restaurant are returned";
    public static final String STATUS_PARAMETER = "Filter orders by order status. Order status options: 'Pending', 'Preparing', 'Ready', 'Delivered' or 'Canceled'.";
}