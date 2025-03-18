package com.example.OrderService.repository;

import com.example.OrderService.domain.Order;
import com.example.OrderService.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderId(Long id);

    List<Order> findOrderByUserId(Long userId);

    List<Order> findByStatus(OrderStatus status);

    @Modifying
    @Query(nativeQuery = true, value = "UPDATE orders SET status = :status WHERE order_id = :orderId")
    void updateOrderStatus(@Param("orderId") Long orderId, @Param("status") String status);
}
