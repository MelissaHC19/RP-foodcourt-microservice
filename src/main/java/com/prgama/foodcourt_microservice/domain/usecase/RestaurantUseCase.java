package com.prgama.foodcourt_microservice.domain.usecase;

import com.prgama.foodcourt_microservice.domain.api.IRestaurantServicePort;
import com.prgama.foodcourt_microservice.domain.constants.ExceptionConstants;
import com.prgama.foodcourt_microservice.domain.constants.UseCaseConstants;
import com.prgama.foodcourt_microservice.domain.exception.EmptyOrNullFieldsException;
import com.prgama.foodcourt_microservice.domain.exception.InvalidNameException;
import com.prgama.foodcourt_microservice.domain.exception.InvalidNitException;
import com.prgama.foodcourt_microservice.domain.exception.InvalidPhoneNumberException;
import com.prgama.foodcourt_microservice.domain.model.Restaurant;
import com.prgama.foodcourt_microservice.domain.spi.IRestaurantPersistencePort;

public class RestaurantUseCase implements IRestaurantServicePort {
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public RestaurantUseCase(IRestaurantPersistencePort restaurantPersistencePort) {
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void createRestaurant(Restaurant restaurant) {
        validateMandatoryFields(restaurant);
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
}
