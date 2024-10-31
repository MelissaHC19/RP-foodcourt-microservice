package com.prgama.foodcourt_microservice.infrastructure.output.jpa.repository;

import com.prgama.foodcourt_microservice.infrastructure.output.jpa.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByClientId(Long clientId);

    @Query("SELECT order FROM OrderEntity order WHERE order.restaurant.id = :restaurantId AND order.status = :orderStatus")
    Page<OrderEntity> findAllByRestaurantIdAndStatus(@Param("restaurantId") Long restaurantId, @Param("orderStatus") String orderStatus, PageRequest pageRequests);

}
