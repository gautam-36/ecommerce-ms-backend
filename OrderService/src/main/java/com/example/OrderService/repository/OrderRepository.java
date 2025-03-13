package com.example.OrderService.repository;

import com.example.OrderService.domain.Order;
import com.example.OrderService.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderId(Long id);

    List<Order> findOrderByUserId(Long userId);

    List<Order> findByStatus(OrderStatus status);
}
