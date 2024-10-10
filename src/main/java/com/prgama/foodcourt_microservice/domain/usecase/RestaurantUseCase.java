package com.prgama.foodcourt_microservice.domain.usecase;

import com.prgama.foodcourt_microservice.application.dto.request.GetUserRequest;
import com.prgama.foodcourt_microservice.domain.api.IRestaurantServicePort;
import com.prgama.foodcourt_microservice.domain.api.IUserServicePort;
import com.prgama.foodcourt_microservice.domain.constants.ExceptionConstants;
import com.prgama.foodcourt_microservice.domain.constants.UseCaseConstants;
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
        validateMandatoryFields(restaurant);
        validateOwner(restaurant);
        validateRestaurant(restaurant);
        restaurantPersistencePort.createRestaurant(restaurant);
    }

    private void validateMandatoryFields(Restaurant restaurant) {
        if (restaurant.getName().trim().isEmpty()) {
            throw new EmptyOrNullFieldsException(ExceptionConstants.NAME_MANDATORY_MESSAGE);
        }
        if (restaurant.getNit().trim().isEmpty()) {
            throw new EmptyOrNullFieldsException(ExceptionConstants.NIT_MANDATORY_MESSAGE);
        }
        if (restaurant.getAddress().trim().isEmpty()) {
            throw new EmptyOrNullFieldsException(ExceptionConstants.ADDRESS_MANDATORY_MESSAGE);
        }
        if (restaurant.getPhoneNumber().trim().isEmpty()) {
            throw new EmptyOrNullFieldsException(ExceptionConstants.PHONE_NUMBER_MANDATORY_MESSAGE);
        }
        if (restaurant.getUrlLogo().trim().isEmpty()) {
            throw new EmptyOrNullFieldsException(ExceptionConstants.URL_LOGO_MANDATORY_MESSAGE);
        }
        if (restaurant.getOwnerId() == null) {
            throw new EmptyOrNullFieldsException(ExceptionConstants.OWNER_ID_MANDATORY_MESSAGE);
        }
    }

    private void validateRestaurant(Restaurant restaurant) {
        if (!restaurant.getName().matches(UseCaseConstants.NAME_REGULAR_EXPRESSION)) {
            throw new InvalidNameException(ExceptionConstants.INVALID_NAME_MESSAGE);
        }
        if (!restaurant.getNit().matches(UseCaseConstants.NIT_REGULAR_EXPRESSION)) {
            throw new InvalidNitException(ExceptionConstants.INVALID_NIT_MESSAGE);
        }
        if (!restaurant.getPhoneNumber().matches(UseCaseConstants.PHONE_NUMBER_REGULAR_EXPRESSION)) {
            throw new InvalidPhoneNumberException(ExceptionConstants.INVALID_PHONE_NUMBER_MESSAGE);
        }
    }

    private void validateOwner(Restaurant restaurant) {
        GetUserRequest getUserRequest = userServicePort.getUserById(restaurant.getOwnerId());

        if (getUserRequest == null) {
            throw new UserNotFoundException(ExceptionConstants.OWNER_NOT_FOUND_MESSAGE);
        } else if (!getUserRequest.getRole().equals(UseCaseConstants.OWNER_ROLE)) {
            throw new NotOwnerException(ExceptionConstants.NOT_OWNER_MESSAGE);
        }
    }
}
