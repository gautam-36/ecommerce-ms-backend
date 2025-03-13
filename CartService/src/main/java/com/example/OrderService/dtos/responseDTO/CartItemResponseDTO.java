package com.example.OrderService.dtos.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItemResponseDTO {
    private Long productId;
    private String productName;
    private int quantity;
    private Double price;


}
