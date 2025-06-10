package com.ecommerce.ecomProject.controller;

import com.ecommerce.ecomProject.config.AppContants;
import com.ecommerce.ecomProject.model.Product;
import com.ecommerce.ecomProject.payload.ProductDTO;
import com.ecommerce.ecomProject.payload.ProductResponse;
import com.ecommerce.ecomProject.service.ProductService;
import jakarta.validation.Valid;
import org.hibernate.query.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/admin/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO,
                                                 @PathVariable Long categoryId){
        ProductDTO savedproductDTO = productService.addProduct(productDTO, categoryId);

        return new ResponseEntity<>(productDTO, HttpStatus.CREATED);
    }

    @GetMapping("public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam (name = "PageNumber", defaultValue = AppContants.PAGE_NUMBER, required = false) Integer pageNumber,
            @RequestParam (name = "pageSize", defaultValue = AppContants.PAGE_SIZE, required = false) Integer pageSize,
            @RequestParam (name = "sortBy", defaultValue = AppContants.SORT_PRODUCTS_BY, required = false) String sortBy,
            @RequestParam (name = "sortOrder", defaultValue = AppContants.SORT_PRODUCTS_BY, required = false) String sortOrder
    ){
        ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }

    @GetMapping("public/products/{categoryId}/products")
    ResponseEntity<ProductResponse> getProductByCategory(@PathVariable Long categoryId,@RequestParam (name = "PageNumber", defaultValue = AppContants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                         @RequestParam (name = "pageSize", defaultValue = AppContants.PAGE_SIZE, required = false) Integer pageSize,
                                                         @RequestParam (name = "sortBy", defaultValue = AppContants.SORT_PRODUCTS_BY, required = false) String sortBy,
                                                         @RequestParam (name = "sortOrder", defaultValue = AppContants.SORT_PRODUCTS_BY, required = false) String sortOrder
    ){
        ProductResponse productResponse= productService.searchByCategory(categoryId, pageNumber, pageSize,sortBy,sortOrder);
        return new ResponseEntity<>(productResponse,HttpStatus.OK);
    }


    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductByKeyword(@PathVariable String keyword,@RequestParam (name = "PageNumber", defaultValue = AppContants.PAGE_NUMBER, required = false) Integer pageNumber,
                                                               @RequestParam (name = "pageSize", defaultValue = AppContants.PAGE_SIZE, required = false) Integer pageSize,
                                                               @RequestParam (name = "sortBy", defaultValue = AppContants.SORT_PRODUCTS_BY, required = false) String sortBy,
                                                               @RequestParam (name = "sortOrder", defaultValue = AppContants.SORT_PRODUCTS_BY, required = false) String sortOrder
    ){
        ProductResponse productResponse = productService.searchProductByKeyword(keyword);
        return new ResponseEntity<>( productResponse,HttpStatus.FOUND);
    }

    @PutMapping("/admin/product/productId")
    public ResponseEntity<ProductDTO> updateProduct(@Valid @RequestBody ProductDTO productDTO,
                                                         @PathVariable Long productId){
   ProductDTO updateProductDTO = productService.updateProduct(productId, productDTO);
    return new ResponseEntity<>(updateProductDTO, HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable Long productId){
        ProductDTO deleteProduct;
        deleteProduct = productService.deleteProduct(productId);
        return new ResponseEntity<ProductDTO>(deleteProduct, HttpStatus.OK);
    }

    @PutMapping("Products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productImage,
                                                         @RequestParam ("Image")MultipartFile image) throws IOException {
        ProductDTO updateProduct = productService.updateProductImage(productImage, image);


        return  new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }
}
