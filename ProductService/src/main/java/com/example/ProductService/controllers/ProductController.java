package com.example.ProductService.controllers;

import com.example.ProductService.dtos.requestDto.ProductRequestDTO;
import com.example.ProductService.dtos.responseDto.ProductResponseDTO;
import com.example.ProductService.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Product API", description = "Endpoints for managing products")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    @Operation(summary = "Create a new product", description = "Adds a new product to the catalog")
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductRequestDTO requestDTO) {
        log.info("Received request to create product: {}", requestDTO);
        ProductResponseDTO response = productService.createProduct(requestDTO);
        log.info("Product created successfully: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get product by ID", description = "Fetches product details by ID")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        log.info("Fetching product with ID: {}", id);
        ProductResponseDTO response = productService.getProductById(id);
        log.info("Product fetched successfully: {}", response);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(summary = "Get all products", description = "Retrieves a list of all available products")
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts() {
        log.info("Fetching all products");
        List<ProductResponseDTO> products = productService.getAllProducts();
        log.info("Total products fetched: {}", products.size());
        return ResponseEntity.ok(products);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product", description = "Updates an existing product by ID")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable Long id, @RequestBody ProductRequestDTO requestDTO) {
        log.info("Updating product with ID: {}", id);
        ProductResponseDTO updatedProduct = productService.updateProduct(id, requestDTO);
        log.info("Product updated successfully: {}", updatedProduct);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete product", description = "Deletes a product from the catalog by ID")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        log.info("Deleting product with ID: {}", id);
        productService.deleteProduct(id);
        log.info("Product deleted successfully");
        return ResponseEntity.ok("Product deleted successfully");
    }

    @GetMapping("/search")
    @Operation(summary = "Search products", description = "Searches products by product name or title")
    public ResponseEntity<List<ProductResponseDTO>> searchProducts(@RequestParam String search) {
        log.info("Searching products with name: {}", search);
        List<ProductResponseDTO> results = productService.searchProducts(search);
        log.info("Total products found: {}", results.size());
        return ResponseEntity.ok(results);
    }

    @GetMapping("/paginate")
    @Operation(summary = "Paginated product listing", description = "Retrieves a paginated list of products")
    public ResponseEntity<List<ProductResponseDTO>> getPaginatedProducts(@RequestParam int page, @RequestParam int size) {
        log.info("Fetching paginated products - Page: {}, Size: {}", page, size);
        List<ProductResponseDTO> paginatedProducts = productService.getPaginatedProducts(page, size);
        log.info("Total products in this page: {}", paginatedProducts.size());
        return ResponseEntity.ok(paginatedProducts);
    }
}
