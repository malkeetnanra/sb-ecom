package com.ecommerce.ecomProject.service;

import com.ecommerce.ecomProject.exceptions.APIException;
import com.ecommerce.ecomProject.exceptions.ResourceNotFoundException;
import com.ecommerce.ecomProject.model.Category;
import com.ecommerce.ecomProject.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
    public class CategoryServiceImpl implements CategoryService {

        private Long nextId=1L;

        @Autowired
        private CategoryRepository categoryRepository;

        @Override
        public List<Category> getAllCategories() {
            List<Category> categories = categoryRepository.findAll();
            if(categories.isEmpty()){
                throw new APIException("No Category created till now");
            }
            return categoryRepository.findAll();
        }

        @Override
        public void createCategory(Category category) {
            //this os to check the duplicate category
            Category savedCategory = categoryRepository.findByCategoryName(category.getCategoryName());
            if(savedCategory != null){
                throw new APIException("Category with the name "+category.getCategoryName()+" already exists!");
            }
            category.setCategoryId(nextId++);
            categoryRepository.save(category);
        }

        @Override
        public String deleteCategory(Long categoryId) {
            Category category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("category","categoryId",categoryId));
            categoryRepository.delete(category);
            return "Category with categoryId: " + categoryId + " deleted successfully !!";
        }

        @Transactional
        @Override
        public Category updateCategory(Category category, Long categoryId) {
//            try {
//                Category existingCategory = categoryRepository.findById(categoryId)
//                        .orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId",categoryId));
//
//                existingCategory.setCategoryName(category.getCategoryName());
//                return categoryRepository.save(existingCategory);
//            } catch (OptimisticLockException e) {
//                throw new ResponseStatusException(HttpStatus.CONFLICT, "Conflict occurred while updating category");
//            }




            // Fetch the existing category from the database
            Category existingCategory = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));

            // Update the fields of the existing category with input data
            existingCategory.setCategoryName(category.getCategoryName());

            // Save and return the updated category
            return categoryRepository.save(existingCategory);
        }
    }


