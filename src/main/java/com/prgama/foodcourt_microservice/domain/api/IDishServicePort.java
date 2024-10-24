package com.prgama.foodcourt_microservice.domain.api;

import com.prgama.foodcourt_microservice.domain.model.Dish;

public interface IDishServicePort {
    void createDish(Long idOwner, Dish dish);
    void modifyDish(Long id, String description, Integer price, Boolean active, Long idOwner);
}