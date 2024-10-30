package com.prgama.foodcourt_microservice.application.handler;

import com.prgama.foodcourt_microservice.application.dto.request.CreateOrderRequest;
import com.prgama.foodcourt_microservice.application.mapper.ICreateOrderRequestMapper;
import com.prgama.foodcourt_microservice.domain.api.IOrderServicePort;
import com.prgama.foodcourt_microservice.domain.model.Order;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderHandler implements IOrderHandler {
    private final IOrderServicePort orderServicePort;
    private final ICreateOrderRequestMapper orderRequestMapper;

    @Override
    public void createOrder(CreateOrderRequest createOrderRequest, Long clientId) {
        Order order = orderRequestMapper.requestToOrder(createOrderRequest);
        orderServicePort.createOrder(order, clientId);
    }
}
