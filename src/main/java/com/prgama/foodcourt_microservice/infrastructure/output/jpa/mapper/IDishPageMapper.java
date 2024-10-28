package com.prgama.foodcourt_microservice.infrastructure.output.jpa.mapper;

import com.prgama.foodcourt_microservice.domain.model.Dish;
import com.prgama.foodcourt_microservice.domain.model.Pagination;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.entity.DishEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", uses = IDishEntityMapper.class)
public interface IDishPageMapper {
    @Mapping(target = "content", source = "content", qualifiedByName = "dishEntityListToDishList")
    @Mapping(target = "pageNumber", source = "number")
    @Mapping(target = "pageSize", source = "size")
    Pagination<Dish> pageToPagination(Page<DishEntity> page, @Context IDishEntityMapper dishEntityMapper);

    @Named("dishEntityListToDishList")
    default List<Dish> dishEntityListToDishList(List<DishEntity> dishEntityList, @Context IDishEntityMapper dishEntityMapper) {
        if (dishEntityList == null) {
            return Collections.emptyList();
        }
        return dishEntityList.stream().filter(DishEntity::isActive).map(dishEntityMapper::dishEntityToDish).toList();
    }
}
