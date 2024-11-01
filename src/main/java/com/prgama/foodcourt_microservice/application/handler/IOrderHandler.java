package com.prgama.foodcourt_microservice.application.handler;

import com.prgama.foodcourt_microservice.application.dto.request.CreateOrderRequest;
import com.prgama.foodcourt_microservice.application.dto.response.ListOrdersResponse;
import com.prgama.foodcourt_microservice.application.dto.response.PaginationResponse;

public interface IOrderHandler {
    void createOrder(CreateOrderRequest createOrderRequest, Long clientId);
    PaginationResponse<ListOrdersResponse> listOrders(Long employeeId, String orderStatus, Integer pageNumber, Integer pageSize, String sortDirection);
    void assignOrderToEmployee(Long employeeId, Long orderId);
}
