package com.prgama.foodcourt_microservice.application.handler;

import com.prgama.foodcourt_microservice.application.dto.request.CreateOrderRequest;

public interface IOrderHandler {
    void createOrder(CreateOrderRequest createOrderRequest, Long clientId);
}
