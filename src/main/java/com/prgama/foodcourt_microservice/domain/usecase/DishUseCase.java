package com.prgama.foodcourt_microservice.domain.usecase;

import com.prgama.foodcourt_microservice.domain.api.IDishServicePort;
import com.prgama.foodcourt_microservice.domain.constants.ExceptionConstants;
import com.prgama.foodcourt_microservice.domain.constants.PaginationConstants;
import com.prgama.foodcourt_microservice.domain.exception.*;
import com.prgama.foodcourt_microservice.domain.model.Dish;
import com.prgama.foodcourt_microservice.domain.model.Pagination;
import com.prgama.foodcourt_microservice.domain.spi.ICategoryPersistencePort;
import com.prgama.foodcourt_microservice.domain.spi.IDishPersistencePort;
import com.prgama.foodcourt_microservice.domain.spi.IRestaurantPersistencePort;

public class DishUseCase implements IDishServicePort {
    private final IDishPersistencePort dishPersistencePort;
    private final ICategoryPersistencePort categoryPersistencePort;
    private final IRestaurantPersistencePort restaurantPersistencePort;

    public DishUseCase(IDishPersistencePort dishPersistencePort, ICategoryPersistencePort categoryPersistencePort, IRestaurantPersistencePort restaurantPersistencePort) {
        this.dishPersistencePort = dishPersistencePort;
        this.categoryPersistencePort = categoryPersistencePort;
        this.restaurantPersistencePort = restaurantPersistencePort;
    }

    @Override
    public void createDish(Long ownerId, Dish dish) {
        validateDish(dish);
        validateOwnerPermission(ownerId, dish);
        dishPersistencePort.createDish(dish);
    }

    @Override
    public void modifyDish(Long id, String description, Integer price, Boolean active, Long ownerId) {
        Dish dish = dishPersistencePort.findById(id);
        validateDishExistence(dish);
        validateOwnerPermission(ownerId, dish);

        if (description != null) {
            dish.setDescription(description);
        }
        if (price != null) {
            dish.setPrice(price);
        }
        if (active != null) {
            dish.setActive(active);
        }
        if (description != null || price != null || active != null) {
            dishPersistencePort.modifyDish(dish);
        }
    }

    @Override
    public Pagination<Dish> listDishesByRestaurantAndCategory(Long restaurantId, Long categoryId, Integer pageNumber, Integer pageSize, String sortDirection) {
        validateRestaurantAndCategoryExistence(restaurantId, categoryId);
        validatePagination(pageNumber, pageSize, sortDirection);
        return dishPersistencePort.listDishesByRestaurantAndCategory(restaurantId, categoryId, pageNumber, pageSize, PaginationConstants.SORT_BY_NAME, sortDirection);
    }

    private void validateDish(Dish dish) {
        if (dishPersistencePort.alreadyExistsByName(dish.getName())) {
            throw new AlreadyExistsByNameException(ExceptionConstants.ALREADY_EXISTS_BY_NAME_MESSAGE);
        }
        if (!restaurantPersistencePort.alreadyExistsById(dish.getRestaurant().getId())) {
            throw new RestaurantNotFoundException(ExceptionConstants.RESTAURANT_NOT_FOUND_MESSAGE);
        }
        if (!categoryPersistencePort.alreadyExistsById(dish.getCategory().getId())) {
            throw new CategoryNotFoundException(ExceptionConstants.CATEGORY_NOT_FOUND_MESSAGE);
        }
    }

    private void validateOwnerPermission(Long ownerId, Dish dish) {
        if (restaurantPersistencePort.getRestaurant(dish.getRestaurant().getId()).getOwnerId() != ownerId) {
            throw new UnauthorizedOwnerException(ExceptionConstants.UNAUTHORIZED_OWNER_MESSAGE);
        }
    }

    private void validateDishExistence(Dish dish) {
        if (dish == null) {
            throw new DishNotFoundException(ExceptionConstants.DISH_NOT_FOUND_MESSAGE);
        }
    }

    private void validateRestaurantAndCategoryExistence(Long restaurantId, Long categoryId) {
        if (!restaurantPersistencePort.alreadyExistsById(restaurantId)) {
            throw new RestaurantNotFoundException(ExceptionConstants.RESTAURANT_NOT_FOUND_MESSAGE);
        }
        if (categoryId != null && !categoryPersistencePort.alreadyExistsById(categoryId)) {
            throw new CategoryNotFoundException(ExceptionConstants.CATEGORY_NOT_FOUND_MESSAGE);
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
