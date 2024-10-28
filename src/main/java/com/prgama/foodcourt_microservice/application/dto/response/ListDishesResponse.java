package com.prgama.foodcourt_microservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ListDishesResponse {
    private String name;
    private String description;
    private Integer price;
    private String urlImage;
    private String category;
}
