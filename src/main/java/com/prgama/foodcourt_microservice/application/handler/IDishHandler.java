package com.prgama.foodcourt_microservice.application.handler;

import com.prgama.foodcourt_microservice.application.dto.request.CreateDishRequest;
import com.prgama.foodcourt_microservice.application.dto.request.ModifyDishRequest;
import com.prgama.foodcourt_microservice.application.dto.request.UpdateDishStatusRequest;

public interface IDishHandler {
    void createDish(Long ownerId, CreateDishRequest createDishRequest);
    void modifyDish(Long id, ModifyDishRequest modifyDishRequest, Long ownerId);
    void updateDishStatus(Long id, UpdateDishStatusRequest updateDishStatusRequest, Long ownerId);
}