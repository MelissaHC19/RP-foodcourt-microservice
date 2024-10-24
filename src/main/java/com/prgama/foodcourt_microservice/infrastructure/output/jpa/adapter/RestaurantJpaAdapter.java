package com.prgama.foodcourt_microservice.infrastructure.output.jpa.adapter;

import com.prgama.foodcourt_microservice.domain.model.Restaurant;
import com.prgama.foodcourt_microservice.domain.spi.IRestaurantPersistencePort;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.mapper.IRestaurantEntityMapper;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IRestaurantRepository restaurantRepository;

    @Override
    public void createRestaurant(Restaurant restaurant) {
        restaurantRepository.save(restaurantEntityMapper.restaurantToEntity(restaurant));
    }

    @Override
    public boolean alreadyExistsByNit(String nit) {
        return restaurantRepository.findByNit(nit).isPresent();
    }

    @Override
    public boolean alreadyExistsById(Long id) {
        return restaurantRepository.findById(id).isPresent();
    }

    @Override
    public Restaurant getRestaurant(Long id) {
        return restaurantEntityMapper.entityToRestaurant(restaurantRepository.findById(id)
                .orElse(null));
    }
}