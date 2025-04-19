package com.ecommerce.ecomProject.service;

import com.ecommerce.ecomProject.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> getAllCategories();
    void createCategory(Category category);

    String deleteCategory (Long categoryID);
}
