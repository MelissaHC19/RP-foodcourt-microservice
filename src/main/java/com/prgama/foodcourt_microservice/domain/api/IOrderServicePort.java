package com.prgama.foodcourt_microservice.domain.api;

import com.prgama.foodcourt_microservice.domain.model.Order;
import com.prgama.foodcourt_microservice.domain.model.Pagination;

public interface IOrderServicePort {
    void createOrder(Order order, Long clientId);
    Pagination<Order> listOrdersByRestaurantAndStatus(Long employeeId, String orderStatus, Integer pageNumber, Integer pageSize, String sortDirection);
}
