package com.prgama.foodcourt_microservice.infrastructure.configuration;

import com.prgama.foodcourt_microservice.domain.api.IDishServicePort;
import com.prgama.foodcourt_microservice.domain.api.IRestaurantServicePort;
import com.prgama.foodcourt_microservice.domain.api.IUserServicePort;
import com.prgama.foodcourt_microservice.domain.spi.ICategoryPersistencePort;
import com.prgama.foodcourt_microservice.domain.spi.IDishPersistencePort;
import com.prgama.foodcourt_microservice.domain.spi.IRestaurantPersistencePort;
import com.prgama.foodcourt_microservice.domain.usecase.DishUseCase;
import com.prgama.foodcourt_microservice.domain.usecase.RestaurantUseCase;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.adapter.CategoryJpaAdapter;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.adapter.DishJpaAdapter;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.adapter.RestaurantJpaAdapter;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.mapper.IDishEntityMapper;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.mapper.IRestaurantEntityMapper;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.repository.ICategoryRepository;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.repository.IDishRepository;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.repository.IRestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {
    private final IRestaurantEntityMapper restaurantEntityMapper;
    private final IRestaurantRepository restaurantRepository;
    private final IUserServicePort userServicePort;
    private final IDishEntityMapper dishEntityMapper;
    private final IDishRepository dishRepository;
    private final ICategoryRepository categoryRepository;

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantJpaAdapter(restaurantEntityMapper, restaurantRepository);
    }

    @Bean
    public IRestaurantServicePort restaurantServicePort() {
        return new RestaurantUseCase(restaurantPersistencePort(), userServicePort);
    }

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryJpaAdapter(categoryRepository);
    }

    @Bean
    public IDishPersistencePort dishPersistencePort() {
        return new DishJpaAdapter(dishEntityMapper, dishRepository);
    }

    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase(dishPersistencePort(), categoryPersistencePort(), restaurantPersistencePort());
    }
}