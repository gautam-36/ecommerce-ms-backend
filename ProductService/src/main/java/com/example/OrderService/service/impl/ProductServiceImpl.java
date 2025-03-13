package com.example.OrderService.service.impl;

import com.example.OrderService.domain.Category;
import com.example.OrderService.domain.Product;
import com.example.OrderService.dtos.requestDto.ProductRequestDTO;
import com.example.OrderService.dtos.responseDto.ProductResponseDTO;
import com.example.OrderService.repository.CategoryRepository;
import com.example.OrderService.repository.ProductRepository;
import com.example.OrderService.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private  ProductRepository productRepository;

    @Autowired
    private  CategoryRepository categoryRepository;


    @Override
    public ProductResponseDTO createProduct(ProductRequestDTO requestDTO) {
        log.info("Creating a new product: {}", requestDTO);

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
        log.info("Product created successfully with ID: {}", savedProduct.getProductId());

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
        log.info("Fetching product by ID: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found with ID: {}", id);
                    return new RuntimeException("Product not found");
                });

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
        log.info("Fetching all products");

        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> new ProductResponseDTO(
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
        log.info("Deleting product with ID: {}", id);
        productRepository.deleteById(id);
        log.info("Product deleted successfully");
    }

    @Override
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO requestDTO) {
        log.info("Updating product with ID: {}", id);

        Product product = productRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found with ID: {}", id);
                    return new RuntimeException("Product not found");
                });

        product.setTitle(requestDTO.getTitle());
        product.setDescription(requestDTO.getDescription());
        product.setPrice(requestDTO.getPrice());
        product.setStockQuantity(requestDTO.getStockQuantity());
        product.setUpdatedAt(LocalDateTime.now());

        Optional<Category> category = categoryRepository.findById(requestDTO.getCategoryId());
        category.ifPresent(product::setCategory);

        Product updatedProduct = productRepository.save(product);
        log.info("Product updated successfully");

        return new ProductResponseDTO(
                updatedProduct.getProductId(),
                updatedProduct.getTitle(),
                updatedProduct.getDescription(),
                updatedProduct.getPrice(),
                updatedProduct.getStockQuantity(),
                updatedProduct.getCategory() != null ? updatedProduct.getCategory().getCategoryName() : null,
                updatedProduct.getCreatedAt(),
                updatedProduct.getUpdatedAt(),
                updatedProduct.getDeletedAt()
        );
    }
    @Override
    public List<ProductResponseDTO> searchProducts(String productName) {
        log.info("Searching for products with name: {}", productName);

        List<Product> products = productRepository.findByTitleContainingIgnoreCase(productName);

        return products.stream().map(product -> new ProductResponseDTO(
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
    public List<ProductResponseDTO> getPaginatedProducts(int page, int size) {
        log.info("Fetching paginated products - Page: {}, Size: {}", page, size);

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        List<Product> products = productRepository.findAll(pageable).getContent();

        return products.stream().map(product -> new ProductResponseDTO(
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
}
