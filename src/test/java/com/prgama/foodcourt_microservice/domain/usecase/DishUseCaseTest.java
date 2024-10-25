package com.prgama.foodcourt_microservice.domain.usecase;

import com.prgama.foodcourt_microservice.domain.constants.ExceptionConstants;
import com.prgama.foodcourt_microservice.domain.exception.*;
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
        Long ownerIdPost = 10L;
        Dish dish = new Dish(1L, "Crispy Calamari",
                "Lightly breaded calamari rings, served with a tangy marinara sauce.",
                45000, "crispy-calamari.jpg",
                new Restaurant(1L, null, null, null, null, null, null),
                new Category(1L, null, null));
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, 10L);
        Mockito.when(dishPersistencePort.alreadyExistsByName("Crispy Calamari")).thenReturn(false);
        Mockito.when(categoryPersistencePort.alreadyExistsById(1L)).thenReturn(true);
        Mockito.when(restaurantPersistencePort.alreadyExistsById(1L)).thenReturn(true);
        Mockito.when(restaurantPersistencePort.getRestaurant(1L)).thenReturn(restaurant);
        dishUseCase.createDish(ownerIdPost, dish);
        Mockito.verify(dishPersistencePort, Mockito.times(1)).createDish(dish);
        Mockito.verify(dishPersistencePort, Mockito.times(1)).alreadyExistsByName("Crispy Calamari");
        Mockito.verify(categoryPersistencePort, Mockito.times(1)).alreadyExistsById(1L);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).alreadyExistsById(1L);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).getRestaurant(1L);
    }

    @Test
    @DisplayName("Validation exception when dish already exists by name in the DB")
    void createDishShouldThrowValidationExceptionWhenDishAlreadyExistsByName() {
        Long ownerIdPost = 10L;
        Dish dish = new Dish(1L, "Crispy Calamari",
                "Lightly breaded calamari rings, served with a tangy marinara sauce.",
                45000, "crispy-calamari.jpg",
                new Restaurant(1L, null, null, null, null, null, null),
                new Category(1L, null, null));
        Mockito.when(dishPersistencePort.alreadyExistsByName("Crispy Calamari")).thenReturn(true);
        AlreadyExistsByNameException exception = assertThrows(AlreadyExistsByNameException.class, () -> {
            dishUseCase.createDish(ownerIdPost, dish);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.ALREADY_EXISTS_BY_NAME_MESSAGE);
        Mockito.verify(dishPersistencePort, Mockito.never()).createDish(dish);
    }

    @Test
    @DisplayName("Validation exception when restaurant doesn't exists")
    void createDishShouldThrowValidationExceptionWhenRestaurantNotFound() {
        Long ownerIdPost = 10L;
        Dish dish = new Dish(1L, "Crispy Calamari",
                "Lightly breaded calamari rings, served with a tangy marinara sauce.",
                45000, "crispy-calamari.jpg",
                new Restaurant(1L, null, null, null, null, null, null),
                new Category(1L, null, null));
        Mockito.when(restaurantPersistencePort.alreadyExistsById(1L)).thenReturn(false);
        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class, () -> {
            dishUseCase.createDish(ownerIdPost, dish);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.RESTAURANT_NOT_FOUND_MESSAGE);
        Mockito.verify(dishPersistencePort, Mockito.never()).createDish(dish);
    }

    @Test
    @DisplayName("Validation exception when category doesn't exists")
    void createDishShouldThrowValidationExceptionWhenCategoryNotFound() {
        Long ownerIdPost = 10L;
        Dish dish = new Dish(1L, "Crispy Calamari",
                "Lightly breaded calamari rings, served with a tangy marinara sauce.",
                45000, "crispy-calamari.jpg",
                new Restaurant(1L, null, null, null, null, null, null),
                new Category(1L, null, null));
        Mockito.when(restaurantPersistencePort.alreadyExistsById(1L)).thenReturn(true);
        Mockito.when(categoryPersistencePort.alreadyExistsById(1L)).thenReturn(false);
        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> {
            dishUseCase.createDish(ownerIdPost, dish);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.CATEGORY_NOT_FOUND_MESSAGE);
        Mockito.verify(dishPersistencePort, Mockito.never()).createDish(dish);
    }

    @Test
    @DisplayName("Validation exception when user trying to create dish isn't the owner of the restaurant he's trying to modify")
    void createDishShouldThrowValidationExceptionWhenUserNotAuthorizedOwner() {
        Long ownerIdPost = 12L;
        Dish dish = new Dish(1L, "Crispy Calamari",
                "Lightly breaded calamari rings, served with a tangy marinara sauce.",
                45000, "crispy-calamari.jpg",
                new Restaurant(1L, null, null, null, null, null, null),
                new Category(1L, null, null));
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, 10L);
        Mockito.when(restaurantPersistencePort.alreadyExistsById(1L)).thenReturn(true);
        Mockito.when(categoryPersistencePort.alreadyExistsById(1L)).thenReturn(true);
        Mockito.when(restaurantPersistencePort.getRestaurant(1L)).thenReturn(restaurant);
        UnauthorizedOwnerException exception = assertThrows(UnauthorizedOwnerException.class, () -> {
            dishUseCase.createDish(ownerIdPost, dish);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.UNAUTHORIZED_OWNER_MESSAGE);
        Mockito.verify(dishPersistencePort, Mockito.never()).createDish(dish);
    }

    @Test
    @DisplayName("Modifies description, price, and dish status from successfully")
    void modifyDishDescriptionPriceAndStatus() {
        Long ownerIdPost = 10L;
        Long dishId = 1L;
        Dish dish = new Dish(1L, "Crispy Calamari",
                "Lightly breaded calamari rings, served with a tangy marinara sauce.",
                45000, "crispy-calamari.jpg",
                new Restaurant(1L, null, null, null, null, null, null),
                new Category(1L, null, null));
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, 10L);
        Mockito.when(dishPersistencePort.findById(dishId)).thenReturn(dish);
        Mockito.when(restaurantPersistencePort.getRestaurant(1L)).thenReturn(restaurant);
        dishUseCase.modifyDish(dishId, "Lightly breaded calamari rings, served with a delicious marinara sauce.", 50000, true, ownerIdPost);
        Mockito.verify(dishPersistencePort, Mockito.times(1)).modifyDish(dish);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).getRestaurant(1L);
    }

    @Test
    @DisplayName("Modifies description from a dish successfully")
    void modifyDishDescription() {
        Long ownerIdPost = 10L;
        Long dishId = 1L;
        Dish dish = new Dish(1L, "Crispy Calamari",
                "Lightly breaded calamari rings, served with a tangy marinara sauce.",
                45000, "crispy-calamari.jpg",
                new Restaurant(1L, null, null, null, null, null, null),
                new Category(1L, null, null));
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, 10L);
        Mockito.when(dishPersistencePort.findById(dishId)).thenReturn(dish);
        Mockito.when(restaurantPersistencePort.getRestaurant(1L)).thenReturn(restaurant);
        dishUseCase.modifyDish(dishId, "Lightly breaded calamari rings, served with a delicious marinara sauce.", null, null, ownerIdPost);
        Mockito.verify(dishPersistencePort, Mockito.times(1)).modifyDish(dish);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).getRestaurant(1L);
    }

    @Test
    @DisplayName("Modifies price from a dish successfully")
    void modifyDishPrice() {
        Long ownerIdPost = 10L;
        Long dishId = 1L;
        Dish dish = new Dish(1L, "Crispy Calamari",
                "Lightly breaded calamari rings, served with a tangy marinara sauce.",
                45000, "crispy-calamari.jpg",
                new Restaurant(1L, null, null, null, null, null, null),
                new Category(1L, null, null));
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, 10L);
        Mockito.when(dishPersistencePort.findById(dishId)).thenReturn(dish);
        Mockito.when(restaurantPersistencePort.getRestaurant(1L)).thenReturn(restaurant);
        dishUseCase.modifyDish(dishId, null, 50000, null, ownerIdPost);
        Mockito.verify(dishPersistencePort, Mockito.times(1)).modifyDish(dish);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).getRestaurant(1L);
    }

    @Test
    @DisplayName("Modifies dish status successfully")
    void modifyDishStatus() {
        Long ownerIdPost = 10L;
        Long dishId = 1L;
        Dish dish = new Dish(1L, "Crispy Calamari",
                "Lightly breaded calamari rings, served with a tangy marinara sauce.",
                45000, "crispy-calamari.jpg",
                new Restaurant(1L, null, null, null, null, null, null),
                new Category(1L, null, null));
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, 10L);
        Mockito.when(dishPersistencePort.findById(dishId)).thenReturn(dish);
        Mockito.when(restaurantPersistencePort.getRestaurant(1L)).thenReturn(restaurant);
        dishUseCase.modifyDish(dishId, null, null, false, ownerIdPost);
        Mockito.verify(dishPersistencePort, Mockito.times(1)).modifyDish(dish);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).getRestaurant(1L);
    }

    @Test
    @DisplayName("Shouldn't modify dish when description, price, and dish status are null")
    void modifyDishShouldNotModifyWhenDescriptionPriceAndStatusAreNull() {
        Long ownerIdPost = 10L;
        Long dishId = 1L;
        Dish dish = new Dish(1L, "Crispy Calamari",
                "Lightly breaded calamari rings, served with a tangy marinara sauce.",
                45000, "crispy-calamari.jpg",
                new Restaurant(1L, null, null, null, null, null, null),
                new Category(1L, null, null));
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, 10L);
        Mockito.when(dishPersistencePort.findById(dishId)).thenReturn(dish);
        Mockito.when(restaurantPersistencePort.getRestaurant(1L)).thenReturn(restaurant);
        dishUseCase.modifyDish(dishId, null, null, null, ownerIdPost);
        Mockito.verify(dishPersistencePort, Mockito.never()).modifyDish(dish);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).getRestaurant(1L);
    }

    @Test
    @DisplayName("Validation exception when dish doesn't exist")
    void modifyDishShouldThrowValidationExceptionWhenDishNotFound() {
        Long ownerIdPost = 10L;
        Long dishId = 1L;
        Mockito.when(dishPersistencePort.findById(dishId)).thenReturn(null);
        DishNotFoundException exception = assertThrows(DishNotFoundException.class, () -> {
            dishUseCase.modifyDish(dishId, "Lightly breaded calamari rings, served with a delicious marinara sauce.", 50000, true, ownerIdPost);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.DISH_NOT_FOUND_MESSAGE);
        Mockito.verify(dishPersistencePort, Mockito.never()).modifyDish(null);
    }

    @Test
    @DisplayName("Validation exception when user trying to modify dish isn't the owner of the restaurant he's trying to modify")
    void modifyDishShouldThrowValidationExceptionWhenUserNotAuthorizedOwner() {
        Long ownerIdPost = 13L;
        Long dishId = 1L;
        Dish dish = new Dish(1L, "Crispy Calamari",
                "Lightly breaded calamari rings, served with a tangy marinara sauce.",
                45000, "crispy-calamari.jpg",
                new Restaurant(1L, null, null, null, null, null, null),
                new Category(1L, null, null));
        Restaurant restaurant = new Restaurant(1L, null, null, null, null, null, 10L);
        Mockito.when(dishPersistencePort.findById(dishId)).thenReturn(dish);
        Mockito.when(restaurantPersistencePort.getRestaurant(1L)).thenReturn(restaurant);
        UnauthorizedOwnerException exception = assertThrows(UnauthorizedOwnerException.class, () -> {
            dishUseCase.modifyDish(dishId, "Lightly breaded calamari rings, served with a delicious marinara sauce.", 50000, true, ownerIdPost);
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.UNAUTHORIZED_OWNER_MESSAGE);
        Mockito.verify(dishPersistencePort, Mockito.never()).modifyDish(null);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).getRestaurant(1L);
    }
}