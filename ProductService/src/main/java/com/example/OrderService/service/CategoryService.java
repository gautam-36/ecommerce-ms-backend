package com.example.OrderService.service;

import com.example.OrderService.domain.Category;
import com.example.OrderService.dtos.requestDto.CategoryRequestDTO;
import com.example.OrderService.dtos.responseDto.CategoryResponseDTO;

import java.util.List;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO);

    Category getCategoryById(Long id);

    List<Category> getAllCategories();

    void deleteCategory(Long id);

    CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO);

    CategoryResponseDTO getCategoryByName(String name);
}
