package com.prgama.foodcourt_microservice.application.constants;

public class RequestConstants {
    private RequestConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String NAME_MANDATORY_MESSAGE = "Name must be provided.";
    public static final String NAME_REGULAR_EXPRESSION = "^(?!\\d+$)[a-zA-Z\\d ]+$";
    public static final String INVALID_NAME_MESSAGE = "Name can't be numeric only.";

    public static final String NIT_MANDATORY_MESSAGE = "NIT must be provided.";
    public static final String NIT_REGULAR_EXPRESSION = "^\\d+$";
    public static final String INVALID_NIT_MESSAGE = "NIT must be numeric only.";

    public static final String ADDRESS_MANDATORY_MESSAGE = "Address must be provided.";

    public static final String PHONE_NUMBER_MANDATORY_MESSAGE = "Phone number must be provided.";
    public static final String PHONE_NUMBER_REGULAR_EXPRESSION = "^(\\+57)?\\d{10}$";
    public static final String INVALID_PHONE_NUMBER_MESSAGE = "Invalid phone number, it must have 10 digits.";

    public static final String URL_LOGO_MANDATORY_MESSAGE = "Logo url must be provided.";

    public static final String OWNER_ID_MANDATORY_MESSAGE = "Owner's id must be provided.";

    public static final String DESCRIPTION_MANDATORY_MESSAGE = "Description must be provided.";

    public static final String PRICE_MANDATORY_MESSAGE = "Price must be provided.";
    public static final String POSITIVE_PRICE_MESSAGE = "Price must be greater than zero.";

    public static final String URL_IMAGE_MANDATORY_MESSAGE = "Image url must be provided.";

    public static final String RESTAURANT_ID_MANDATORY_MESSAGE = "Restaurant's id must be provided.";

    public static final String CATEGORY_ID_MANDATORY_MESSAGE = "Category's id must be provided.";

    public static final String DISH_STATUS_MANDATORY_MESSAGE = "You must indicate dish status. If it's active, true, if not, false.";
}
