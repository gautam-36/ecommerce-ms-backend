package com.example.OrderService.dtos.responseDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponseDTO {
    private Long categoryId;
    private String categoryName;
    private Long parentCategoryId; // To indicate parent-child relationships
    private List<String> subCategoryNames;
}
