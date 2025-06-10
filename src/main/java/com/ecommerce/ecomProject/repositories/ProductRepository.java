package com.ecommerce.ecomProject.repositories;

import com.ecommerce.ecomProject.model.Category;
import com.ecommerce.ecomProject.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findByCategoryOrderByPriceAsc(Category category, Pageable pageDetails);

    List<Product> findByproductNameLikeIgnoreCase(String keyword);
}
