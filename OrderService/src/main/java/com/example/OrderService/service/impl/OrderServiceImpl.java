package com.example.OrderService.service.impl;

import com.example.OrderService.client.CartFiegnClient;
import com.example.OrderService.client.CartResponse;
import com.example.OrderService.domain.Order;
import com.example.OrderService.enums.OrderStatus;
import com.example.OrderService.events.OrderEvent;
import com.example.OrderService.repository.OrderRepository;
import com.example.OrderService.service.OrderService;
import feign.FeignException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CartFiegnClient client;

    @Autowired
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Transactional
    @Override
    public Order placeOrder(Long userId) {
        CartResponse cart;
        try {
            cart = client.getCart(userId);
        } catch (FeignException e) {
            throw new RuntimeException("Failed to fetch cart for user ID " + userId + ". Reason: " + e.getMessage());
        }

        if (cart == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cart is empty for user ID " + userId + ". Cannot create an order.");
        }

        Order order = new Order();
        order.setUserId(cart.getUserId());
        order.setTotalAmount(cart.getTotalCartPrice());
        order.setStatus(OrderStatus.PENDING_PAYMENT);
        order.setOrderDate(LocalDateTime.now());

        Order savedOrder =  orderRepository.save(order);

        // publishing an event
        OrderEvent event = new OrderEvent(savedOrder.getOrderId(),savedOrder.getUserId(),"Faridabad");
        kafkaTemplate.send("order-created-topic" , event);

        return savedOrder;
    }

    public void orderConfirmed(){

    }



    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findByOrderId(id).orElseThrow(() -> new RuntimeException("Order with ID " + id + " doesn't exist"));

    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public String cancelOrder(Long orderId) {
        Order existingOrder = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (existingOrder.getStatus() == OrderStatus.DELIVERED) {
            throw new RuntimeException("Cannot cancel a delivered order");
        }

        existingOrder.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(existingOrder);
        return "Order " + orderId + " has been canceled successfully.";
    }


    @Transactional
    @Override
    public String updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        // validating that order with given id is exits or not
        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order with ID " + orderId + " doesn't exist"));

        // Perform the update
        orderRepository.updateOrderStatus(orderId, orderStatus.toString());
        return "Update successful";
    }


    @Override
    public List<Order> getOrdersByUserId(Long userId) {
        List<Order>orders = orderRepository.findOrderByUserId(userId);
        return orders;
    }

    @Override
    public List<Order> getOrdersByStatus(OrderStatus status) {
        return orderRepository.findByStatus(status);
    }

    @Override
    public Order requestReturn(Long orderId) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() != OrderStatus.DELIVERED) {
            throw new RuntimeException("Only delivered orders can be returned.");
        }

        order.setStatus(OrderStatus.RETURN_REQUESTED);
        return orderRepository.save(order);
    }

    @Override
    public Order markOrderAsPaid(Long orderId) {
        Order order = orderRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        if (order.getStatus() == OrderStatus.PENDING_PAYMENT) {
            order.setStatus(OrderStatus.PAID);
            return orderRepository.save(order);
        } else {
            throw new RuntimeException("Order cannot be marked as paid.");
        }
    }




}
