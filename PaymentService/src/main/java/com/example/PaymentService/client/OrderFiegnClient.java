package com.example.PaymentService.client;

import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "Order-Service", path = "/orders")
public interface OrderFiegnClient {

    @PutMapping("/{orderId}/status")
    String updateOrderStatus(@PathVariable Long orderId, @Parameter(description = "New payment status") @RequestParam OrderStatus orderStatus);




}