package com.prgama.foodcourt_microservice.application.handler;

import com.prgama.foodcourt_microservice.application.dto.request.CreateDishRequest;

public interface IDishHandler {
    void createDish(CreateDishRequest createDishRequest);
}