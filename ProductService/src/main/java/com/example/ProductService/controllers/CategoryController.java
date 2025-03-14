package com.example.ProductService.controllers;

import com.example.ProductService.domain.Category;
import com.example.ProductService.dtos.requestDto.CategoryRequestDTO;
import com.example.ProductService.dtos.responseDto.CategoryResponseDTO;
import com.example.ProductService.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Category API", description = "Endpoints for managing product categories")
@Slf4j
public class CategoryController {

    @Autowired
    private  CategoryService categoryService;


    @PostMapping
    @Operation(summary = "Create a new category", description = "Adds a new product category")
    public ResponseEntity<CategoryResponseDTO> createCategory(@RequestBody CategoryRequestDTO requestDTO) {
        log.info("Creating new category: {}", requestDTO.getCategoryName());
        CategoryResponseDTO category = categoryService.createCategory(requestDTO);
        return ResponseEntity.status(201).body(category); // Returns HTTP 201 Created
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get category by ID", description = "Fetches category details by ID")
    public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
        log.info("Fetching category with ID: {}", id);
        try{
            return ResponseEntity.ok(categoryService.getCategoryById(id));
        } catch (Exception e) {
            String msg = "category not found" + " with Id " + id;
            return new ResponseEntity<>(msg,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    @Operation(summary = "Get all categories", description = "Retrieves a list of all categories")
    public ResponseEntity<List<Category>> getAllCategories() {
        log.info("Fetching all categories");
        List<Category> categories = categoryService.getAllCategories();
        return categories.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(categories);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete category by ID", description = "Deletes a category by ID")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        log.warn("Deleting category with ID: {}", id);
        categoryService.deleteCategory(id);
        return ResponseEntity.ok("Category deleted successfully");
    }

    // Update category details
    @PutMapping("/{id}")
    @Operation(summary = "Update category by ID", description = "Updates category details by ID")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryRequestDTO requestDTO) {
        log.info("Updating category with ID: {}", id);
        return ResponseEntity.ok(categoryService.updateCategory(id, requestDTO));
    }

    // Fetch category by name
    @GetMapping("/name/{name}")
    @Operation(summary = "Find category by name", description = "Retrieves a category by its name")
    public ResponseEntity<CategoryResponseDTO> getCategoryByName(@PathVariable String name) {
        log.info("Fetching category with name: {}", name);
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }
}
