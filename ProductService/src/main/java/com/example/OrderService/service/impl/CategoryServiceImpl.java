package com.example.OrderService.service.impl;

import com.example.OrderService.domain.Category;
import com.example.OrderService.dtos.requestDto.CategoryRequestDTO;
import com.example.OrderService.dtos.responseDto.CategoryResponseDTO;
import com.example.OrderService.repository.CategoryRepository;
import com.example.OrderService.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;


    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO requestDTO) {
        Category category = new Category();
        category.setCategoryName(requestDTO.getCategoryName());

        if (requestDTO.getParentCategoryId() != null) {
            Optional<Category> parentCategory = categoryRepository.findById(requestDTO.getParentCategoryId());
            parentCategory.ifPresent(category::setParentCategory);
        }

        Category savedCategory = categoryRepository.save(category);
        return new CategoryResponseDTO(
                savedCategory.getCategoryId(),
                savedCategory.getCategoryName(),
                savedCategory.getParentCategory() != null ? savedCategory.getParentCategory().getCategoryId() : null,
                savedCategory.getSubCategory() != null
                        ? savedCategory.getSubCategory().stream().map(Category::getCategoryName).collect(Collectors.toList())
                        : List.of()
        );
    }


    @Override
    public Category getCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + id));
        return category;
    }


    @Override
    public List<Category> getAllCategories() {
        List<Category>categories = categoryRepository.findAll();
        return categories;
    }
    @Override
    public void deleteCategory(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found with id: " + id);
        }
        categoryRepository.deleteById(id);
    }

    // Update category by ID
    @Override
    public CategoryResponseDTO updateCategory(Long id, CategoryRequestDTO requestDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category with ID " + id + " not found"));

        category.setCategoryName(requestDTO.getCategoryName());

        // Update Parent Category if needed
        Long parentCategoryId = requestDTO.getParentCategoryId();
        if (parentCategoryId != null) {
            Category parentCategory = categoryRepository.findById(parentCategoryId)
                    .orElseThrow(() -> new RuntimeException("Parent category not found"));
            category.setParentCategory(parentCategory);
        } else {
            category.setParentCategory(null);
        }

        category = categoryRepository.save(category);
        log.info("Updated category with ID: {}", id);

        // Building Response DTO
        Long parentId = (category.getParentCategory() != null) ? category.getParentCategory().getCategoryId() : null;
        List<String> subCategoryNames = category.getSubCategory() != null
                ? category.getSubCategory().stream().map(Category::getCategoryName).collect(Collectors.toList())
                : Collections.emptyList();

        return new CategoryResponseDTO(category.getCategoryId(), category.getCategoryName(), parentId, subCategoryNames);
    }

    // ✅ Get Category by Name
    @Override
    public CategoryResponseDTO getCategoryByName(String name) {
        Category category = categoryRepository.findByCategoryName(name)
                .orElseThrow(() -> new RuntimeException("Category with name " + name + " not found"));

        // Building Response DTO
        Long parentId = (category.getParentCategory() != null) ? category.getParentCategory().getCategoryId() : null;
        List<String> subCategoryNames = category.getSubCategory() != null
                ? category.getSubCategory().stream().map(Category::getCategoryName).collect(Collectors.toList())
                : Collections.emptyList();

        return new CategoryResponseDTO(category.getCategoryId(), category.getCategoryName(), parentId, subCategoryNames);
    }


}
