package com.example.OrderService.dtos.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartRequestDTO {
    private List<CartItemRequestDTO> items;

}
