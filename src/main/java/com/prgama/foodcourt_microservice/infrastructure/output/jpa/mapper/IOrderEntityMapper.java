package com.prgama.foodcourt_microservice.infrastructure.output.jpa.mapper;

import com.prgama.foodcourt_microservice.domain.model.Order;
import com.prgama.foodcourt_microservice.domain.model.OrderDish;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.entity.OrderDishEntity;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IOrderEntityMapper {
    OrderEntity orderToEntity(Order order);
    Order orderEntityToOrder(OrderEntity orderEntity);
    OrderDishEntity orderDishToEntity(OrderDish orderDish);

    @Mapping(target = "order", ignore = true)
    OrderDish orderDishEntityToOrderDish(OrderDishEntity orderDishEntity);
}
