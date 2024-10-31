package com.prgama.foodcourt_microservice.application.handler;

import com.prgama.foodcourt_microservice.application.dto.request.CreateRestaurantRequest;
import com.prgama.foodcourt_microservice.application.dto.response.GetRestaurantResponse;
import com.prgama.foodcourt_microservice.application.dto.response.ListRestaurantsResponse;
import com.prgama.foodcourt_microservice.application.dto.response.PaginationResponse;

public interface IRestaurantHandler {
    void createRestaurant(CreateRestaurantRequest createRestaurantRequest);
    PaginationResponse<ListRestaurantsResponse> listRestaurants(Integer pageNumber, Integer pageSize, String sortDirection);
    GetRestaurantResponse getRestaurantById(Long restaurantId, Long ownerId);
}
