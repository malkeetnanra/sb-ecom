package com.ecommerce.ecomProject.service;

import com.ecommerce.ecomProject.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerErrorException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.NOT_FOUND;

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


    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found"));

//The below code is not needed as above we are handling the exception using the status code
//        if(category == null){
//            return "Category not found in the database!"; //to find the category we want to delete
//        }

        categories.remove(category);
        return "Category with categoryId "+categoryId+" deleted successfully!";
    }

    @Override
    public Category updateCategory (Category category, Long categoryId) {
        Optional<Category> optionalCategory = categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst();

        if(optionalCategory.isPresent()){
            Category exisitingCategory = optionalCategory.get();
            exisitingCategory.setCategoryName(category.getCategoryName());
            return exisitingCategory;
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Resource not found");
        }
    }
}
