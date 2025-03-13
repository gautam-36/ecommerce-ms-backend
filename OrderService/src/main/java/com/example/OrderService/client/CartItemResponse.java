package com.example.OrderService.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItemResponse {
    private Long productId;
    private String productName;
    private int quantity;
    private Double price;
}
