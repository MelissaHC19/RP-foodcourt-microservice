package com.prgama.foodcourt_microservice.domain.api;

import com.prgama.foodcourt_microservice.domain.model.Order;

public interface IOrderServicePort {
    void createOrder(Order order, Long clientId);
}
