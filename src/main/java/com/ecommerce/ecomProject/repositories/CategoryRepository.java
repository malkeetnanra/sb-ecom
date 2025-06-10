package com.ecommerce.ecomProject.repositories;

import com.ecommerce.ecomProject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.lang.ScopedValue;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    Category findByCategoryName(String CategoryName);
}