package com.prgama.foodcourt_microservice.domain.spi;

import com.prgama.foodcourt_microservice.domain.model.Dish;

public interface IDishPersistencePort {
    void createDish(Dish dish);
    boolean alreadyExistsByName(String name);
    void modifyDish(Dish dish);
    Dish findById(Long id);
}