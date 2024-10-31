package com.prgama.foodcourt_microservice.infrastructure.output.jpa.adapter;

import com.prgama.foodcourt_microservice.domain.constants.OrderStatusConstants;
import com.prgama.foodcourt_microservice.domain.model.Order;
import com.prgama.foodcourt_microservice.domain.model.Pagination;
import com.prgama.foodcourt_microservice.domain.spi.IOrderPersistencePort;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.entity.OrderDishEntity;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.entity.OrderEntity;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.mapper.IOrderEntityMapper;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.mapper.IOrderPageMapper;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.repository.IOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class OrderJpaAdapter implements IOrderPersistencePort {
    private final IOrderRepository orderRepository;
    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderPageMapper orderPageMapper;

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

    @Override
    public Pagination<Order> listOrdersByRestaurantAndStatus(Long restaurantId, String orderStatus, Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortDirection)));
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<OrderEntity> page = orderRepository.findAllByRestaurantIdAndStatus(restaurantId, orderStatus, pageRequest);

        return orderPageMapper.pageToPagination(page, orderEntityMapper);
    }
}
