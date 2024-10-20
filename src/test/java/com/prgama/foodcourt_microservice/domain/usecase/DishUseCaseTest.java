package com.prgama.foodcourt_microservice.domain.usecase;

import com.prgama.foodcourt_microservice.domain.constants.ExceptionConstants;
import com.prgama.foodcourt_microservice.domain.exception.AlreadyExistsByNameException;
import com.prgama.foodcourt_microservice.domain.exception.CategoryNotFoundException;
import com.prgama.foodcourt_microservice.domain.exception.RestaurantNotFoundException;
import com.prgama.foodcourt_microservice.domain.model.Category;
import com.prgama.foodcourt_microservice.domain.model.Dish;
import com.prgama.foodcourt_microservice.domain.model.Restaurant;
import com.prgama.foodcourt_microservice.domain.spi.ICategoryPersistencePort;
import com.prgama.foodcourt_microservice.domain.spi.IDishPersistencePort;
import com.prgama.foodcourt_microservice.domain.spi.IRestaurantPersistencePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DishUseCaseTest {
    @Mock
    private IDishPersistencePort dishPersistencePort;

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @Mock
    private IRestaurantPersistencePort restaurantPersistencePort;

    @InjectMocks
    private DishUseCase dishUseCase;

    @Test
    @DisplayName("Inserts a dish in the DB")
    void createDish() {
        Dish dish = new Dish(1L, "Crispy Calamari",
                "Lightly breaded calamari rings, served with a tangy marinara sauce.",
                45000, "crispy-calamari.jpg",
                new Restaurant(1L, null, null, null, null, null, null),
                new Category(1L, null, null));
        Mockito.when(dishPersistencePort.alreadyExistsByName("Crispy Calamari")).thenReturn(false);
        Mockito.when(categoryPersistencePort.alreadyExistsById(1L)).thenReturn(true);
        Mockito.when(restaurantPersistencePort.alreadyExistsById(1L)).thenReturn(true);
        dishUseCase.createDish(dish);
        Mockito.verify(dishPersistencePort, Mockito.times(1)).createDish(dish);
        Mockito.verify(dishPersistencePort, Mockito.times(1)).alreadyExistsByName("Crispy Calamari");
        Mockito.verify(categoryPersistencePort, Mockito.times(1)).alreadyExistsById(1L);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).alreadyExistsById(1L);
    }

    @Test
    @DisplayName("Validation exception when dish already exists by name in the DB")
    void createDishShouldThrowValidationExceptionWhenDishAlreadyExistsByName() {
        Dish dish = new Dish(1L, "Crispy Calamari",
                "Lightly breaded calamari rings, served with a tangy marinara sauce.",
                45000, "crispy-calamari.jpg",
                new Restaurant(1L, null, null, null, null, null, null),
                new Category(1L, null, null));
        Mockito.when(dishPersistencePort.alreadyExistsByName("Crispy Calamari")).thenReturn(true);
        AlreadyExistsByNameException exception = assertThrows(AlreadyExistsByNameException.class, () -> {
            dishUseCase.createDish(dish);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ALREADY_EXISTS_BY_NAME_MESSAGE);
        Mockito.verify(dishPersistencePort, Mockito.never()).createDish(dish);
    }

    @Test
    @DisplayName("Validation exception when restaurant doesn't exists")
    void createDishShouldThrowValidationExceptionWhenRestaurantNotFound() {
        Dish dish = new Dish(1L, "Crispy Calamari",
                "Lightly breaded calamari rings, served with a tangy marinara sauce.",
                45000, "crispy-calamari.jpg",
                new Restaurant(1L, null, null, null, null, null, null),
                new Category(1L, null, null));
        Mockito.when(restaurantPersistencePort.alreadyExistsById(1L)).thenReturn(false);
        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class, () -> {
            dishUseCase.createDish(dish);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.RESTAURANT_NOT_FOUND_MESSAGE);
        Mockito.verify(dishPersistencePort, Mockito.never()).createDish(dish);
    }

    @Test
    @DisplayName("Validation exception when category doesn't exists")
    void createDishShouldThrowValidationExceptionWhenCategoryNotFound() {
        Dish dish = new Dish(1L, "Crispy Calamari",
                "Lightly breaded calamari rings, served with a tangy marinara sauce.",
                45000, "crispy-calamari.jpg",
                new Restaurant(1L, null, null, null, null, null, null),
                new Category(1L, null, null));
        Mockito.when(restaurantPersistencePort.alreadyExistsById(1L)).thenReturn(true);
        Mockito.when(categoryPersistencePort.alreadyExistsById(1L)).thenReturn(false);
        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> {
            dishUseCase.createDish(dish);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.CATEGORY_NOT_FOUND_MESSAGE);
        Mockito.verify(dishPersistencePort, Mockito.never()).createDish(dish);
    }
}