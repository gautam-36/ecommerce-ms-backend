package com.example.OrderService.service.impl;

import com.example.OrderService.domain.Category;
import com.example.OrderService.domain.Product;
import com.example.OrderService.dtos.requestDto.ProductRequestDTO;
import com.example.OrderService.dtos.responseDto.ProductResponseDTO;
import com.example.OrderService.repository.CategoryRepository;
import com.example.OrderService.repository.ProductRepository;
import com.example.OrderService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private  ProductRepository productRepository;

    @Autowired
    private  CategoryRepository categoryRepository;


    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
        Product product = new Product();
        product.setTitle(requestDTO.getTitle());
        product.setDescription(requestDTO.getDescription());
        product.setPrice(requestDTO.getPrice());
        product.setStockQuantity(requestDTO.getStockQuantity());
        product.setCreatedAt(LocalDateTime.now());
        product.setUpdatedAt(LocalDateTime.now());

        Optional<Category> category = categoryRepository.findById(requestDTO.getCategoryId());
        category.ifPresent(product::setCategory);

        Product savedProduct = productRepository.save(product);
        return new ProductResponseDTO(
                savedProduct.getProductId(),
                savedProduct.getTitle(),
                savedProduct.getDescription(),
                savedProduct.getPrice(),
                savedProduct.getStockQuantity(),
                savedProduct.getCategory() != null ? savedProduct.getCategory().getCategoryName() : null,
                savedProduct.getCreatedAt(),
                savedProduct.getUpdatedAt(),
                savedProduct.getDeletedAt()
        );
    }
    @Override
    public ProductResponseDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return new ProductResponseDTO(
                product.getProductId(),
                product.getTitle(),
                product.getDescription(),
                product.getPrice(),
                product.getStockQuantity(),
                product.getCategory() != null ? product.getCategory().getCategoryName() : null,
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getDeletedAt()
        );
    }
    @Override
    public List<ProductResponseDTO> getAllProducts() {
        return productRepository.findAll().stream().map(product ->
                new ProductResponseDTO(
                        product.getProductId(),
                        product.getTitle(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getStockQuantity(),
                        product.getCategory() != null ? product.getCategory().getCategoryName() : null,
                        product.getCreatedAt(),
                        product.getUpdatedAt(),
                        product.getDeletedAt()
                )).collect(Collectors.toList());
    }
    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
    @Override
    public void updateProduct(Long id, ProductRequestDTO productRequestDTO) {

    }
}
