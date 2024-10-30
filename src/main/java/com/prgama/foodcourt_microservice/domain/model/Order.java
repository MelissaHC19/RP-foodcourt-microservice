package com.prgama.foodcourt_microservice.domain.model;

import com.prgama.foodcourt_microservice.domain.constants.OrderStatusConstants;

import java.time.LocalDateTime;
import java.util.List;

public class Order {
    private Long id;
    private Long clientId;
    private LocalDateTime date;
    private String status;
    private Long employeeId;
    private Restaurant restaurant;
    private List<OrderDish> orderDishes;

    public Order(Long id, Long clientId, LocalDateTime date, String status, Long employeeId, Restaurant restaurant, List<OrderDish> orderDishes) {
        this.id = id;
        this.clientId = clientId;
        this.date = date;
        this.status = status;
        this.employeeId = employeeId;
        this.restaurant = restaurant;
        this.orderDishes = orderDishes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public List<OrderDish> getOrderDishes() {
        return orderDishes;
    }

    public void setOrderDishes(List<OrderDish> orderDishes) {
        this.orderDishes = orderDishes;
    }
}