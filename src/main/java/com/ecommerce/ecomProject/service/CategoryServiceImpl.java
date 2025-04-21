package com.ecommerce.ecomProject.service;

import com.ecommerce.ecomProject.model.Category;
import com.ecommerce.ecomProject.repositories.CategoryRepository;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;



    @Service
    public class CategoryServiceImpl implements CategoryService {

        @Autowired
        private CategoryRepository categoryRepository;

        @Override
        public List<Category> getAllCategories() {
            return categoryRepository.findAll();
        }

        @Override
        public void createCategory(Category category) {
            categoryRepository.save(category);
        }

        @Override
        public String deleteCategory(Long categoryId) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));
            categoryRepository.delete(category);
            return "Category with categoryId: " + categoryId + " deleted successfully !!";
        }

        @Transactional
        @Override
        public Category updateCategory(Category category, Long categoryId) {
            try {
                Category existingCategory = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
                existingCategory.setCategoryName(category.getCategoryName());
                return categoryRepository.save(existingCategory);
            } catch (OptimisticLockException e) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Conflict occurred while updating category");
            }
        }
    }
