package com.ecommerce.ecomProject.service;

import com.ecommerce.ecomProject.payload.CategoryDTO;
import com.ecommerce.ecomProject.payload.CategoryResponse;

public interface CategoryService {

    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

   // CategoryDTO createCategory (Category category);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId);

}
