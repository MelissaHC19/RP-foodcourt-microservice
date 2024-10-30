package com.prgama.foodcourt_microservice.domain.usecase;

import com.prgama.foodcourt_microservice.domain.constants.ExceptionConstants;
import com.prgama.foodcourt_microservice.domain.exception.DishNotFromRestaurantException;
import com.prgama.foodcourt_microservice.domain.exception.HasProcessingOrderException;
import com.prgama.foodcourt_microservice.domain.exception.RestaurantNotFoundException;
import com.prgama.foodcourt_microservice.domain.model.*;
import com.prgama.foodcourt_microservice.domain.spi.IDishPersistencePort;
import com.prgama.foodcourt_microservice.domain.spi.IOrderPersistencePort;
import com.prgama.foodcourt_microservice.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderUseCaseTest {
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IOrderPersistencePort orderPersistencePort;

    @Mock
    private IDishPersistencePort dishPersistencePort;

    @InjectMocks
    private OrderUseCase orderUseCase;

    @Test
    @DisplayName("Creates an order successfully")
    void createOrder() {
        Long clientId = 1L;
        Restaurant restaurant = new Restaurant(1L, "Test Restaurant", null, null, null, null, null);
        Dish dish = new Dish(1L, "Dish Name", "Delicious dish", 10000, "dish-image.jpg", restaurant, new Category(1L, "Category", null));
        OrderDish orderDish = new OrderDish(5L, null, dish, 5);
        Order order = new Order(3L, clientId, LocalDateTime.now(), "Pending", 3L, restaurant, List.of(orderDish));
        orderDish.setOrder(order);

        Mockito.when(restaurantPersistencePort.alreadyExistsById(1L)).thenReturn(true);
        Mockito.when(orderPersistencePort.findOrdersByClientId(clientId)).thenReturn(false);
        Mockito.when(dishPersistencePort.findById(1L)).thenReturn(dish);

        orderUseCase.createOrder(order, clientId);

        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).alreadyExistsById(1L);
        Mockito.verify(orderPersistencePort, Mockito.times(1)).findOrdersByClientId(clientId);
        Mockito.verify(dishPersistencePort, Mockito.times(1)).findById(1L);
        Mockito.verify(orderPersistencePort, Mockito.times(1)).createOrder(order);
    }

    @Test
    @DisplayName("Validation exception when restaurant doesn't exist")
    void createOrderShouldThrowValidationExceptionWhenRestaurantNotFound() {
        Long clientId = 1L;
        Restaurant restaurant = new Restaurant(1L, "Test Restaurant", null, null, null, null, null);
        Dish dish = new Dish(1L, "Dish Name", "Delicious dish", 10000, "dish-image.jpg", restaurant, new Category(1L, "Category", null));
        OrderDish orderDish = new OrderDish(5L, null, dish, 5);
        Order order = new Order(3L, clientId, LocalDateTime.now(), "Pending", 3L, restaurant, List.of(orderDish));
        Mockito.when(restaurantPersistencePort.alreadyExistsById(1L)).thenReturn(false);

        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class, () -> {
            orderUseCase.createOrder(order, clientId);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.RESTAURANT_NOT_FOUND_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).alreadyExistsById(1L);
        Mockito.verify(orderPersistencePort, Mockito.never()).createOrder(Mockito.any());
    }

    @Test
    @DisplayName("Validation exception when all dishes aren't from the same restaurant")
    void createOrderShouldThrowValidationExceptionWhenDishNotFromRestaurant() {
        Long clientId = 1L;
        Restaurant restaurant = new Restaurant(1L, "Test Restaurant", null, null, null, null, null);
        Dish dish = new Dish(1L, "Dish Name", "Delicious dish", 10000, "dish-image.jpg", restaurant, new Category(1L, "Category", null));
        OrderDish orderDish = new OrderDish(5L, null, dish, 5);
        Order order = new Order(3L, clientId, LocalDateTime.now(), "Pending", 3L, restaurant, List.of(orderDish));
        Mockito.when(restaurantPersistencePort.alreadyExistsById(1L)).thenReturn(true);
        Mockito.when(dishPersistencePort.findById(1L)).thenReturn(new Dish(1L, "Dish Name", null, 0, null, new Restaurant(2L, null, null, null, null, null, null), null));


        DishNotFromRestaurantException exception = assertThrows(DishNotFromRestaurantException.class, () -> {
            orderUseCase.createOrder(order, clientId);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.DISH_NOT_FROM_RESTAURANT_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).alreadyExistsById(1L);
        Mockito.verify(dishPersistencePort, Mockito.times(1)).findById(1L);
        Mockito.verify(orderPersistencePort, Mockito.never()).createOrder(Mockito.any());
    }

    @Test
    @DisplayName("Validation exception when client already has an order in process")
    void createOrderShouldThrowValidationExceptionWhenUserHasProcessingOrder() {
        Long clientId = 1L;
        Restaurant restaurant = new Restaurant(1L, "Test Restaurant", null, null, null, null, null);
        Dish dish = new Dish(1L, "Dish Name", "Delicious dish", 10000, "dish-image.jpg", restaurant, new Category(1L, "Category", null));
        OrderDish orderDish = new OrderDish(5L, null, dish, 5);
        Order order = new Order(3L, clientId, LocalDateTime.now(), "Pending", 3L, restaurant, List.of(orderDish));
        Mockito.when(restaurantPersistencePort.alreadyExistsById(1L)).thenReturn(true);
        Mockito.when(dishPersistencePort.findById(1L)).thenReturn(dish);
        Mockito.when(orderPersistencePort.findOrdersByClientId(clientId)).thenReturn(true);

        HasProcessingOrderException exception = assertThrows(HasProcessingOrderException.class, () -> {
            orderUseCase.createOrder(order, clientId);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.HAS_PROCESSING_ORDER_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).alreadyExistsById(1L);
        Mockito.verify(dishPersistencePort, Mockito.times(1)).findById(1L);
        Mockito.verify(orderPersistencePort, Mockito.times(1)).findOrdersByClientId(clientId);
        Mockito.verify(orderPersistencePort, Mockito.never()).createOrder(Mockito.any());
    }
}