package com.prgama.foodcourt_microservice.domain.constants;

public class UseCaseConstants {
    private UseCaseConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String NAME_REGULAR_EXPRESSION = "^(?!\\d+$)[a-zA-Z\\d ]+$";

    public static final String NIT_REGULAR_EXPRESSION = "^\\d+$";

    public static final String PHONE_NUMBER_REGULAR_EXPRESSION = "^\\+([1-9]\\d{0,1})\\d{10}$|^([1-9]\\d{0,1})?\\d{10}$";

    public static final String OWNER_ROLE = "Owner";
}
