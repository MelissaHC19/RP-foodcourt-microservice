package com.prgama.foodcourt_microservice.domain.usecase;

import com.prgama.foodcourt_microservice.domain.api.IRestaurantServicePort;
import com.prgama.foodcourt_microservice.domain.api.IUserServicePort;
import com.prgama.foodcourt_microservice.domain.constants.ExceptionConstants;
import com.prgama.foodcourt_microservice.domain.constants.PaginationConstants;
import com.prgama.foodcourt_microservice.domain.exception.*;
import com.prgama.foodcourt_microservice.domain.model.Pagination;
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
        validateRestaurant(restaurant);
        validateOwner(restaurant);
        restaurantPersistencePort.createRestaurant(restaurant);
    }

    @Override
    public Pagination<Restaurant> listRestaurants(Integer pageNumber, Integer pageSize, String sortDirection) {
        validatePagination(pageNumber, pageSize, sortDirection);
        return restaurantPersistencePort.listRestaurants(pageNumber, pageSize, PaginationConstants.SORT_BY_NAME, sortDirection);
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (restaurantPersistencePort.alreadyExistsByNit(restaurant.getNit())) {
            throw new AlreadyExistsByNitException(ExceptionConstants.ALREADY_EXISTS_BY_NIT_MESSAGE);
        }
    }

    private void validateOwner(Restaurant restaurant) {
        boolean ownerValidation = userServicePort.getUserById(restaurant.getOwnerId());

        if (!ownerValidation) {
            throw new NotOwnerException(ExceptionConstants.NOT_OWNER_MESSAGE);
        }
    }

    private void validatePagination(Integer pageNumber, Integer pageSize, String sortDirection) {
        if (pageNumber == null) {
            throw new InvalidPageNumberException(ExceptionConstants.PAGE_NUMBER_MANDATORY_MESSAGE);
        } else if (pageNumber < 0) {
            throw new InvalidPageNumberException(ExceptionConstants.INVALID_PAGE_NUMBER_MESSAGE);
        }
        if (pageSize == null) {
            throw new InvalidPageSizeException(ExceptionConstants.PAGE_SIZE_MANDATORY_MESSAGE);
        } else if (pageSize <= 0) {
            throw new InvalidPageSizeException(ExceptionConstants.INVALID_PAGE_SIZE_MESSAGE);
        }
        if (!sortDirection.equalsIgnoreCase(PaginationConstants.SORT_DIRECTION_ASC) && !sortDirection.equalsIgnoreCase(PaginationConstants.SORT_DIRECTION_DESC)) {
            throw new InvalidSortDirectionException(ExceptionConstants.INVALID_SORT_DIRECTION_MESSAGE);
        }
    }
}
