//package com.ecommerce.ecomProject.service;
//
//import com.ecommerce.ecomProject.exceptions.APIException;
//import com.ecommerce.ecomProject.exceptions.ResourceNotFoundException;
//import com.ecommerce.ecomProject.model.Category;
//import com.ecommerce.ecomProject.payload.CategoryDTO;
//import com.ecommerce.ecomProject.payload.CategoryResponse;
//import com.ecommerce.ecomProject.repositories.CategoryRepository;
//import jakarta.transaction.Transactional;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.orm.ObjectOptimisticLockingFailureException;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//    public class CategoryServiceImpl implements CategoryService {
//
//        @Autowired
//        private CategoryRepository categoryRepository;
//
//        @Autowired
//        private ModelMapper modelMapper;
//
//        @Override
//        public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
//
//            Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
//                    ? Sort.by(sortBy).ascending()
//                    : Sort.by(sortBy).descending();
//
//            Pageable pageDetails = PageRequest.of(pageNumber,pageSize, sortByAndOrder);
//            Page<Category> categoryPage = categoryRepository.findAll(pageDetails);
//
//            //List<Category> categories = categoryRepository.findAll();
//            List<Category> categories = categoryPage.getContent();
//            if(categories.isEmpty()){
//                throw new APIException("No Category created till now");
//            }
//            List<CategoryDTO> categoryDTOS = categories.stream()
//                    .map(category -> modelMapper.map(category,CategoryDTO.class))
//                    .toList();
//
//            CategoryResponse categoryResponse = new CategoryResponse();
//            categoryResponse.setContent(categoryDTOS);
//            categoryResponse.setPageNumber(categoryPage.getNumber());
//            categoryResponse.setPageSize(categoryPage.getSize());
//            categoryResponse.setTotalElements(categoryPage.getTotalElements());
//            categoryResponse.setTotalPages(categoryPage.getTotalPages());
//            categoryResponse.setLastPage(categoryPage.isLast());
//
//            return categoryResponse;
//        }
//
//    @Override
//    public CategoryDTO createCategory (CategoryDTO categoryDTO) {
//            Category category = modelMapper.map(categoryDTO, Category.class);
//            //this os to check the duplicate category
//            Category savedCategoryDB = categoryRepository.findByCategoryName(category.getCategoryName());
//            if(savedCategoryDB != null){
//                throw new APIException("Category with the name "+category.getCategoryName()+" already exists!!");
//            }
//        Category savedCategory=categoryRepository.save(category);
//        return modelMapper.map(savedCategory, CategoryDTO.class);
//    }
//
//        @Override
//        public CategoryDTO deleteCategory(Long categoryId) {
//            Category category = categoryRepository.findById(categoryId)
//                    .orElseThrow(() -> new ResourceNotFoundException("category","categoryId",categoryId));
//            categoryRepository.delete(category);
//            return modelMapper.map(category,CategoryDTO.class);
//        }
//
////        @Transactional
////        @Override
////        public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
////
////            // Fetch the existing category from the database
////            Category existingCategory = categoryRepository.findById(categoryId)
////                    .orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
////
////            Category category = modelMapper.map(categoryDTO,Category.class);
////
////            // Update the fields of the existing category with input data
////            existingCategory.setCategoryName(categoryDTO.getCategoryName());
////
////            Category updateCategory = categoryRepository.save(category);
////
////            // Save and return the updated category
////            return modelMapper.map(updateCategory, CategoryDTO.class);
////        }
//    @Transactional
//    @Override
//    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
//        Category existingCategory = categoryRepository.findById(categoryId)
//                .orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
//
//        existingCategory.setCategoryName(categoryDTO.getCategoryName());
//
//        try {
//            Category updatedCategory = categoryRepository.save(existingCategory);
//            return modelMapper.map(updatedCategory, CategoryDTO.class);
//        } catch (ObjectOptimisticLockingFailureException e) {
//            throw new APIException("Category was updated by another transaction. Please refresh and try again.");
//        }
//    }
//
//}
//
//

package com.ecommerce.ecomProject.service;

import com.ecommerce.ecomProject.exceptions.APIException;
import com.ecommerce.ecomProject.exceptions.ResourceNotFoundException;
import com.ecommerce.ecomProject.model.Category;
import com.ecommerce.ecomProject.payload.CategoryDTO;
import com.ecommerce.ecomProject.payload.CategoryResponse;
import com.ecommerce.ecomProject.repositories.CategoryRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageDetails);

        List<Category> categories = categoryPage.getContent();
        if (categories.isEmpty()) {
            throw new APIException("No Category created till now");
        }

        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .toList();

        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setContent(categoryDTOS);
        categoryResponse.setPageNumber(categoryPage.getNumber());
        categoryResponse.setPageSize(categoryPage.getSize());
        categoryResponse.setTotalElements(categoryPage.getTotalElements());
        categoryResponse.setTotalPages(categoryPage.getTotalPages());
        categoryResponse.setLastPage(categoryPage.isLast());

        return categoryResponse;
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category savedCategoryDB = categoryRepository.findByCategoryName(category.getCategoryName());
        if (savedCategoryDB != null) {
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists!!");
        }
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));
        categoryRepository.delete(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Transactional
    @Override
    public CategoryDTO updateCategory(CategoryDTO categoryDTO, Long categoryId) {
        Category existingCategory = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("category", "categoryId", categoryId));

        existingCategory.setCategoryName(categoryDTO.getCategoryName());

        try {
            Category updatedCategory = categoryRepository.save(existingCategory);
            return modelMapper.map(updatedCategory, CategoryDTO.class);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new APIException("Category was updated by another transaction. Please refresh and try again.");
        }
    }
}
