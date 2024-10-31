package com.prgama.foodcourt_microservice.application.mapper;

import com.prgama.foodcourt_microservice.application.dto.response.ListOrdersResponse;
import com.prgama.foodcourt_microservice.application.dto.response.OrderDishResponse;
import com.prgama.foodcourt_microservice.application.dto.response.PaginationResponse;
import com.prgama.foodcourt_microservice.domain.model.Order;
import com.prgama.foodcourt_microservice.domain.model.OrderDish;
import com.prgama.foodcourt_microservice.domain.model.Pagination;
import com.prgama.foodcourt_microservice.domain.model.Restaurant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public interface IListOrdersResponseMapper {
    @Mapping(source = "restaurant", target = "restaurant", qualifiedByName = "mapRestaurantToRestaurantId")
    ListOrdersResponse orderToResponse(Order order);

    @Named("mapRestaurantToRestaurantId")
    default Long mapRestaurantToRestaurantId(Restaurant restaurant){
        return restaurant.getId();
    }

    OrderDishResponse orderDishToResponse(OrderDish orderDish);

    @Named("orderListToResponse")
    default List<ListOrdersResponse> orderListToResponse(List<Order> orders) {
        if (orders == null) {
            return Collections.emptyList();
        }
        return orders.stream().map(this::orderToResponse).toList();
    }

    @Mapping(target = "content", source = "content", qualifiedByName = "orderListToResponse")
    PaginationResponse<ListOrdersResponse> paginationToPaginationResponse(Pagination<Order> pagination);
}
