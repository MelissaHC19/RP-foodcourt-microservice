package com.prgama.foodcourt_microservice.application.handler;

import com.prgama.foodcourt_microservice.application.dto.request.CreateRestaurantRequest;

public interface IRestaurantHandler {
    void createRestaurant(CreateRestaurantRequest createRestaurantRequest);
}
