package com.ecommerce.ecomProject.controller;

import java.util.*;
import com.ecommerce.ecomProject.model.Category;
import com.ecommerce.ecomProject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Dependency Injection and Service Implementation in the Category Module
     *
     * The CategoryService interface is called using dependency injection (via the @Autowired annotation).
     * This approach avoids explicit constructor usage as Spring automatically manages the wiring of dependencies.
     *
     * CategoryService: This interface defines the service methods that are invoked as needed in the application layer.
     * CategoryServiceImpl: This class provides the concrete implementation of the business logic for each method defined in the CategoryService interface.
     *
     * By using this layered architecture, the interface abstraction ensures loose coupling and a clear separation of concerns,
     * while the implementation class encapsulates the specific logic required for each operation.
     */

    @GetMapping("/api/public/categories")
    public List<Category> getAllCategories(){
        return categoryService.getAllCategories();
    }
    @PostMapping("/api/public/categories")
    public String createCategory(@RequestBody Category category){
        categoryService.createCategory(category);
        return "Category added successfully";
    }
}
