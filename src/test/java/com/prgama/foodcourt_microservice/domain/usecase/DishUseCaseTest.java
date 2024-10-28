package com.prgama.foodcourt_microservice.domain.usecase;

import com.prgama.foodcourt_microservice.domain.constants.ExceptionConstants;
import com.prgama.foodcourt_microservice.domain.exception.*;
import com.prgama.foodcourt_microservice.domain.model.Category;
import com.prgama.foodcourt_microservice.domain.model.Dish;
import com.prgama.foodcourt_microservice.domain.model.Pagination;
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

import java.util.List;

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

    @Test
    @DisplayName("List dishes by restaurant and category correctly")
    void listDishesByRestaurantAndCategory() {
        Dish dish = new Dish(1L, "Crispy Calamari",
                "Lightly breaded calamari rings, served with a tangy marinara sauce.",
                45000, "crispy-calamari.jpg",
                new Restaurant(1L, null, null, null, null, null, null),
                new Category(2L, "Appetizers", null));
        Pagination<Dish> pagination = new Pagination<>(List.of(dish), 0, 10, 1L);

        Mockito.when(restaurantPersistencePort.alreadyExistsById(1L)).thenReturn(true);
        Mockito.when(categoryPersistencePort.alreadyExistsById(2L)).thenReturn(true);
        Mockito.when(dishPersistencePort.listDishesByRestaurantAndCategory(1L, 2L, 0, 10, "name", "asc")).thenReturn(pagination);

        Pagination<Dish> result = dishUseCase.listDishesByRestaurantAndCategory(1L, 2L, 0, 10, "asc");

        assertNotNull(result, "The result shouldn't be null.");
        assertFalse(result.getContent().isEmpty(), "The content shouldn't be empty.");
        assertEquals(1, result.getContent().size(), "The number of dishes should be 1.");

        Dish returnedDish = result.getContent().get(0);
        assertEquals("Crispy Calamari", returnedDish.getName(), "The dish's name should be Crispy Calamari.");
        assertEquals("Lightly breaded calamari rings, served with a tangy marinara sauce.", returnedDish.getDescription(), "The dish's description should be 'Lightly breaded calamari rings, served with a tangy marinara sauce.'");
        assertEquals(45000, returnedDish.getPrice(), "The dish's price should be 45000");
        assertEquals("crispy-calamari.jpg", returnedDish.getUrlImage(), "The dish's url image should be crispy-calamari.jpg");
        assertEquals("Appetizers", returnedDish.getCategory().getName(), "The dish's category should be Appetizers");

        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).alreadyExistsById(1L);
        Mockito.verify(categoryPersistencePort, Mockito.times(1)).alreadyExistsById(2L);
        Mockito.verify(dishPersistencePort, Mockito.times(1)).listDishesByRestaurantAndCategory(1L, 2L, 0, 10, "name", "asc");
    }

    @Test
    @DisplayName("List dishes by restaurant correctly")
    void listDishesByRestaurant() {
        Dish dish = new Dish(1L, "Crispy Calamari",
                "Lightly breaded calamari rings, served with a tangy marinara sauce.",
                45000, "crispy-calamari.jpg",
                new Restaurant(1L, null, null, null, null, null, null),
                new Category(2L, "Appetizers", null));
        Pagination<Dish> pagination = new Pagination<>(List.of(dish), 0, 10, 1L);

        Mockito.when(restaurantPersistencePort.alreadyExistsById(1L)).thenReturn(true);
        Mockito.when(dishPersistencePort.listDishesByRestaurantAndCategory(1L, null, 0, 10, "name", "asc")).thenReturn(pagination);

        Pagination<Dish> result = dishUseCase.listDishesByRestaurantAndCategory(1L, null, 0, 10, "asc");

        assertNotNull(result, "The result shouldn't be null.");
        assertFalse(result.getContent().isEmpty(), "The content shouldn't be empty.");
        assertEquals(1, result.getContent().size(), "The number of dishes should be 1.");

        Dish returnedDish = result.getContent().get(0);
        assertEquals("Crispy Calamari", returnedDish.getName(), "The dish's name should be Crispy Calamari.");
        assertEquals("Lightly breaded calamari rings, served with a tangy marinara sauce.", returnedDish.getDescription(), "The dish's description should be 'Lightly breaded calamari rings, served with a tangy marinara sauce.'");
        assertEquals(45000, returnedDish.getPrice(), "The dish's price should be 45000");
        assertEquals("crispy-calamari.jpg", returnedDish.getUrlImage(), "The dish's url image should be crispy-calamari.jpg");
        assertEquals("Appetizers", returnedDish.getCategory().getName(), "The dish's category should be Appetizers");

        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).alreadyExistsById(1L);
        Mockito.verify(dishPersistencePort, Mockito.times(1)).listDishesByRestaurantAndCategory(1L, null, 0, 10, "name", "asc");
    }

    @Test
    @DisplayName("Validation exception when restaurant not found")
    void listDishesByRestaurantShouldThrowValidationExceptionWhenRestaurantNotFound() {
        RestaurantNotFoundException exception = assertThrows(RestaurantNotFoundException.class, () -> {
            dishUseCase.listDishesByRestaurantAndCategory(1L, null, 0, 10, "asc");
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.RESTAURANT_NOT_FOUND_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).alreadyExistsById(1L);
        Mockito.verify(dishPersistencePort, Mockito.never()).listDishesByRestaurantAndCategory(1L, null, 0, 10, "name", "asc");
    }

    @Test
    @DisplayName("Validation exception when category not found")
    void listDishesByRestaurantAndCategoryShouldThrowValidationExceptionWhenCategoryNotFound() {
        Mockito.when(restaurantPersistencePort.alreadyExistsById(1L)).thenReturn(true);
        CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class, () -> {
            dishUseCase.listDishesByRestaurantAndCategory(1L, 2L, 0, 10, "asc");
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.CATEGORY_NOT_FOUND_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).alreadyExistsById(1L);
        Mockito.verify(categoryPersistencePort, Mockito.times(1)).alreadyExistsById(2L);
        Mockito.verify(dishPersistencePort, Mockito.never()).listDishesByRestaurantAndCategory(1L, 2L, 0, 10, "name", "asc");
    }

    @Test
    @DisplayName("Validation exception when page number is null")
    void listDishesByRestaurantShouldThrowValidationExceptionWhenPageNumberIsNull() {
        Mockito.when(restaurantPersistencePort.alreadyExistsById(1L)).thenReturn(true);
        InvalidPageNumberException exception = assertThrows(InvalidPageNumberException.class, () -> {
            dishUseCase.listDishesByRestaurantAndCategory(1L, null, null, 10, "asc");
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.PAGE_NUMBER_MANDATORY_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).alreadyExistsById(1L);
        Mockito.verify(dishPersistencePort, Mockito.never()).listDishesByRestaurantAndCategory(1L, null, null, 10, "name", "asc");
    }

    @Test
    @DisplayName("Validation exception when page number is a negative number")
    void listDishesByRestaurantShouldThrowValidationExceptionWhenPageNumberIsNegative() {
        Mockito.when(restaurantPersistencePort.alreadyExistsById(1L)).thenReturn(true);
        InvalidPageNumberException exception = assertThrows(InvalidPageNumberException.class, () -> {
            dishUseCase.listDishesByRestaurantAndCategory(1L, null, -1, 10, "asc");
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_PAGE_NUMBER_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).alreadyExistsById(1L);
        Mockito.verify(dishPersistencePort, Mockito.never()).listDishesByRestaurantAndCategory(1L, null, -1, 10, "name", "asc");
    }

    @Test
    @DisplayName("Validation exception when page size is null")
    void listDishesByRestaurantShouldThrowValidationExceptionWhenPageSizeIsNull() {
        Mockito.when(restaurantPersistencePort.alreadyExistsById(1L)).thenReturn(true);
        InvalidPageSizeException exception = assertThrows(InvalidPageSizeException.class, () -> {
            dishUseCase.listDishesByRestaurantAndCategory(1L, null, 0, null, "asc");
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.PAGE_SIZE_MANDATORY_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).alreadyExistsById(1L);
        Mockito.verify(dishPersistencePort, Mockito.never()).listDishesByRestaurantAndCategory(1L, null, 0, null, "name", "asc");
    }

    @Test
    @DisplayName("Validation exception when page size is less than or equal to zero")
    void listDishesByRestaurantShouldThrowValidationExceptionWhenPageSizeIsLessThanOrEqualToZero() {
        Mockito.when(restaurantPersistencePort.alreadyExistsById(1L)).thenReturn(true);
        InvalidPageSizeException exception = assertThrows(InvalidPageSizeException.class, () -> {
            dishUseCase.listDishesByRestaurantAndCategory(1L, null, 0, 0, "asc");
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_PAGE_SIZE_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).alreadyExistsById(1L);
        Mockito.verify(dishPersistencePort, Mockito.never()).listDishesByRestaurantAndCategory(1L, null, 0, 0, "name", "asc");
    }

    @Test
    @DisplayName("Validation exception when sort direction isn't 'asc' or 'desc'")
    void listDishesByRestaurantShouldThrowValidationExceptionWhenSortDirectionIsNotAscOrDesc() {
        Mockito.when(restaurantPersistencePort.alreadyExistsById(1L)).thenReturn(true);
        InvalidSortDirectionException exception = assertThrows(InvalidSortDirectionException.class, () -> {
            dishUseCase.listDishesByRestaurantAndCategory(1L, null, 0, 10, "order");
        });
        assertThat(exception.getMessage()).isEqualTo(ExceptionConstants.INVALID_SORT_DIRECTION_MESSAGE);
        Mockito.verify(restaurantPersistencePort, Mockito.times(1)).alreadyExistsById(1L);
        Mockito.verify(dishPersistencePort, Mockito.never()).listDishesByRestaurantAndCategory(1L, null, 0, 10, "name", "order");
    }
}