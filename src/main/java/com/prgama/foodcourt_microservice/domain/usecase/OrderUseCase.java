package com.prgama.foodcourt_microservice.domain.usecase;

import com.prgama.foodcourt_microservice.domain.api.IOrderServicePort;
import com.prgama.foodcourt_microservice.domain.api.IUserServicePort;
import com.prgama.foodcourt_microservice.domain.constants.ExceptionConstants;
import com.prgama.foodcourt_microservice.domain.constants.OrderStatusConstants;
import com.prgama.foodcourt_microservice.domain.constants.PaginationConstants;
import com.prgama.foodcourt_microservice.domain.exception.*;
import com.prgama.foodcourt_microservice.domain.model.Dish;
import com.prgama.foodcourt_microservice.domain.model.Order;
import com.prgama.foodcourt_microservice.domain.model.OrderDish;
import com.prgama.foodcourt_microservice.domain.model.Pagination;
import com.prgama.foodcourt_microservice.domain.spi.IDishPersistencePort;
import com.prgama.foodcourt_microservice.domain.spi.IOrderPersistencePort;
import com.prgama.foodcourt_microservice.domain.spi.IRestaurantPersistencePort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class OrderUseCase implements IOrderServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IOrderPersistencePort orderPersistencePort;
    private final IDishPersistencePort dishPersistencePort;
    private final IUserServicePort userServicePort;

    public OrderUseCase(IRestaurantPersistencePort restaurantPersistencePort, IOrderPersistencePort orderPersistencePort, IDishPersistencePort dishPersistencePort, IUserServicePort userServicePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.orderPersistencePort = orderPersistencePort;
        this.dishPersistencePort = dishPersistencePort;
        this.userServicePort = userServicePort;
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

    @Override
    public Pagination<Order> listOrdersByRestaurantAndStatus(Long employeeId, String orderStatus, Integer pageNumber, Integer pageSize, String sortDirection) {
        validateOrderStatus(orderStatus);
        validatePagination(pageNumber, pageSize, sortDirection);
        Long restaurantId = userServicePort.getEmployeesRestaurant(employeeId);
        return orderPersistencePort.listOrdersByRestaurantAndStatus(restaurantId, orderStatus, pageNumber, pageSize, PaginationConstants.SORT_BY_ID, sortDirection);
    }

    @Override
    public void assignOrderToEmployee(Long employeeId, Long orderId) {
        Order order = orderPersistencePort.findOrderById(orderId);
        validateOrderExistence(order);
        Long employeesRestaurantId = userServicePort.getEmployeesRestaurant(employeeId);
        validateIfEmployeeWorksForOrderRestaurant(employeesRestaurantId, order.getRestaurant().getId());
        validateIfOrderInPendingStatus(order.getStatus());
        order.setEmployeeId(employeeId);
        order.setStatus(OrderStatusConstants.PREPARING_STATUS);
        System.out.println(order.getId());
        orderPersistencePort.updateOrderAssignEmployee(order);
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

    private void validateOrderStatus(String orderStatus) {
        if (!Objects.equals(orderStatus, OrderStatusConstants.PENDING_STATUS)
                && !Objects.equals(orderStatus, OrderStatusConstants.PREPARING_STATUS)
                && !Objects.equals(orderStatus, OrderStatusConstants.READY_STATUS)
                && !Objects.equals(orderStatus, OrderStatusConstants.DELIVERED_STATUS)
                && !Objects.equals(orderStatus, OrderStatusConstants.CANCELED_STATUS)) {
            throw new InvalidOrderStatusException(ExceptionConstants.INVALID_ORDER_STATUS_MESSAGE);
        }
    }

    private void validatePagination(Integer pageNumber, Integer pageSize, String sortDirection) {
        if (pageNumber == null) {
            throw new InvalidPageNumberException(ExceptionConstants.PAGE_NUMBER_MANDATORY_MESSAGE);
        } else if (pageNumber < 0) {
            throw new InvalidPageNumberException(ExceptionConstants.INVALID_PAGE_NUMBER_MESSAGE);
        }
        if (pageSize == null) {
            throw new InvalidPageSizeException(ExceptionConstants.PAGE_SIZE_MANDATORY_MESSAGE);
        } else if (pageSize <= 0) {
            throw new InvalidPageSizeException(ExceptionConstants.INVALID_PAGE_SIZE_MESSAGE);
        }
        if (!sortDirection.equalsIgnoreCase(PaginationConstants.SORT_DIRECTION_ASC) && !sortDirection.equalsIgnoreCase(PaginationConstants.SORT_DIRECTION_DESC)) {
            throw new InvalidSortDirectionException(ExceptionConstants.INVALID_SORT_DIRECTION_MESSAGE);
        }
    }

    private void validateOrderExistence(Order order) {
        if (order == null) {
            throw new OrderNotFoundException(ExceptionConstants.ORDER_NOT_FOUND_MESSAGE);
        }
    }

    private void validateIfEmployeeWorksForOrderRestaurant(Long employeesRestaurantId, Long orderRestaurantId) {
        if (!Objects.equals(employeesRestaurantId, orderRestaurantId)) {
            throw new UnauthorizedEmployeeException(ExceptionConstants.UNAUTHORIZED_EMPLOYEE_MESSAGE);
        }
    }

    private void validateIfOrderInPendingStatus(String orderStatus) {
        if (!Objects.equals(orderStatus, OrderStatusConstants.PENDING_STATUS)) {
            throw new OrderNotPendingException(ExceptionConstants.ORDER_NOT_PENDING_MESSAGE);
        }
    }
}
