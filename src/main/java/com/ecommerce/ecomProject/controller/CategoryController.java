package com.ecommerce.ecomProject.controller;

import java.util.*;
import com.ecommerce.ecomProject.model.Category;
import com.ecommerce.ecomProject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


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

    /*Request mapping is an old way to add the mappings where the new method is directly ading the annotations like
    getmapping, deletemapping, etc.

    Here, Request mapping is equal to GetMapping as the request menthod is GET
    Here, as all the way round on all the lines we have used /api/public/categories, we can add a request mapping
    at the controller level we can set a URL pattern that the contoller will handle
    eg:

    @PostMapping("/api/public/categories")

    so we will use
    @RequestMapping("/api") as this is common on all the api requests

    once done, we will write others like

    @PostMapping("/public/categories") and this will work
    */
    @RestController
    @RequestMapping("/api")
    public class CategoryController {

        @Autowired
        private CategoryService categoryService;

        @GetMapping("/public/categories")
        public ResponseEntity<List<Category>> getAllCategories() {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(categories);
        }

        @PostMapping("/public/categories")
        public ResponseEntity<String> createCategory(@RequestBody Category category) {
            categoryService.createCategory(category);
            return ResponseEntity.status(HttpStatus.CREATED).body("Category added successfully");
        }

        @DeleteMapping("/admin/categories/{categoryId}")
        public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
            try {
                String status = categoryService.deleteCategory(categoryId);
                return ResponseEntity.ok(status);
            } catch (ResponseStatusException e) {
                return ResponseEntity.status(e.getStatusCode()).body(e.getReason());
            }
        }

        @PutMapping("/public/categories/{categoryId}")
        public ResponseEntity<Category> updateCategory(
                @RequestBody Category category, @PathVariable Long categoryId) {
            try {
                Category updatedCategory = categoryService.updateCategory(category, categoryId);
                return ResponseEntity.ok(updatedCategory);
            } catch (ResponseStatusException e) {
                return ResponseEntity.status(e.getStatusCode()).build();
            }
        }
    }
