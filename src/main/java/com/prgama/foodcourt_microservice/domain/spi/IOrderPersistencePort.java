package com.prgama.foodcourt_microservice.domain.spi;

import com.prgama.foodcourt_microservice.domain.model.Order;
import com.prgama.foodcourt_microservice.domain.model.Pagination;

public interface IOrderPersistencePort {
    void createOrder(Order order);
    boolean findOrdersByClientId(Long clientId);
    Pagination<Order> listOrdersByRestaurantAndStatus(Long restaurantId, String orderStatus, Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
}
