package com.prgama.foodcourt_microservice.infrastructure.output.jpa.adapter;

import com.prgama.foodcourt_microservice.domain.spi.ICategoryPersistencePort;
import com.prgama.foodcourt_microservice.infrastructure.output.jpa.repository.ICategoryRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CategoryJpaAdapter implements ICategoryPersistencePort {
    private final ICategoryRepository categoryRepository;

    @Override
    public boolean alreadyExistsById(Long id) {
        return categoryRepository.findById(id).isPresent();
    }
}