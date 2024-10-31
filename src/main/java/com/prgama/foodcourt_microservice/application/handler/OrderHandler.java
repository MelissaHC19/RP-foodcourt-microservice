package com.prgama.foodcourt_microservice.application.handler;

import com.prgama.foodcourt_microservice.application.dto.request.CreateOrderRequest;
import com.prgama.foodcourt_microservice.application.dto.response.ListOrdersResponse;
import com.prgama.foodcourt_microservice.application.dto.response.PaginationResponse;
import com.prgama.foodcourt_microservice.application.mapper.ICreateOrderRequestMapper;
import com.prgama.foodcourt_microservice.application.mapper.IListOrdersResponseMapper;
import com.prgama.foodcourt_microservice.domain.api.IOrderServicePort;
import com.prgama.foodcourt_microservice.domain.model.Order;
import com.prgama.foodcourt_microservice.domain.model.Pagination;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderHandler {
    private final IOrderServicePort orderServicePort;
    private final ICreateOrderRequestMapper orderRequestMapper;
    private final IListOrdersResponseMapper listOrdersResponseMapper;

    @Override
    public void createOrder(CreateOrderRequest createOrderRequest, Long clientId) {
        Order order = orderRequestMapper.requestToOrder(createOrderRequest);
        orderServicePort.createOrder(order, clientId);
    }

    @Override
    public PaginationResponse<ListOrdersResponse> listOrders(Long employeeId, String orderStatus, Integer pageNumber, Integer pageSize, String sortDirection) {
        Pagination<Order> pagination = orderServicePort.listOrdersByRestaurantAndStatus(employeeId, orderStatus, pageNumber, pageSize, sortDirection);
        return listOrdersResponseMapper.paginationToPaginationResponse(pagination);
    }
}
