package com.prgama.foodcourt_microservice.infrastructure.output.jpa.repository;

import com.prgama.foodcourt_microservice.infrastructure.output.jpa.entity.DishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {
    Optional<DishEntity> findByName(String name);
}