package com.prgama.foodcourt_microservice.infrastructure.configuration;

import com.prgama.foodcourt_microservice.domain.api.IRestaurantServicePort;
import com.prgama.foodcourt_microservice.domain.spi.IRestaurantPersistencePort;
import com.prgama.foodcourt_microservice.domain.usecase.RestaurantUseCase;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.adapter.RestaurantJpaAdapter;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.mapper.IRestaurantEntityMapper;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IRestaurantRepository restaurantRepository;

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantJpaAdapter(restaurantEntityMapper, restaurantRepository);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort());
    }
}