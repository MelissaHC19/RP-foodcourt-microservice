package com.prgama.foodcourt_microservice.domain.spi;

public interface ICategoryPersistencePort {
    boolean alreadyExistsById(Long id);
}