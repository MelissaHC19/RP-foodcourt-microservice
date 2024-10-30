package com.prgama.foodcourt_microservice.infrastructure.configuration;

import com.prgama.foodcourt_microservice.domain.api.*;
import com.prgama.foodcourt_microservice.domain.spi.*;
import com.prgama.foodcourt_microservice.domain.usecase.AuthenticationUseCase;
import com.prgama.foodcourt_microservice.domain.usecase.DishUseCase;
import com.prgama.foodcourt_microservice.domain.usecase.OrderUseCase;
import com.prgama.foodcourt_microservice.domain.usecase.RestaurantUseCase;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.adapter.CategoryJpaAdapter;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.adapter.DishJpaAdapter;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.adapter.OrderJpaAdapter;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.adapter.RestaurantJpaAdapter;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.mapper.*;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.repository.*;
import com.prgama.foodcourt_microservice.infrastructure.security.adapter.TokenAdapter;
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
    private final IRestaurantPageMapper restaurantPageMapper;
    private final IDishPageMapper dishPageMapper;
    private final IOrderEntityMapper orderEntityMapper;
    private final IOrderRepository orderRepository;
    private final IOrderDishRepository orderDishRepository;

    @Bean
    public IRestaurantPersistencePort restaurantPersistencePort() {
        return new RestaurantJpaAdapter(restaurantEntityMapper, restaurantRepository, restaurantPageMapper);
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
        return new DishJpaAdapter(dishEntityMapper, dishRepository, dishPageMapper);
    }

    @Bean
    public IDishServicePort dishServicePort() {
        return new DishUseCase(dishPersistencePort(), categoryPersistencePort(), restaurantPersistencePort());
    }

    @Bean
    public ITokenProviderPort tokenProviderPort() {
        return new TokenAdapter();
    }

    @Bean
    public IAuthenticationServicePort authenticationServicePort() {
        return new AuthenticationUseCase(tokenProviderPort());
    }

    @Bean
    public IOrderPersistencePort orderPersistencePort() {
        return new OrderJpaAdapter(orderRepository, orderDishRepository, orderEntityMapper);
    }

    @Bean
    public IOrderServicePort orderServicePort() {
        return new OrderUseCase(restaurantPersistencePort(), orderPersistencePort(), dishPersistencePort());
    }
}