package com.prgama.foodcourt_microservice.domain.usecase;

import com.prgama.foodcourt_microservice.domain.api.IOrderServicePort;
import com.prgama.foodcourt_microservice.domain.constants.ExceptionConstants;
import com.prgama.foodcourt_microservice.domain.constants.OrderStatusConstants;
import com.prgama.foodcourt_microservice.domain.exception.DishNotFromRestaurantException;
import com.prgama.foodcourt_microservice.domain.exception.HasProcessingOrderException;
import com.prgama.foodcourt_microservice.domain.exception.RestaurantNotFoundException;
import com.prgama.foodcourt_microservice.domain.model.Dish;
import com.prgama.foodcourt_microservice.domain.model.Order;
import com.prgama.foodcourt_microservice.domain.model.OrderDish;
import com.prgama.foodcourt_microservice.domain.spi.IDishPersistencePort;
import com.prgama.foodcourt_microservice.domain.spi.IOrderPersistencePort;
import com.prgama.foodcourt_microservice.domain.spi.IRestaurantPersistencePort;

import java.time.LocalDateTime;
import java.util.List;

public class OrderUseCase implements IOrderServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IOrderPersistencePort orderPersistencePort;
    private final IDishPersistencePort dishPersistencePort;

    public OrderUseCase(IRestaurantPersistencePort restaurantPersistencePort, IOrderPersistencePort orderPersistencePort, IDishPersistencePort dishPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.orderPersistencePort = orderPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
    }

    @Override
    public void createOrder(Order order, Long clientId) {
        validateRestaurantExistence(order);
        validateAllDishesFromSameRestaurant(order);
        validateIfOrderInProcess(clientId);
        order.setDate(LocalDateTime.now());
        order.setStatus(OrderStatusConstants.PENDING_STATUS);
        order.setClientId(clientId);
        orderPersistencePort.createOrder(order);
    }

    private void validateRestaurantExistence(Order order) {
        if (!restaurantPersistencePort.alreadyExistsById(order.getRestaurant().getId())) {
            throw new RestaurantNotFoundException(ExceptionConstants.RESTAURANT_NOT_FOUND_MESSAGE);
        }
    }

    private void validateIfOrderInProcess(Long clientId) {
        boolean status = orderPersistencePort.findOrdersByClientId(clientId);
        if (status) {
            throw new HasProcessingOrderException(ExceptionConstants.HAS_PROCESSING_ORDER_MESSAGE);
        }
    }

    private void validateAllDishesFromSameRestaurant(Order order) {
        Long restaurantId = order.getRestaurant().getId();
        List<OrderDish> orderDishes = order.getOrderDishes();
        List<Long> dishesId = orderDishes.stream().map(orderDish -> orderDish.getDish().getId()).toList();
        for (Long dishId : dishesId) {
            Dish dbDish = dishPersistencePort.findById(dishId);
            if (!dbDish.getRestaurant().getId().equals(restaurantId)) {
                throw new DishNotFromRestaurantException(ExceptionConstants.DISH_NOT_FROM_RESTAURANT_MESSAGE);
            }
        }
    }
}
