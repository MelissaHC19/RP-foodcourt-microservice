package com.prgama.foodcourt_microservice.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRestaurantResponse {
    private boolean restaurantExistence;
}
