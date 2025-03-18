package com.example.OrderService.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Cart-Service", path = "/carts")
public interface CartFiegnClient {

    @GetMapping("/{userId}")
    CartResponse getCart(@PathVariable("userId") Long userId);


}
