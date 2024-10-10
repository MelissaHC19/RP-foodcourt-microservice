package com.prgama.foodcourt_microservice.application.mapper;

import com.prgama.foodcourt_microservice.application.dto.request.CreateRestaurantRequest;
import com.prgama.foodcourt_microservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ICreateRestaurantRequestMapper {
    @Mapping(target = "id", ignore = true)
    Restaurant requestToRestaurant(CreateRestaurantRequest createRestaurantRequest);
}