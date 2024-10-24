package com.prgama.foodcourt_microservice.infrastructure.output.jpa.adapter;

import com.prgama.foodcourt_microservice.domain.model.Dish;
import com.prgama.foodcourt_microservice.domain.spi.IDishPersistencePort;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.mapper.IDishEntityMapper;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.repository.IDishRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DishJpaAdapter implements IDishPersistencePort {
    private final IDishEntityMapper dishEntityMapper;
    private final IDishRepository dishRepository;

    @Override
    public void createDish(Dish dish) {
        dishRepository.save(dishEntityMapper.dishToEntity(dish));
    }

    @Override
    public boolean alreadyExistsByName(String name) {
        return dishRepository.findByName(name).isPresent();
    }

    @Override
    public void modifyDish(Dish dish) {
        dishRepository.save(dishEntityMapper.dishToEntity(dish));
    }

    @Override
    public Dish findById(Long id) {
        return dishEntityMapper.dishEntityToDish(dishRepository.findById(id).orElse(null));
    }
}