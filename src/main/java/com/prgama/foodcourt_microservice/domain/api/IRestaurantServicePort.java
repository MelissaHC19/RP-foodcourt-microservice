package com.prgama.foodcourt_microservice.domain.api;

import com.prgama.foodcourt_microservice.domain.model.Restaurant;

public interface IRestaurantServicePort {
    void createRestaurant(Restaurant restaurant);
}
