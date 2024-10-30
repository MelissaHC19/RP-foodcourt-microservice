package com.prgama.foodcourt_microservice.domain.spi;

import com.prgama.foodcourt_microservice.domain.model.Order;

public interface IOrderPersistencePort {
    void createOrder(Order order);
    boolean findOrdersByClientId(Long clientId);
}
