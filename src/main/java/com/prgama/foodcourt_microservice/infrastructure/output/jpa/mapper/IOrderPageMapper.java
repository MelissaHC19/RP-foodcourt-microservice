package com.prgama.foodcourt_microservice.infrastructure.output.jpa.mapper;

import com.prgama.foodcourt_microservice.domain.model.Order;
import com.prgama.foodcourt_microservice.domain.model.Pagination;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.entity.OrderEntity;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring", uses = IOrderEntityMapper.class)
public interface IOrderPageMapper {
    @Mapping(target = "content", source = "content", qualifiedByName = "orderEntityListToOrderList")
    @Mapping(target = "pageNumber", source = "number")
    @Mapping(target = "pageSize", source = "size")
    Pagination<Order> pageToPagination(Page<OrderEntity> page, @Context IOrderEntityMapper orderEntityMapper);

    @Named("orderEntityListToOrderList")
    default List<Order> orderEntityListToOrderList(List<OrderEntity> orderEntityList, @Context IOrderEntityMapper orderEntityMapper) {
        if (orderEntityList == null) {
            return Collections.emptyList();
        }
        return orderEntityList.stream().map(orderEntityMapper::orderEntityToOrder).toList();
    }
}
