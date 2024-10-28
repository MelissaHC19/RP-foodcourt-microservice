package com.prgama.foodcourt_microservice.infrastructure.output.jpa.repository;

import com.prgama.foodcourt_microservice.infrastructure.output.jpa.entity.DishEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface IDishRepository extends JpaRepository<DishEntity, Long> {
    Optional<DishEntity> findByName(String name);

    @Query("SELECT d FROM DishEntity d WHERE d.restaurantId.id = :restaurantId")
    Page<DishEntity> findAllByRestaurantId(Long restaurantId, PageRequest pageRequest);

    @Query("SELECT d FROM DishEntity d WHERE d.restaurantId.id = :restaurantId AND d.categoryId.id = :categoryId")
    Page<DishEntity> findAllByRestaurantIdAndCategoryId(Long categoryId, Long restaurantId, PageRequest pageRequest);
}