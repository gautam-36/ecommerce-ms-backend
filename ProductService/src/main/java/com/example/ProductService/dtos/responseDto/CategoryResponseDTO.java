package com.example.ProductService.dtos.responseDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {
    private Long categoryId;
    private String categoryName;
    private Long parentCategoryId; // To indicate parent-child relationships
    private List<String> subCategoryNames;
}
