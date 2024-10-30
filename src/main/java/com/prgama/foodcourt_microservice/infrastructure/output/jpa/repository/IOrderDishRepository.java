package com.prgama.foodcourt_microservice.infrastructure.output.jpa.repository;

import com.prgama.foodcourt_microservice.infrastructure.output.jpa.entity.OrderDishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderDishRepository extends JpaRepository<OrderDishEntity, Long> {
}
