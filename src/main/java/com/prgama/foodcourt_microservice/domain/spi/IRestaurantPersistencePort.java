package com.prgama.foodcourt_microservice.domain.spi;

import com.prgama.foodcourt_microservice.domain.model.Pagination;
import com.prgama.foodcourt_microservice.domain.model.Restaurant;

public interface IRestaurantPersistencePort {
    void createRestaurant(Restaurant restaurant);
    boolean alreadyExistsByNit(String nit);
    boolean alreadyExistsById(Long id);
    Restaurant getRestaurant(Long id);
    Pagination<Restaurant> listRestaurants(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection);
}
