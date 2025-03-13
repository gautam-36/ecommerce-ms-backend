package com.example.OrderService.service;

import com.example.OrderService.dtos.requestDto.ProductRequestDTO;
import com.example.OrderService.dtos.responseDto.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO requestDTO);

    ProductResponseDTO getProductById(Long id);

    List<ProductResponseDTO> getAllProducts();

    void deleteProduct(Long id);

    void updateProduct(Long id, ProductRequestDTO productRequestDTO );


}
