package com.prgama.foodcourt_microservice.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderDishRequest {
    private Long dishId;
    private Integer quantity;
}
