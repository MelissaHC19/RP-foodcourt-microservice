package com.prgama.foodcourt_microservice.application.mapper;

import com.prgama.foodcourt_microservice.application.dto.response.ListRestaurantsResponse;
import com.prgama.foodcourt_microservice.application.dto.response.PaginationResponse;
import com.prgama.foodcourt_microservice.domain.model.Pagination;
import com.prgama.foodcourt_microservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IListRestaurantsResponseMapper {
    ListRestaurantsResponse restaurantToResponse(Restaurant restaurant);

    @Named("restaurantListToResponse")
    default List<ListRestaurantsResponse> restaurantListToResponse(List<Restaurant> restaurants) {
        if (restaurants == null) {
            return Collections.emptyList();
        }
        return restaurants.stream().map(this::restaurantToResponse).toList();
    }

    @Mapping(target = "content", source = "content", qualifiedByName = "restaurantListToResponse")
    PaginationResponse<ListRestaurantsResponse> paginationToPaginationResponse(Pagination<Restaurant> pagination);
}
