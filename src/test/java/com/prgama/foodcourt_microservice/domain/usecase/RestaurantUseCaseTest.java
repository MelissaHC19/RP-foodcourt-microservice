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
        GetUserRequest owner = new GetUserRequest(1L, "Melissa", "Henao",
                "1004738846", "573205898802", LocalDate.parse("2001-05-19"),
                "melissahenao19@gmail.com", "owner123", "Owner");
        Mockito.when(userServicePort.getUserById(1L)).thenReturn(owner);
        restaurantUseCase.createRestaurant(restaurant);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).createRestaurant(restaurant);
    }

    @Test
    @DisplayName("Validation exception when name field is empty")
    void createRestaurantShouldThrowValidationExceptionWhenNameIsEmpty() {
        Restaurant restaurant = new Restaurant(1L, "", "123456789", "Parque Arboleda Piso 4",
                "+573233039679", "https://logo.url", 1L);
        EmptyOrNullFieldsException exception = assertThrows(EmptyOrNullFieldsException.class, () -> {
            restaurantUseCase.createRestaurant(restaurant);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.NAME_MANDATORY_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.never()).createRestaurant(restaurant);
    }

    @Test
    @DisplayName("Validation exception when NIT field is empty")
    void createRestaurantShouldThrowValidationExceptionWhenNitIsEmpty() {
        Restaurant restaurant = new Restaurant(1L, "Frisby", "", "Parque Arboleda Piso 4",
                "+573233039679", "https://logo.url", 1L);
        EmptyOrNullFieldsException exception = assertThrows(EmptyOrNullFieldsException.class, () -> {
            restaurantUseCase.createRestaurant(restaurant);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.NIT_MANDATORY_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.never()).createRestaurant(restaurant);
    }

    @Test
    @DisplayName("Validation exception when address field is empty")
    void createRestaurantShouldThrowValidationExceptionWhenAddressIsEmpty() {
        Restaurant restaurant = new Restaurant(1L, "Frisby", "123456789", "",
                "+573233039679", "https://logo.url", 1L);
        EmptyOrNullFieldsException exception = assertThrows(EmptyOrNullFieldsException.class, () -> {
            restaurantUseCase.createRestaurant(restaurant);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ADDRESS_MANDATORY_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.never()).createRestaurant(restaurant);
    }

    @Test
    @DisplayName("Validation exception when phone number field is empty")
    void createRestaurantShouldThrowValidationExceptionWhenPhoneNumberIsEmpty() {
        Restaurant restaurant = new Restaurant(1L, "Frisby", "123456789", "Parque Arboleda Piso 4",
                "", "https://logo.url", 1L);
        EmptyOrNullFieldsException exception = assertThrows(EmptyOrNullFieldsException.class, () -> {
            restaurantUseCase.createRestaurant(restaurant);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.PHONE_NUMBER_MANDATORY_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.never()).createRestaurant(restaurant);
    }

    @Test
    @DisplayName("Validation exception when url logo field is empty")
    void createRestaurantShouldThrowValidationExceptionWhenUrlLogoIsEmpty() {
        Restaurant restaurant = new Restaurant(1L, "Frisby", "123456789", "Parque Arboleda Piso 4",
                "+573233039679", "", 1L);
        EmptyOrNullFieldsException exception = assertThrows(EmptyOrNullFieldsException.class, () -> {
            restaurantUseCase.createRestaurant(restaurant);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.URL_LOGO_MANDATORY_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.never()).createRestaurant(restaurant);
    }

    @Test
    @DisplayName("Validation exception when owner's id field is null")
    void createRestaurantShouldThrowValidationExceptionWhenOwnerIdIsNull() {
        Restaurant restaurant = new Restaurant(1L, "Frisby", "123456789", "Parque Arboleda Piso 4",
                "+573233039679", "https://logo.url", null);
        EmptyOrNullFieldsException exception = assertThrows(EmptyOrNullFieldsException.class, () -> {
            restaurantUseCase.createRestaurant(restaurant);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.OWNER_ID_MANDATORY_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.never()).createRestaurant(restaurant);
    }

    @Test
    @DisplayName("Validation exception when owner doesn't exist")
    void createRestaurantShouldThrowValidationExceptionWhenOwnerNotFound() {
        Restaurant restaurant = new Restaurant(1L, "Frisby", "123456789", "Parque Arboleda Piso 4",
                "+573233039679", "https://logo.url", 25L);
        Mockito.when(userServicePort.getUserById(25L)).thenReturn(null);
        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            restaurantUseCase.createRestaurant(restaurant);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.OWNER_NOT_FOUND_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.never()).createRestaurant(restaurant);
    }

    @Test
    @DisplayName("Validation exception when provided owner's id isn't an owner")
    void createRestaurantShouldThrowValidationExceptionWhenUserIsNotOwner() {
        Restaurant restaurant = new Restaurant(1L, "Frisby", "123456789", "Parque Arboleda Piso 4",
                "+573233039679", "https://logo.url", 1L);
        GetUserRequest notOwner = new GetUserRequest(1L, "Melissa", "Henao",
                "1004738846", "573205898802", LocalDate.parse("2001-05-19"),
                "melissahenao19@gmail.com", "owner123", "Admin");
        Mockito.when(userServicePort.getUserById(1L)).thenReturn(notOwner);
        NotOwnerException exception = assertThrows(NotOwnerException.class, () -> {
            restaurantUseCase.createRestaurant(restaurant);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.NOT_OWNER_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.never()).createRestaurant(restaurant);
    }

    @Test
    @DisplayName("Validation exception when name is numeric only")
    void createRestaurantShouldThrowValidationExceptionWhenNameIsInvalid() {
        Restaurant restaurant = new Restaurant(1L, "12345", "123456789", "Parque Arboleda Piso 4",
                "+573233039679", "https://logo.url", 1L);
        GetUserRequest owner = new GetUserRequest(1L, "Melissa", "Henao",
                "1004738846", "573205898802", LocalDate.parse("2001-05-19"),
                "melissahenao19@gmail.com", "owner123", "Owner");
        Mockito.when(userServicePort.getUserById(1L)).thenReturn(owner);
        InvalidNameException exception = assertThrows(InvalidNameException.class, () -> {
            restaurantUseCase.createRestaurant(restaurant);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_NAME_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.never()).createRestaurant(restaurant);
    }

    @Test
    @DisplayName("Validation exception when NIT isn't numeric only")
    void createRestaurantShouldThrowValidationExceptionWhenNitIsInvalid() {
        Restaurant restaurant = new Restaurant(1L, "Frisby", "123.456.789", "Parque Arboleda Piso 4",
                "+573233039679", "https://logo.url", 1L);
        GetUserRequest owner = new GetUserRequest(1L, "Melissa", "Henao",
                "1004738846", "573205898802", LocalDate.parse("2001-05-19"),
                "melissahenao19@gmail.com", "owner123", "Owner");
        Mockito.when(userServicePort.getUserById(1L)).thenReturn(owner);
        InvalidNitException exception = assertThrows(InvalidNitException.class, () -> {
            restaurantUseCase.createRestaurant(restaurant);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_NIT_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.never()).createRestaurant(restaurant);
    }

    @Test
    @DisplayName("Validation exception when phone number is invalid")
    void createRestaurantShouldThrowValidationExceptionWhenPhoneNumberIsInvalid() {
        Restaurant restaurant = new Restaurant(1L, "Frisby", "123456789", "Parque Arboleda Piso 4",
                "+3233039679", "https://logo.url", 1L);
        GetUserRequest owner = new GetUserRequest(1L, "Melissa", "Henao",
                "1004738846", "573205898802", LocalDate.parse("2001-05-19"),
                "melissahenao19@gmail.com", "owner123", "Owner");
        Mockito.when(userServicePort.getUserById(1L)).thenReturn(owner);
        InvalidPhoneNumberException exception = assertThrows(InvalidPhoneNumberException.class, () -> {
            restaurantUseCase.createRestaurant(restaurant);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_PHONE_NUMBER_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.never()).createRestaurant(restaurant);
    }
}