package com.prgama.foodcourt_microservice.domain.usecase;

import com.prgama.foodcourt_microservice.application.dto.request.GetUserRequest;
import com.prgama.foodcourt_microservice.domain.api.IUserServicePort;
import com.prgama.foodcourt_microservice.domain.constants.ExceptionConstants;
import com.prgama.foodcourt_microservice.domain.exception.*;
import com.prgama.foodcourt_microservice.domain.model.Restaurant;
import com.prgama.foodcourt_microservice.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RestaurantUseCaseTest {
    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @Mock
    private IUserServicePort userServicePort;

    @InjectMocks
    private RestaurantUseCase restaurantUseCase;

    @Test
    @DisplayName("Inserts a restaurant in the DB")
    void createRestaurant() {
        Restaurant restaurant = new Restaurant(1L, "Frisby", "123456789", "Parque Arboleda Piso 4",
                "+573233039679", "https://logo.url", 1L);
        Mockito.when(userServicePort.getUserById(1L)).thenReturn(true);
        restaurantUseCase.createRestaurant(restaurant);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).createRestaurant(restaurant);
    }

    @Test
    @DisplayName("Validation exception when provided owner's id isn't an owner")
    void createRestaurantShouldThrowValidationExceptionWhenUserIsNotOwner() {
        Restaurant restaurant = new Restaurant(1L, "Frisby", "123456789", "Parque Arboleda Piso 4",
                "+573233039679", "https://logo.url", 1L);
        Mockito.when(userServicePort.getUserById(1L)).thenReturn(false);
        NotOwnerException exception = assertThrows(NotOwnerException.class, () -> {
            restaurantUseCase.createRestaurant(restaurant);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.NOT_OWNER_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.never()).createRestaurant(restaurant);
    }
}