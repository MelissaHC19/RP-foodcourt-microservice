package com.prgama.foodcourt_microservice.domain.usecase;

import com.prgama.foodcourt_microservice.domain.api.IDishServicePort;
import com.prgama.foodcourt_microservice.domain.constants.ExceptionConstants;
import com.prgama.foodcourt_microservice.domain.exception.AlreadyExistsByNameException;
import com.prgama.foodcourt_microservice.domain.exception.CategoryNotFoundException;
import com.prgama.foodcourt_microservice.domain.exception.RestaurantNotFoundException;
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
    public void createDish(Dish dish) {
        validateDish(dish);
        dishPersistencePort.createDish(dish);
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
}
