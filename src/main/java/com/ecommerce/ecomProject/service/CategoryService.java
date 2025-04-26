package com.ecommerce.ecomProject.service;

import com.ecommerce.ecomProject.model.Category;

public interface CategoryService {

    Object getAllCategories();

    void createCategory(Category category);

    String deleteCategory(Long categoryId);

    Category updateCategory(Category category, Long categoryId);

}
