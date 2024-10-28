package com.prgama.foodcourt_microservice.application.handler;

import com.prgama.foodcourt_microservice.application.dto.request.CreateDishRequest;
import com.prgama.foodcourt_microservice.application.dto.request.ModifyDishRequest;
import com.prgama.foodcourt_microservice.application.dto.response.ListDishesResponse;
import com.prgama.foodcourt_microservice.application.dto.response.PaginationResponse;

public interface IDishHandler {
    void createDish(Long ownerId, CreateDishRequest createDishRequest);
    void modifyDish(Long id, ModifyDishRequest modifyDishRequest, Long ownerId);
    PaginationResponse<ListDishesResponse> listDishes(Long restaurantId, Long categoryId, Integer pageNumber, Integer pageSize, String sortDirection);
}