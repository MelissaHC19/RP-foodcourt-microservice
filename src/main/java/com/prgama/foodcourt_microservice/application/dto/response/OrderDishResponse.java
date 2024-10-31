package com.prgama.foodcourt_microservice.application.dto.response;

import com.prgama.foodcourt_microservice.domain.model.Dish;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderDishResponse {
    private Long id;
    private Dish dish;
    private Integer quantity;
}
