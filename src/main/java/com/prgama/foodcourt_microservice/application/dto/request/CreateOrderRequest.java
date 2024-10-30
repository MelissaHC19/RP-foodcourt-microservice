package com.prgama.foodcourt_microservice.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CreateOrderRequest {
    private Long restaurantId;
    private List<OrderDishRequest> listDishes;
}
