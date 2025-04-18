package com.ecommerce.ecomProject.service;

import com.ecommerce.ecomProject.model.Category;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class CategoryServiceImpl implements CategoryService {

    private List<Category> categories = new ArrayList<>();
    private Long nextId = 1L; //Auto increment for the categories

    @Override
    public List<Category> getAllCategories () {
        return categories;
        //List.of(); Do not user because this creates an immutable, empty list
    }

    @Override
    public void createCategory (Category category) {
        category.setCategoryId(nextId++);
        categories.add(category);
    }
}
