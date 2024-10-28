package com.prgama.foodcourt_microservice.infrastructure.output.jpa.adapter;

import com.prgama.foodcourt_microservice.domain.model.Pagination;
import com.prgama.foodcourt_microservice.domain.model.Restaurant;
import com.prgama.foodcourt_microservice.domain.spi.IRestaurantPersistencePort;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.entity.RestaurantEntity;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.mapper.IRestaurantEntityMapper;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.mapper.IRestaurantPageMapper;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
public class RestaurantJpaAdapter implements IRestaurantPersistencePort {
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IRestaurantRepository restaurantRepository;
    private final IRestaurantPageMapper restaurantPageMapper;

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

    @Override
    public Pagination<Restaurant> listRestaurants(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Order.by(sortBy).with(Sort.Direction.fromString(sortDirection)));
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<RestaurantEntity> page = restaurantRepository.findAll(pageable);
        return restaurantPageMapper.pageToPagination(page, restaurantEntityMapper);
    }
}