package com.example.PaymentService.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartResponse {
    private Long cartId;
    private Long userId;
    private List<OrderStatus> items;
    private Double totalCartPrice;
}
