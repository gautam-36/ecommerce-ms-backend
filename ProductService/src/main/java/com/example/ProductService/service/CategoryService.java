package com.example.ProductService.service;

import com.example.ProductService.domain.Category;
import com.example.ProductService.dtos.requestDto.CategoryRequestDTO;
import com.example.ProductService.dtos.responseDto.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO);

    Category getCategoryById(Long id);

    List<Category> getAllCategories();

    void deleteCategory(Long id);

    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO);

    CategoryResponseDTO getCategoryByName(String name);
}
