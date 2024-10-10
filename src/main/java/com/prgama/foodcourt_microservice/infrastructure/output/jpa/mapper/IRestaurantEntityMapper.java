package com.prgama.foodcourt_microservice.infrastructure.output.jpa.mapper;

import com.prgama.foodcourt_microservice.domain.model.Restaurant;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.entity.RestaurantEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IRestaurantEntityMapper {
    RestaurantEntity restaurantToEntity(Restaurant restaurant);
}
