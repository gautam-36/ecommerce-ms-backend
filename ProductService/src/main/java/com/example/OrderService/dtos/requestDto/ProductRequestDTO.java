package com.example.OrderService.dtos.requestDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDTO {
    private String title;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private Long categoryId;
}
