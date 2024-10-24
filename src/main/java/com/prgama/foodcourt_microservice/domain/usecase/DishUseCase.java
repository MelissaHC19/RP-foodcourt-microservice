package com.prgama.foodcourt_microservice.domain.usecase;

import com.prgama.foodcourt_microservice.domain.api.IDishServicePort;
import com.prgama.foodcourt_microservice.domain.constants.ExceptionConstants;
import com.prgama.foodcourt_microservice.domain.exception.*;
import com.prgama.foodcourt_microservice.domain.model.Dish;
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
    public void modifyDish(Long id, String description, Integer price, Long ownerId) {
        Dish dish = dishPersistencePort.findById(id);
        if (dish == null) {
            throw new DishNotFoundException(ExceptionConstants.DISH_NOT_FOUND_MESSAGE);
        }

        validateOwnerPermission(ownerId, dish);

        if (description != null) {
            dish.setDescription(description);
        }
        if (price != null) {
            dish.setPrice(price);
        }
        if (description != null || price != null) {
            dishPersistencePort.modifyDish(dish);
        }
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
}
