package com.prgama.foodcourt_microservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
public class ListOrdersResponse {
    private Long id;
    private Long clientId;
    private LocalDateTime date;
    private String status;
    private Long employeeId;
    private Long restaurant;
    private List<OrderDishResponse> orderDishes;
}
