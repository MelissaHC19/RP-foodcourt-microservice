package com.prgama.foodcourt_microservice.infrastructure.output.jpa.mapper;

import com.prgama.foodcourt_microservice.domain.model.Pagination;
import com.prgama.foodcourt_microservice.domain.model.Restaurant;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.entity.RestaurantEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", uses = IRestaurantEntityMapper.class)
public interface IRestaurantPageMapper {
    @Mapping(target = "content", source = "content", qualifiedByName = "restaurantEntityListToRestaurantList")
    @Mapping(target = "pageNumber", source = "number")
    @Mapping(target = "pageSize", source = "size")
    Pagination<Restaurant> pageToPagination(Page<RestaurantEntity> page, @Context IRestaurantEntityMapper restaurantEntityMapper);

    @Named("restaurantEntityListToRestaurantList")
    default List<Restaurant> restaurantEntityListToRestaurantList(List<RestaurantEntity> restaurantEntityList, @Context IRestaurantEntityMapper restaurantEntityMapper) {
        if (restaurantEntityList == null) {
            return Collections.emptyList();
        }
        return restaurantEntityList.stream().map(restaurantEntityMapper::entityToRestaurant).toList();
    }
}
