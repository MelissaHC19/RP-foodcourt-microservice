package com.prgama.foodcourt_microservice.domain.spi;

import com.prgama.foodcourt_microservice.domain.model.Dish;
import com.prgama.foodcourt_microservice.domain.model.Pagination;

public interface IDishPersistencePort {
    void createDish(Dish dish);
    boolean alreadyExistsByName(String name);
    void modifyDish(Dish dish);
    Dish findById(Long id);
    Pagination<Dish> listDishesByRestaurantAndCategory(Long restaurantId, Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
}