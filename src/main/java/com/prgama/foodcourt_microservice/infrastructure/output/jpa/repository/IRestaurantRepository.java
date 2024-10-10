package com.prgama.foodcourt_microservice.infrastructure.output.jpa.repository;

import com.prgama.foodcourt_microservice.infrastructure.output.jpa.entity.RestaurantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRestaurantRepository extends JpaRepository<RestaurantEntity, Long> {
}
