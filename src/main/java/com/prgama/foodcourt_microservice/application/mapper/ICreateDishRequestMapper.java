package com.prgama.foodcourt_microservice.application.mapper;

import com.prgama.foodcourt_microservice.application.dto.request.CreateDishRequest;
import com.prgama.foodcourt_microservice.domain.model.Category;
import com.prgama.foodcourt_microservice.domain.model.Dish;
import com.prgama.foodcourt_microservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ICreateDishRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(source = "restaurantId", target = "restaurant", qualifiedByName = "mapRestaurantIdToRestaurant")
    @Mapping(source = "categoryId", target = "category", qualifiedByName = "mapCategoryIdToCategory")
    Dish requestToDish(CreateDishRequest createDishRequest);

    @Named("mapRestaurantIdToRestaurant")
    default Restaurant mapRestaurantIdToRestaurant(Long restaurantId) {
        return new Restaurant(restaurantId, null, null, null, null, null, null);
    }

    @Named("mapCategoryIdToCategory")
    default Category mapCategoryIdToCategory(Long categoryId) {
        return new Category(categoryId, null, null);
    }
}
