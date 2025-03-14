package com.example.OrderService.controller;

import com.example.OrderService.domain.Order;
import com.example.OrderService.enums.OrderStatus;
import com.example.OrderService.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/orders")
@RestController
@Slf4j
@Tag(name = "Order API", description = "Endpoints for order related operations")
public class OrderController {

     @Autowired
     OrderService orderService;



    @Operation(summary = "Create a new Order", description = "Create a new Order")
    @PostMapping("/create/{userId}")
    public ResponseEntity<?> createOrder(@PathVariable Long userId) {
        try {
            Order order = orderService.createOrder(userId);
            return ResponseEntity.status(201).body(order);
        } catch (RuntimeException e) {
            log.error("Error while creating order for user {}: {}", userId, e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Operation(summary = "Get all Orders", description = "Get all Orders")
    @GetMapping
    public ResponseEntity<?> getOrders() {
        try {
            List<Order> orders = orderService.getAllOrder();
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            log.error("Error fetching orders: {}", e.getMessage());
            return ResponseEntity.internalServerError().body("Unable to fetch orders.");
        }
    }

    @Operation(summary = "Get Order By Order Id", description = "Get Order By Order Id")
    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {
        try {
            Order order = orderService.getOrderById(orderId);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            log.error("Order with ID {} not found: {}", orderId, e.getMessage());
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @Operation(summary = "Get Orders By User Id", description = "Get Orders By User Id")
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getOrdersByUserId(@PathVariable Long userId) {
        try {
            List<Order> orders = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (RuntimeException e) {
            log.error("Error fetching orders for user {}: {}", userId, e.getMessage());
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @Operation(summary = "Update Order Status", description = "This API is used to update the status of an order")
    @PutMapping("/{orderId}/status")
    public ResponseEntity<?> updateOrderStatus(@PathVariable Long orderId, @RequestBody OrderStatus orderStatus) {
        try {
            Order order = orderService.updateOrderStatus(orderId, orderStatus);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            log.error("Error updating order status for order {}: {}", orderId, e.getMessage());
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @Operation(summary = "Cancel an Order", description = "Cancel an order before it is shipped")
    @DeleteMapping("/{orderId}/cancel")
    public ResponseEntity<?> cancelOrder(@PathVariable Long orderId) {
        try {
            String response = orderService.cancelOrder(orderId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            log.error("Error canceling order {}: {}", orderId, e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @Operation(summary = "Get Orders by Status", description = "Fetch all orders with a specific status")
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getOrdersByStatus(@PathVariable OrderStatus status) {
        try {
            List<Order> orders = orderService.getOrdersByStatus(status);
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            log.error("Error fetching orders by status {}: {}", status, e.getMessage());
            return ResponseEntity.internalServerError().body("Unable to fetch orders.");
        }
    }

    @Operation(summary = "Get Order History for a User", description = "Fetch order history for a given user ID")
    @GetMapping("/user/{userId}/history")
    public ResponseEntity<?> getOrderHistory(@PathVariable Long userId) {
        try {
            List<Order> orders = orderService.getOrdersByUserId(userId);
            return ResponseEntity.ok(orders);
        } catch (RuntimeException e) {
            log.error("Error fetching order history for user {}: {}", userId, e.getMessage());
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }


    @Operation(summary = "Request Order Return", description = "Request a return for a delivered order")
    @PutMapping("/{orderId}/return")
    public ResponseEntity<?> requestReturn(@PathVariable Long orderId) {
        try {
            Order order = orderService.requestReturn(orderId);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            log.error("Error processing return request for order {}: {}", orderId, e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @Operation(summary = "Mark Order as Paid", description = "Update order status to PAID")
    @PutMapping("/{orderId}/pay")
    public ResponseEntity<?> markOrderAsPaid(@PathVariable Long orderId) {
        try {
            Order order = orderService.markOrderAsPaid(orderId);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            log.error("Error updating payment status for order {}: {}", orderId, e.getMessage());
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }







}
