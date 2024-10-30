package com.prgama.foodcourt_microservice.domain.constants;

public class OrderStatusConstants {
    private OrderStatusConstants() {
        throw new IllegalStateException("Utility class");
    }

    public static final String PENDING_STATUS = "Pending";
    public static final String PREPARING_STATUS = "Preparing";
    public static final String READY_STATUS = "Ready";
    public static final String DELIVERED_STATUS = "Delivered";
    public static final String CANCELED_STATUS = "Canceled";
}
