package com.prgama.foodcourt_microservice.infrastructure.output.jpa.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders_dishes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class OrderDishEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId", nullable = false)
    private OrderEntity order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dishId", nullable = false)
    private DishEntity dish;

    @Column(nullable = false)
    private Integer quantity;
}
