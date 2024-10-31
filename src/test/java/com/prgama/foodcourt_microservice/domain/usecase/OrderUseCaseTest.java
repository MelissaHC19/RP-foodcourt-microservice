package com.prgama.foodcourt_microservice.domain.usecase;

import com.prgama.foodcourt_microservice.domain.api.IUserServicePort;
import com.prgama.foodcourt_microservice.domain.constants.ExceptionConstants;
import com.prgama.foodcourt_microservice.domain.exception.*;
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

    @Mock
    private IUserServicePort userServicePort;

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

    @Test
    @DisplayName("List orders by restaurant and order status correctly")
    void listOrdersByRestaurantAndStatus() {
        Long employeeId = 3L;
        Restaurant restaurant = new Restaurant(1L, "Test Restaurant", null, null, null, null, null);
        Dish dish = new Dish(1L, "Dish Name", "Delicious dish", 10000, "dish-image.jpg", restaurant, new Category(1L, "Category", null));
        OrderDish orderDish = new OrderDish(5L, null, dish, 5);
        Order order = new Order(3L, 1L, LocalDateTime.now(), "Pending", 3L, restaurant, List.of(orderDish));

        Pagination<Order> pagination = new Pagination<>(List.of(order), 0, 10, 1L);

        Mockito.when(userServicePort.getEmployeesRestaurant(employeeId)).thenReturn(restaurant.getId());
        Mockito.when(orderPersistencePort.listOrdersByRestaurantAndStatus(restaurant.getId(), "Pending", 0, 10, "id", "asc")).thenReturn(pagination);

        Pagination<Order> result = orderUseCase.listOrdersByRestaurantAndStatus(employeeId, "Pending", 0, 10, "asc");

        assertEquals(pagination, result);

        Mockito.verify(userServicePort, Mockito.times(1)).getEmployeesRestaurant(employeeId);
        Mockito.verify(orderPersistencePort, Mockito.times(1)).listOrdersByRestaurantAndStatus(restaurant.getId(), "Pending", 0, 10, "id", "asc");
    }

    @Test
    @DisplayName("Validation exception when invalid order status")
    void listOrdersByRestaurantAndStatusShouldThrowValidationExceptionWhenInvalidOrderStatus() {
        InvalidOrderStatusException exception = assertThrows(InvalidOrderStatusException.class, () -> {
            orderUseCase.listOrdersByRestaurantAndStatus(1L, "Processing", 0, 10, "asc");
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_ORDER_STATUS_MESSAGE);
        Mockito.verify(orderPersistencePort, Mockito.never()).listOrdersByRestaurantAndStatus(1L, "Processing", 0, 10, "id", "asc");
    }

    @Test
    @DisplayName("Validation exception when page number is null")
    void listOrdersByRestaurantAndStatusShouldThrowValidationExceptionWhenPageNumberIsNull() {
        InvalidPageNumberException exception = assertThrows(InvalidPageNumberException.class, () -> {
            orderUseCase.listOrdersByRestaurantAndStatus(1L, "Pending", null, 10, "asc");
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.PAGE_NUMBER_MANDATORY_MESSAGE);
        Mockito.verify(orderPersistencePort, Mockito.never()).listOrdersByRestaurantAndStatus(1L, "Pending", null, 10, "id", "asc");
    }

    @Test
    @DisplayName("Validation exception when page number is a negative number")
    void listOrdersByRestaurantAndStatusShouldThrowValidationExceptionWhenPageNumberIsNegative() {
        InvalidPageNumberException exception = assertThrows(InvalidPageNumberException.class, () -> {
            orderUseCase.listOrdersByRestaurantAndStatus(1L, "Pending", -1, 10, "asc");
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_PAGE_NUMBER_MESSAGE);
        Mockito.verify(orderPersistencePort, Mockito.never()).listOrdersByRestaurantAndStatus(1L, "Pending", -1, 10, "id", "asc");
    }

    @Test
    @DisplayName("Validation exception when page size is null")
    void listOrdersByRestaurantAndStatusShouldThrowValidationExceptionWhenPageSizeIsNull() {
        InvalidPageSizeException exception = assertThrows(InvalidPageSizeException.class, () -> {
            orderUseCase.listOrdersByRestaurantAndStatus(1L, "Pending", 0, null, "asc");
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.PAGE_SIZE_MANDATORY_MESSAGE);
        Mockito.verify(orderPersistencePort, Mockito.never()).listOrdersByRestaurantAndStatus(1L, "Pending", 0, null, "id", "asc");
    }

    @Test
    @DisplayName("Validation exception when page size is less than or equal to zero")
    void listOrdersByRestaurantAndStatusShouldThrowValidationExceptionWhenPageSizeIsLessThanOrEqualToZero() {
        InvalidPageSizeException exception = assertThrows(InvalidPageSizeException.class, () -> {
            orderUseCase.listOrdersByRestaurantAndStatus(1L, "Pending", 0, 0, "asc");
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_PAGE_SIZE_MESSAGE);
        Mockito.verify(orderPersistencePort, Mockito.never()).listOrdersByRestaurantAndStatus(1L, "Pending", 0, 0, "id", "asc");
    }

    @Test
    @DisplayName("Validation exception when sort direction isn't 'asc' or 'desc'")
    void listOrdersByRestaurantAndStatusShouldThrowValidationExceptionWhenSortDirectionIsNotAscOrDesc() {
        InvalidSortDirectionException exception = assertThrows(InvalidSortDirectionException.class, () -> {
            orderUseCase.listOrdersByRestaurantAndStatus(1L, "Pending", 0, 10, "order");
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_SORT_DIRECTION_MESSAGE);
        Mockito.verify(orderPersistencePort, Mockito.never()).listOrdersByRestaurantAndStatus(1L, "Pending", 0, 10, "id", "order");
    }
}