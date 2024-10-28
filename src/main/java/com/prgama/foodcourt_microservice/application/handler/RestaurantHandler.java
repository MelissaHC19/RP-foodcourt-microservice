package com.prgama.foodcourt_microservice.application.handler;

import com.prgama.foodcourt_microservice.application.dto.request.CreateRestaurantRequest;
import com.prgama.foodcourt_microservice.application.dto.response.ListRestaurantsResponse;
import com.prgama.foodcourt_microservice.application.dto.response.PaginationResponse;
import com.prgama.foodcourt_microservice.application.mapper.ICreateRestaurantRequestMapper;
import com.prgama.foodcourt_microservice.application.mapper.IListRestaurantsResponseMapper;
import com.prgama.foodcourt_microservice.domain.api.IRestaurantServicePort;
import com.prgama.foodcourt_microservice.domain.model.Pagination;
import com.prgama.foodcourt_microservice.domain.model.Restaurant;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class RestaurantHandler implements IRestaurantHandler {
    private final IRestaurantServicePort restaurantServicePort;
    private final ICreateRestaurantRequestMapper createRestaurantRequestMapper;
    private final IListRestaurantsResponseMapper listRestaurantsResponseMapper;

    @Override
    public void createRestaurant(CreateRestaurantRequest createRestaurantRequest) {
        Restaurant restaurant = createRestaurantRequestMapper.requestToRestaurant(createRestaurantRequest);
        restaurantServicePort.createRestaurant(restaurant);
    }

    @Override
    public PaginationResponse<ListRestaurantsResponse> listRestaurants(Integer pageNumber, Integer pageSize, String sortDirection) {
        Pagination<Restaurant> pagination = restaurantServicePort.listRestaurants(pageNumber, pageSize, sortDirection);
        return listRestaurantsResponseMapper.paginationToPaginationResponse(pagination);
    }
}
