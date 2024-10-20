package com.prgama.foodcourt_microservice.infrastructure.output.jpa.repository;

import com.prgama.foodcourt_microservice.infrastructure.output.jpa.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
}