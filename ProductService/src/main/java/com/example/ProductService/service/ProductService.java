package com.example.ProductService.service;

import com.example.ProductService.dtos.requestDto.ProductRequestDTO;
import com.example.ProductService.dtos.responseDto.ProductResponseDTO;

import java.util.List;

public interface ProductService {
    ProductResponseDTO createProduct(ProductRequestDTO requestDTO);

    ProductResponseDTO getProductById(Long id);

    List<ProductResponseDTO> getAllProducts();

    void deleteProduct(Long id);

    ProductResponseDTO updateProduct(Long id, ProductRequestDTO productRequestDTO );


    List<ProductResponseDTO> searchProducts(String keyword);

    List<ProductResponseDTO> getPaginatedProducts(int page, int size);
}
