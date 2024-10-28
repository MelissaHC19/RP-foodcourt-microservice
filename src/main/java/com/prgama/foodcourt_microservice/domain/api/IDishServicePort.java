package com.prgama.foodcourt_microservice.domain.api;

import com.prgama.foodcourt_microservice.domain.model.Dish;
import com.prgama.foodcourt_microservice.domain.model.Pagination;

public interface IDishServicePort {
    void createDish(Long idOwner, Dish dish);
    void modifyDish(Long id, String description, Integer price, Boolean active, Long idOwner);
    Pagination<Dish> listDishesByRestaurantAndCategory(Long restaurantId, Long categoryId, Integer pageNumber, Integer pageSize, String sortDirection);
}