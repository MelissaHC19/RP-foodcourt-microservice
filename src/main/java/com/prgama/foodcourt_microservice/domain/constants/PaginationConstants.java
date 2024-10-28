package com.prgama.foodcourt_microservice.domain.constants;

public class PaginationConstants {
    private PaginationConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String SORT_BY_NAME = "name";
    public static final String SORT_DIRECTION_ASC = "asc";
    public static final String SORT_DIRECTION_DESC = "desc";
}
