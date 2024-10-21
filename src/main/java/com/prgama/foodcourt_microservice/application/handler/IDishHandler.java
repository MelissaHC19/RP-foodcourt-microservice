package com.prgama.foodcourt_microservice.application.handler;

import com.prgama.foodcourt_microservice.application.dto.request.CreateDishRequest;
import com.prgama.foodcourt_microservice.application.dto.request.ModifyDishRequest;

public interface IDishHandler {
    void createDish(CreateDishRequest createDishRequest);
    void modifyDish(Long id, ModifyDishRequest modifyDishRequest);
}