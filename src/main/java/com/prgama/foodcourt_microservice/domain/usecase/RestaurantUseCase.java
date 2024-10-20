package com.prgama.foodcourt_microservice.domain.usecase;

import com.prgama.foodcourt_microservice.domain.api.IRestaurantServicePort;
import com.prgama.foodcourt_microservice.domain.api.IUserServicePort;
import com.prgama.foodcourt_microservice.domain.constants.ExceptionConstants;
import com.prgama.foodcourt_microservice.domain.exception.*;
import com.prgama.foodcourt_microservice.domain.model.Restaurant;
import com.prgama.foodcourt_microservice.domain.spi.IRestaurantPersistencePort;

public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;
    private final IUserServicePort userServicePort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort, IUserServicePort userServicePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
        this.userServicePort = userServicePort;
    }

    @Override
    public void createRestaurant(Restaurant restaurant) {
        validateOwner(restaurant);
        restaurantPersistencePort.createRestaurant(restaurant);
    }

    private void validateOwner(Restaurant restaurant) {
        boolean ownerValidation = userServicePort.getUserById(restaurant.getOwnerId());

        if (!ownerValidation) {
            throw new NotOwnerException(ExceptionConstants.NOT_OWNER_MESSAGE);
        }
    }
}
