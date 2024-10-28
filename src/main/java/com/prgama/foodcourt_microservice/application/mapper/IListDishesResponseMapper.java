package com.prgama.foodcourt_microservice.application.mapper;

import com.prgama.foodcourt_microservice.application.dto.response.ListDishesResponse;
import com.prgama.foodcourt_microservice.application.dto.response.PaginationResponse;
import com.prgama.foodcourt_microservice.domain.model.Category;
import com.prgama.foodcourt_microservice.domain.model.Dish;
import com.prgama.foodcourt_microservice.domain.model.Pagination;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IListDishesResponseMapper {
    @Mapping(target = "category", source = "category", qualifiedByName = "mapCategoryToString")
    ListDishesResponse dishToResponse(Dish dish);

    @Named("mapCategoryToString")
    default String mapCategoryToString(Category category) {
        return category.getName();
    }

    @Named("dishListToResponse")
    default List<ListDishesResponse> dishListToResponse(List<Dish> dishes) {
        if (dishes == null) {
            return Collections.emptyList();
        }
        return dishes.stream().map(this::dishToResponse).toList();
    }

    @Mapping(target = "content", source = "content", qualifiedByName = "dishListToResponse")
    PaginationResponse<ListDishesResponse> paginationToPaginationResponse(Pagination<Dish> pagination);
}
