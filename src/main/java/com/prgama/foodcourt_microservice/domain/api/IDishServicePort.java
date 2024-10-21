package com.prgama.foodcourt_microservice.domain.api;

import com.prgama.foodcourt_microservice.domain.model.Dish;

public interface IDishServicePort {
    void createDish(Dish dish);
    void modifyDish(Long id, String description, Integer price);
}