package com.example.CartService.dtos.requestDTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItemRequestDTO {
    private Long productId;
    private int quantity;
}
