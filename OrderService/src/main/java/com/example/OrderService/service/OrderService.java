package com.example.OrderService.service;

import com.example.OrderService.domain.Order;
import com.example.OrderService.enums.OrderStatus;

import java.util.List;

public interface OrderService {


    public Order placeOrder(Long userId);

    public Order getOrderById(Long id);

    public List<Order> getAllOrder();

    public String cancelOrder(Long id);

    public Order updateOrderStatus(Long id, OrderStatus orderStatus);


    List<Order> getOrdersByUserId(Long orderId);

    List<Order> getOrdersByStatus(OrderStatus status);

    Order requestReturn(Long orderId);

    Order markOrderAsPaid(Long orderId);
}
