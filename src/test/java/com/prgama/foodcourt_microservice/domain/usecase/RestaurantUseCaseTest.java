package com.prgama.foodcourt_microservice.domain.usecase;

import com.prgama.foodcourt_microservice.domain.api.IUserServicePort;
import com.prgama.foodcourt_microservice.domain.constants.ExceptionConstants;
import com.prgama.foodcourt_microservice.domain.exception.*;
import com.prgama.foodcourt_microservice.domain.model.Pagination;
import com.prgama.foodcourt_microservice.domain.model.Restaurant;
import com.prgama.foodcourt_microservice.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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
    @DisplayName("Validation exception when restaurant already exists by nit")
    void createRestaurantShouldThrowValidationExceptionWhenAlreadyExistsByNit() {
        Restaurant restaurant = new Restaurant(1L, "Frisby", "123456789", "Parque Arboleda Piso 4",
                "+573233039679", "https://logo.url", 1L);
        Mockito.when(restaurantPersistencePort.alreadyExistsByNit("123456789")).thenReturn(true);
        AlreadyExistsByNitException exception = assertThrows(AlreadyExistsByNitException.class, () -> {
            restaurantUseCase.createRestaurant(restaurant);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ALREADY_EXISTS_BY_NIT_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.never()).createRestaurant(restaurant);
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

    @Test
    @DisplayName("List restaurants correctly")
    void listRestaurants() {
        Restaurant restaurant = new Restaurant(1L, "Frisby", "123456789", "Parque Arboleda Piso 4",
                "+573233039679", "https://logo.url", 1L);
        Pagination<Restaurant> pagination = new Pagination<>(List.of(restaurant), 0, 10, 1L);

        Mockito.when(restaurantPersistencePort.listRestaurants(0, 10, "name", "asc")).thenReturn(pagination);

        Pagination<Restaurant> result = restaurantUseCase.listRestaurants(0, 10, "asc");

        assertNotNull(result, "The result shouldn't be null.");
        assertFalse(result.getContent().isEmpty(), "The content shouldn't be empty.");
        assertEquals(1, result.getContent().size(), "The number of restaurants should be 1.");

        Restaurant returnedRestaurant = result.getContent().get(0);
        assertEquals("Frisby", returnedRestaurant.getName(), "The restaurant's name should be Frisby.");
        assertEquals("https://logo.url", returnedRestaurant.getUrlLogo(), "The restaurant's url logo should be https://logo.url");

        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).listRestaurants(0, 10, "name", "asc");
    }

    @Test
    @DisplayName("Validation exception when page number is null")
    void listRestaurantsShouldThrowValidationExceptionWhenPageNumberIsNull() {
        InvalidPageNumberException exception = assertThrows(InvalidPageNumberException.class, () -> {
            restaurantUseCase.listRestaurants(null, 10, "asc");
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.PAGE_NUMBER_MANDATORY_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.never()).listRestaurants(null, 10, "name", "asc");
    }

    @Test
    @DisplayName("Validation exception when page number is a negative number")
    void listRestaurantsShouldThrowValidationExceptionWhenPageNumberIsNegative() {
        InvalidPageNumberException exception = assertThrows(InvalidPageNumberException.class, () -> {
            restaurantUseCase.listRestaurants(-1, 10, "asc");
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_PAGE_NUMBER_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.never()).listRestaurants(-1, 10, "name", "asc");
    }

    @Test
    @DisplayName("Validation exception when page size is null")
    void listRestaurantsShouldThrowValidationExceptionWhenPageSizeIsNull() {
        InvalidPageSizeException exception = assertThrows(InvalidPageSizeException.class, () -> {
            restaurantUseCase.listRestaurants(0, null, "asc");
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.PAGE_SIZE_MANDATORY_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.never()).listRestaurants(0, null, "name", "asc");
    }

    @Test
    @DisplayName("Validation exception when page size is less than or equal to zero")
    void listRestaurantsShouldThrowValidationExceptionWhenPageSizeIsLessThanOrEqualToZero() {
        InvalidPageSizeException exception = assertThrows(InvalidPageSizeException.class, () -> {
            restaurantUseCase.listRestaurants(0, 0, "asc");
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_PAGE_SIZE_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.never()).listRestaurants(0, 0, "name", "asc");
    }

    @Test
    @DisplayName("Validation exception when sort direction isn't 'asc' or 'desc'")
    void listRestaurantsShouldThrowValidationExceptionWhenSortDirectionIsNotAscOrDesc() {
        InvalidSortDirectionException exception = assertThrows(InvalidSortDirectionException.class, () -> {
            restaurantUseCase.listRestaurants(0, 10, "order");
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_SORT_DIRECTION_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.never()).listRestaurants(0, 10, "name", "order");
    }

    @Test
    @DisplayName("Validation when owner is creating an employee. In this case restaurant exists and belongs to the owner creating the employee")
    void getRestaurantByIdReturnsTrueWhenRestaurantBelongsToOwner() {
        Long restaurantId = 1L;
        Long ownerId = 10L;
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, 10L);
        Mockito.when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(restaurant);

        boolean result = restaurantUseCase.getRestaurantById(restaurantId, ownerId);

        assertTrue(result);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).getRestaurant(restaurantId);
    }

    @Test
    @DisplayName("Validation when owner is creating an employee. In this case restaurant exists, but doesn't belongs to the owner creating the employee")
    void getRestaurantByIdReturnsFalseWhenRestaurantDoesNotBelongToOwner() {
        Long restaurantId = 1L;
        Long ownerId = 10L;
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, 20L);
        Mockito.when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(restaurant);

        boolean result = restaurantUseCase.getRestaurantById(restaurantId, ownerId);

        assertFalse(result);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).getRestaurant(restaurantId);
    }

    @Test
    @DisplayName("Validation when owner is creating an employee. In this case restaurant doesn't exists")
    void getRestaurantByIdReturnsFalseWhenRestaurantDoesNotExists() {
        Long restaurantId = 1L;
        Long ownerId = 10L;
        Mockito.when(restaurantPersistencePort.getRestaurant(restaurantId)).thenReturn(null);

        boolean result = restaurantUseCase.getRestaurantById(restaurantId, ownerId);

        assertFalse(result);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).getRestaurant(restaurantId);
    }
}