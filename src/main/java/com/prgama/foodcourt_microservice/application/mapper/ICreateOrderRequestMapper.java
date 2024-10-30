package com.prgama.foodcourt_microservice.application.mapper;

import com.prgama.foodcourt_microservice.application.dto.request.CreateOrderRequest;
import com.prgama.foodcourt_microservice.application.dto.request.OrderDishRequest;
import com.prgama.foodcourt_microservice.domain.model.Dish;
import com.prgama.foodcourt_microservice.domain.model.Order;
import com.prgama.foodcourt_microservice.domain.model.OrderDish;
import com.prgama.foodcourt_microservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface ICreateOrderRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "date", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "employeeId", ignore = true)
    @Mapping(target = "clientId", ignore = true)
    @Mapping(source = "restaurantId", target = "restaurant", qualifiedByName = "mapRestaurantIdToRestaurant")
    @Mapping(source = "listDishes", target = "orderDishes", qualifiedByName = "mapListDishesToOrderDishes")
    Order requestToOrder(CreateOrderRequest createOrderRequest);

    @Named("mapRestaurantIdToRestaurant")
    default Restaurant mapRestaurantIdToRestaurant(Long id) {
        return new Restaurant(id, null, null, null, null, null, null);
    }

    @Named("mapListDishesToOrderDishes")
    default List<OrderDish> mapListDishesToOrderDishes(List<OrderDishRequest> listDishes) {
        if (listDishes == null) {
            return Collections.emptyList();
        }
        return listDishes.stream()
                .map(orderDishRequest -> new OrderDish(
                        null,
                        null,
                        new Dish(orderDishRequest.getDishId(), null, null, null, null, null, null),
                        orderDishRequest.getQuantity()
                ))
                .toList();
    }
}
