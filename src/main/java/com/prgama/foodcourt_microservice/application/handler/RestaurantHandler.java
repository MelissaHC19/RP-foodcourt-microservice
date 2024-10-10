package com.prgama.foodcourt_microservice.application.handler;

import com.prgama.foodcourt_microservice.application.dto.request.CreateRestaurantRequest;
import com.prgama.foodcourt_microservice.application.mapper.ICreateRestaurantRequestMapper;
import com.prgama.foodcourt_microservice.domain.api.IRestaurantServicePort;
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

    @Override
    public void createRestaurant(CreateRestaurantRequest createRestaurantRequest) {
        Restaurant restaurant = createRestaurantRequestMapper.requestToRestaurant(createRestaurantRequest);
        restaurantServicePort.createRestaurant(restaurant);
    }
}
