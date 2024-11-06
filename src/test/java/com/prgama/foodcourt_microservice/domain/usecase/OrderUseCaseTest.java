package com.prgama.foodcourt_microservice.domain.usecase;

import com.prgama.foodcourt_microservice.domain.api.IMessageServicePort;
import com.prgama.foodcourt_microservice.domain.api.IUserServicePort;
import com.prgama.foodcourt_microservice.domain.constants.ExceptionConstants;
import com.prgama.foodcourt_microservice.domain.constants.MessagingConstants;
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

    @Mock
    private IMessageServicePort messageServicePort;

    @InjectMocks
    private OrderUseCase orderUseCase;

    @Test
    @DisplayName("Creates an order successfully")
    void createOrder() {
        Long clientId = 1L;
        Restaurant restaurant = new Restaurant(1L, "Test Restaurant", null, null, null, null, null);
        Dish dish = new Dish(1L, "Dish Name", "Delicious dish", 10000, "dish-image.jpg", restaurant, new Category(1L, "Category", null));
        OrderDish orderDish = new OrderDish(5L, null, dish, 5);
        Order order = new Order(3L, clientId, LocalDateTime.now(), "Pending", 3L, restaurant, List.of(orderDish), null);
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
        Order order = new Order(3L, clientId, LocalDateTime.now(), "Pending", 3L, restaurant, List.of(orderDish), null);
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
        Order order = new Order(3L, clientId, LocalDateTime.now(), "Pending", 3L, restaurant, List.of(orderDish), null);
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
        Order order = new Order(3L, clientId, LocalDateTime.now(), "Pending", 3L, restaurant, List.of(orderDish), null);
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
        Order order = new Order(3L, 1L, LocalDateTime.now(), "Pending", 3L, restaurant, List.of(orderDish), null);

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

    @Test
    @DisplayName("Order assigned to employee successfully and order status updated to 'Preparing'")
    void assignOrderToEmployee() {
        Long employeeId = 1L;
        Long orderId = 2L;
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(orderId, 3L, LocalDateTime.now(), "Pending", null, restaurant, null, null);

        Mockito.when(orderPersistencePort.findOrderById(orderId)).thenReturn(order);
        Mockito.when(userServicePort.getEmployeesRestaurant(employeeId)).thenReturn(restaurant.getId());

        orderUseCase.assignOrderToEmployee(employeeId, orderId);

        Mockito.verify(orderPersistencePort, Mockito.times(1)).findOrderById(orderId);
        Mockito.verify(userServicePort, Mockito.times(1)).getEmployeesRestaurant(employeeId);
        Mockito.verify(orderPersistencePort, Mockito.times(1)).updateOrderAssignEmployee(order);
        assertEquals("Preparing", order.getStatus());
        assertEquals(employeeId, order.getEmployeeId());
    }

    @Test
    @DisplayName("Validation exception when order not found or doesn't exist")
    void assignOrderToEmployeeShouldThrowValidationExceptionWhenOrderNotFound(){
        Long employeeId = 1L;
        Long orderId = 2L;
        Mockito.when(orderPersistencePort.findOrderById(orderId)).thenReturn(null);

        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            orderUseCase.assignOrderToEmployee(employeeId, orderId);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ORDER_NOT_FOUND_MESSAGE);
        Mockito.verify(orderPersistencePort, Mockito.times(1)).findOrderById(orderId);
        Mockito.verify(orderPersistencePort, Mockito.never()).updateOrderAssignEmployee(null);
    }

    @Test
    @DisplayName("Validation exception when employee doesn't work for the order's restaurant")
    void assignOrderToEmployeeShouldThrowValidationExceptionWhenEmployeeNotFromOrderRestaurant(){
        Long employeeId = 1L;
        Long orderId = 2L;
        Long employeeRestaurantId = 3L;
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(orderId, 3L, LocalDateTime.now(), "Pending", null, restaurant, null, null);

        Mockito.when(orderPersistencePort.findOrderById(orderId)).thenReturn(order);
        Mockito.when(userServicePort.getEmployeesRestaurant(employeeId)).thenReturn(employeeRestaurantId);

        UnauthorizedEmployeeException exception = assertThrows(UnauthorizedEmployeeException.class, () -> {
            orderUseCase.assignOrderToEmployee(employeeId, orderId);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.UNAUTHORIZED_EMPLOYEE_MESSAGE);
        Mockito.verify(orderPersistencePort, Mockito.times(1)).findOrderById(orderId);
        Mockito.verify(userServicePort, Mockito.times(1)).getEmployeesRestaurant(employeeId);
        Mockito.verify(orderPersistencePort, Mockito.never()).updateOrderAssignEmployee(null);
    }

    @Test
    @DisplayName("Validation exception when order doesn't have 'Pending' status")
    void assignOrderToEmployeeShouldThrowValidationExceptionWhenOrderDoesNotHavePendingStatus() {
        Long employeeId = 1L;
        Long orderId = 2L;
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(orderId, 3L, LocalDateTime.now(), "Preparing", null, restaurant, null, null);

        Mockito.when(orderPersistencePort.findOrderById(orderId)).thenReturn(order);
        Mockito.when(userServicePort.getEmployeesRestaurant(employeeId)).thenReturn(restaurant.getId());

        OrderNotPendingException exception = assertThrows(OrderNotPendingException.class, () -> {
            orderUseCase.assignOrderToEmployee(employeeId, orderId);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ORDER_NOT_PENDING_MESSAGE);
        Mockito.verify(orderPersistencePort, Mockito.times(1)).findOrderById(orderId);
        Mockito.verify(userServicePort, Mockito.times(1)).getEmployeesRestaurant(employeeId);
        Mockito.verify(orderPersistencePort, Mockito.never()).updateOrderAssignEmployee(null);
    }

    @Test
    @DisplayName("Order marked as ready successfully and SMS sent")
    void finishOrder() {
        Long employeeId = 1L;
        Long orderId = 2L;
        String phoneNumber = "+573205898802";
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(orderId, 3L, LocalDateTime.now(), "Preparing", 1L, restaurant, null, null);

        Mockito.when(orderPersistencePort.findOrderById(orderId)).thenReturn(order);
        Mockito.when(userServicePort.getClientsPhoneNumber(order.getClientId())).thenReturn(phoneNumber);

        orderUseCase.finishOrder(employeeId, orderId);

        Mockito.verify(orderPersistencePort, Mockito.times(1)).findOrderById(orderId);
        Mockito.verify(userServicePort, Mockito.times(1)).getClientsPhoneNumber(order.getClientId());
        Mockito.verify(orderPersistencePort, Mockito.times(1)).updateOrderStatus(order);
        Mockito.verify(messageServicePort, Mockito.times(1)).sendMessage(phoneNumber, MessagingConstants.SMS + order.getSecurityCode());
        assertEquals("Ready", order.getStatus());
        assertNotNull(order.getSecurityCode());
        assertTrue(order.getSecurityCode() >= 1000 && order.getSecurityCode() <= 9999);
    }

    @Test
    @DisplayName("Validation exception when order not found or doesn't exist")
    void finishOrderShouldThrowValidationExceptionWhenOrderNotFound(){
        Long employeeId = 1L;
        Long orderId = 2L;
        Mockito.when(orderPersistencePort.findOrderById(orderId)).thenReturn(null);

        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            orderUseCase.finishOrder(employeeId, orderId);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ORDER_NOT_FOUND_MESSAGE);
        Mockito.verify(orderPersistencePort, Mockito.times(1)).findOrderById(orderId);
        Mockito.verify(userServicePort, Mockito.never()).getClientsPhoneNumber(null);
        Mockito.verify(messageServicePort, Mockito.never()).sendMessage(null, null);
        Mockito.verify(orderPersistencePort, Mockito.never()).updateOrderStatus(null);
    }

    @Test
    @DisplayName("Validation exception when employee doesn't work in that order")
    void finishOrderShouldThrowValidationExceptionWhenEmployeeNotAssignToOrder(){
        Long employeeId = 1L;
        Long orderId = 2L;
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(orderId, 3L, LocalDateTime.now(), "Preparing", 2L, restaurant, null, null);

        Mockito.when(orderPersistencePort.findOrderById(orderId)).thenReturn(order);

        UnauthorizedEmployeeException exception = assertThrows(UnauthorizedEmployeeException.class, () -> {
            orderUseCase.finishOrder(employeeId, orderId);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.UNAUTHORIZED_EMPLOYEE_ORDER_MESSAGE);
        Mockito.verify(orderPersistencePort, Mockito.times(1)).findOrderById(orderId);
        Mockito.verify(userServicePort, Mockito.never()).getClientsPhoneNumber(null);
        Mockito.verify(messageServicePort, Mockito.never()).sendMessage(null, null);
        Mockito.verify(orderPersistencePort, Mockito.never()).updateOrderStatus(null);
    }

    @Test
    @DisplayName("Validation exception when order doesn't have 'Preparing' status")
    void finishOrderShouldThrowValidationExceptionWhenOrderDoesNotHavePreparingStatus() {
        Long employeeId = 1L;
        Long orderId = 2L;
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(orderId, 3L, LocalDateTime.now(), "Pending", 1L, restaurant, null, null);

        Mockito.when(orderPersistencePort.findOrderById(orderId)).thenReturn(order);

        OrderNotPreparingException exception = assertThrows(OrderNotPreparingException.class, () -> {
            orderUseCase.finishOrder(employeeId, orderId);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ORDER_NOT_PREPARING_MESSAGE);
        Mockito.verify(orderPersistencePort, Mockito.times(1)).findOrderById(orderId);
        Mockito.verify(userServicePort, Mockito.never()).getClientsPhoneNumber(null);
        Mockito.verify(messageServicePort, Mockito.never()).sendMessage(null, null);
        Mockito.verify(orderPersistencePort, Mockito.never()).updateOrderStatus(null);
    }

    @Test
    @DisplayName("Order marked as delivered successfully")
    void deliverOrder() {
        Long employeeId = 1L;
        Long orderId = 2L;
        Integer securityCode = 5678;
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(orderId, 3L, LocalDateTime.now(), "Ready", 1L, restaurant, null, 5678);

        Mockito.when(orderPersistencePort.findOrderById(orderId)).thenReturn(order);

        orderUseCase.deliverOrder(employeeId, orderId, securityCode);

        Mockito.verify(orderPersistencePort, Mockito.times(1)).findOrderById(orderId);
        Mockito.verify(orderPersistencePort, Mockito.times(1)).updateOrderStatus(order);
        assertEquals(order.getSecurityCode(), securityCode);
        assertEquals("Delivered", order.getStatus());
    }

    @Test
    @DisplayName("Validation exception when order not found or doesn't exist")
    void deliverOrderShouldThrowValidationExceptionWhenOrderNotFound(){
        Long employeeId = 1L;
        Long orderId = 2L;
        Integer securityCode = 5678;
        Mockito.when(orderPersistencePort.findOrderById(orderId)).thenReturn(null);

        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            orderUseCase.deliverOrder(employeeId, orderId, securityCode);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ORDER_NOT_FOUND_MESSAGE);
        Mockito.verify(orderPersistencePort, Mockito.times(1)).findOrderById(orderId);
        Mockito.verify(orderPersistencePort, Mockito.never()).updateOrderStatus(null);
    }

    @Test
    @DisplayName("Validation exception when employee doesn't work in that order")
    void deliverOrderShouldThrowValidationExceptionWhenEmployeeNotAssignToOrder(){
        Long employeeId = 1L;
        Long orderId = 2L;
        Integer securityCode = 5678;
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(orderId, 3L, LocalDateTime.now(), "Ready", 3L, restaurant, null, 5678);

        Mockito.when(orderPersistencePort.findOrderById(orderId)).thenReturn(order);

        UnauthorizedEmployeeException exception = assertThrows(UnauthorizedEmployeeException.class, () -> {
            orderUseCase.deliverOrder(employeeId, orderId, securityCode);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.UNAUTHORIZED_EMPLOYEE_ORDER_MESSAGE);
        Mockito.verify(orderPersistencePort, Mockito.times(1)).findOrderById(orderId);
        Mockito.verify(orderPersistencePort, Mockito.never()).updateOrderStatus(null);
    }

    @Test
    @DisplayName("Validation exception when order doesn't have 'Ready' status")
    void deliverOrderShouldThrowValidationExceptionWhenOrderDoesNotHaveReadyStatus() {
        Long employeeId = 1L;
        Long orderId = 2L;
        Integer securityCode = 5678;
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(orderId, 3L, LocalDateTime.now(), "Preparing", 1L, restaurant, null, 5678);

        Mockito.when(orderPersistencePort.findOrderById(orderId)).thenReturn(order);

        OrderNotReadyException exception = assertThrows(OrderNotReadyException.class, () -> {
            orderUseCase.deliverOrder(employeeId, orderId, securityCode);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ORDER_NOT_READY_MESSAGE);
        Mockito.verify(orderPersistencePort, Mockito.times(1)).findOrderById(orderId);
        Mockito.verify(orderPersistencePort, Mockito.never()).updateOrderStatus(null);
    }

    @Test
    @DisplayName("Validation exception when security code provided is incorrect")
    void deliverOrderShouldThrowValidationExceptionWhenInvalidSecurityCode() {
        Long employeeId = 1L;
        Long orderId = 2L;
        Integer securityCode = 1234;
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(orderId, 3L, LocalDateTime.now(), "Ready", 1L, restaurant, null, 5678);

        Mockito.when(orderPersistencePort.findOrderById(orderId)).thenReturn(order);

        InvalidSecurityCodeException exception = assertThrows(InvalidSecurityCodeException.class, () -> {
            orderUseCase.deliverOrder(employeeId, orderId, securityCode);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_SECURITY_CODE_MESSAGE);
        Mockito.verify(orderPersistencePort, Mockito.times(1)).findOrderById(orderId);
        Mockito.verify(orderPersistencePort, Mockito.never()).updateOrderStatus(null);
    }

    @Test
    @DisplayName("Cancel order successfully")
    void cancelOrder() {
        Long clientId = 1L;
        Long orderId = 2L;
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(orderId, 1L, LocalDateTime.now(), "Pending", 1L, restaurant, null, null);

        Mockito.when(orderPersistencePort.findOrderById(orderId)).thenReturn(order);

        orderUseCase.cancelOrder(clientId, orderId);

        Mockito.verify(orderPersistencePort, Mockito.times(1)).findOrderById(orderId);
        Mockito.verify(orderPersistencePort, Mockito.times(1)).updateOrderStatus(order);
        assertEquals("Canceled", order.getStatus());
    }

    @Test
    @DisplayName("Validation exception when order not found or doesn't exist")
    void cancelOrderShouldThrowValidationExceptionWhenOrderNotFound(){
        Long clientId = 1L;
        Long orderId = 2L;
        Mockito.when(orderPersistencePort.findOrderById(orderId)).thenReturn(null);

        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            orderUseCase.cancelOrder(clientId, orderId);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ORDER_NOT_FOUND_MESSAGE);
        Mockito.verify(orderPersistencePort, Mockito.times(1)).findOrderById(orderId);
        Mockito.verify(orderPersistencePort, Mockito.never()).updateOrderStatus(null);
    }

    @Test
    @DisplayName("Validation exception when client didn't placed the order he wants to cancel")
    void cancelOrderShouldThrowValidationExceptionWhenClientDidNotPlaceOrder(){
        Long clientId = 1L;
        Long orderId = 2L;
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(orderId, 3L, LocalDateTime.now(), "Ready", 3L, restaurant, null, null);

        Mockito.when(orderPersistencePort.findOrderById(orderId)).thenReturn(order);

        UnauthorizedClientException exception = assertThrows(UnauthorizedClientException.class, () -> {
            orderUseCase.cancelOrder(clientId, orderId);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.UNAUTHORIZED_CLIENT_MESSAGE);
        Mockito.verify(orderPersistencePort, Mockito.times(1)).findOrderById(orderId);
        Mockito.verify(orderPersistencePort, Mockito.never()).updateOrderStatus(null);
    }

    @Test
    @DisplayName("Validation exception when order doesn't have 'Pending' status")
    void cancelOrderShouldThrowValidationExceptionWhenOrderDoesNotHavePendingStatus() {
        Long clientId = 1L;
        Long orderId = 2L;
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, null);
        Order order = new Order(orderId, 1L, LocalDateTime.now(), "Preparing", 1L, restaurant, null, null);

        Mockito.when(orderPersistencePort.findOrderById(orderId)).thenReturn(order);

        OrderNotPendingException exception = assertThrows(OrderNotPendingException.class, () -> {
            orderUseCase.cancelOrder(clientId, orderId);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ORDER_CANT_BE_CANCELED_MESSAGE);
        Mockito.verify(orderPersistencePort, Mockito.times(1)).findOrderById(orderId);
        Mockito.verify(orderPersistencePort, Mockito.never()).updateOrderStatus(null);
    }
}