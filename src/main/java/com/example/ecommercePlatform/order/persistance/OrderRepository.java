package com.example.ecommercePlatform.order.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
     List<Order> findAllByUserId(Long userId);
}
