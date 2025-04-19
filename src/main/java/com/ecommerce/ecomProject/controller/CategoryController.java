package com.ecommerce.ecomProject.controller;

import java.util.*;
import com.ecommerce.ecomProject.model.Category;
import com.ecommerce.ecomProject.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<List<Category>> getAllCategories(){
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK) ;
    }
    @PostMapping("/api/public/categories")
    public ResponseEntity<String> createCategory(@RequestBody Category category){
        categoryService.createCategory(category);
        return new ResponseEntity<>("Category added successfully",HttpStatus.CREATED);
    }

    @DeleteMapping("/api/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId){
        try {
            String status = categoryService.deleteCategory(categoryId);
            //different ways to show status
     //       return ResponseEntity.ok(status);
      //      return new ResponseEntity<>(status, HttpStatus.OK);
            return ResponseEntity.status(HttpStatus.OK).body(status);
        } catch (ResponseStatusException e){
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }
}
