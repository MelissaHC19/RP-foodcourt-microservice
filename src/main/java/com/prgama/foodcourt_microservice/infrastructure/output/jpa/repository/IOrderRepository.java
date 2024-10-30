package com.prgama.foodcourt_microservice.infrastructure.output.jpa.repository;

import com.prgama.foodcourt_microservice.infrastructure.output.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByClientId(Long clientId);
}
