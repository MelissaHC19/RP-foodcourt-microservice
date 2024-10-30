package com.prgama.foodcourt_microservice.infrastructure.output.jpa.adapter;

import com.prgama.foodcourt_microservice.domain.constants.OrderStatusConstants;
import com.prgama.foodcourt_microservice.domain.model.Order;
import com.prgama.foodcourt_microservice.domain.spi.IOrderPersistencePort;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.entity.OrderDishEntity;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.entity.OrderEntity;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.mapper.IOrderEntityMapper;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {
    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;

    @Override
    public void createOrder(Order order) {
        OrderEntity orderEntity = orderEntityMapper.orderToEntity(order);
        List<OrderDishEntity> orderDishEntityList = setOrderIdInOrderDishes(order, orderEntity);
        orderEntity.setOrderDishes(orderDishEntityList);
        orderRepository.save(orderEntity);
    }

    private List<OrderDishEntity> setOrderIdInOrderDishes(Order order, OrderEntity orderEntity) {
        return order.getOrderDishes().stream()
                .map(orderDish -> {
                    OrderDishEntity orderDishEntity = orderEntityMapper.orderDishToEntity(orderDish);
                    orderDishEntity.setOrder(orderEntity);
                    return  orderDishEntity;
                })
                .toList();
    }

    @Override
    public boolean findOrdersByClientId(Long clientId) {
        List<OrderEntity> orderEntities = orderRepository.findAllByClientId(clientId);
        for (OrderEntity orderEntity : orderEntities) {
            if (OrderStatusConstants.PENDING_STATUS.equalsIgnoreCase(orderEntity.getStatus()) ||
                    OrderStatusConstants.PREPARING_STATUS.equalsIgnoreCase(orderEntity.getStatus()) ||
                    OrderStatusConstants.READY_STATUS.equalsIgnoreCase(orderEntity.getStatus())) {
                return true;
            }
        }
        return false;
    }
}
