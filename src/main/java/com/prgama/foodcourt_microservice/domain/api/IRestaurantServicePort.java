package com.prgama.foodcourt_microservice.domain.api;

import com.prgama.foodcourt_microservice.domain.model.Pagination;
import com.prgama.foodcourt_microservice.domain.model.Restaurant;

public interface IRestaurantServicePort {
    void createRestaurant(Restaurant restaurant);
    Pagination<Restaurant> listRestaurants(Integer pageNumber, Integer pageSize, String sortDirection);
    boolean getRestaurantById(Long restaurantId, Long ownerId);
}
