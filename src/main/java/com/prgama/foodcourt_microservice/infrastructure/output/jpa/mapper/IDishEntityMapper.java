package com.prgama.foodcourt_microservice.infrastructure.output.jpa.mapper;

import com.prgama.foodcourt_microservice.domain.model.Dish;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.entity.DishEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IDishEntityMapper {
    @Mapping(source = "restaurant", target = "restaurantId")
    @Mapping(source = "category", target = "categoryId")
    DishEntity dishToEntity(Dish dish);
}
