package com.ecommerce.ecomProject.controller;

import com.ecommerce.ecomProject.model.Category;
import com.ecommerce.ecomProject.payload.CategoryDTO;
import com.ecommerce.ecomProject.payload.CategoryResponse;
import com.ecommerce.ecomProject.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//    /**
//     * Dependency Injection and Service Implementation in the Category Module
//     *
//     * The CategoryService interface is called using dependency injection (via the @Autowired annotation).
//     * This approach avoids explicit constructor usage as Spring automatically manages the wiring of dependencies.
//     *
//     * CategoryService: This interface defines the service methods that are invoked as needed in the application layer.
//     * CategoryServiceImpl: This class provides the concrete implementation of the business logic for each method defined in the CategoryService interface.
//     *
//     * By using this layered architecture, the interface abstraction ensures loose coupling and a clear separation of concerns,
//     * while the implementation class encapsulates the specific logic required for each operation.
//     */
//
//    /*Request mapping is an old way to add the mappings where the new method is directly ading the annotations like
//    getmapping, deletemapping, etc.
//
//    Here, Request mapping is equal to GetMapping as the request menthod is GET
//    Here, as all the way round on all the lines we have used /api/public/categories, we can add a request mapping
//    at the controller level we can set a URL pattern that the contoller will handle
//    eg:
//
@RestController
@RequestMapping("/api")

public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/public/categories")
    public ResponseEntity<CategoryResponse> getAllCategories() {
        CategoryResponse categoryResponse = categoryService.getAllCategories();
        return new ResponseEntity<>(categoryResponse, HttpStatus.OK);
    }

    @PostMapping("/public/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
            CategoryDTO deletedCategory = categoryService.deleteCategory(categoryId);
            return new ResponseEntity<>(deletedCategory,HttpStatus.OK);
    }

    @PutMapping("/public/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                                 @PathVariable Long categoryId) {

    CategoryDTO savedCategory = categoryService.updateCategory(categoryDTO, categoryId);
    return new ResponseEntity<>(savedCategory, HttpStatus.OK);
    }
}