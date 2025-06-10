package com.ecommerce.ecomProject.service;

import com.ecommerce.ecomProject.exceptions.APIException;
import com.ecommerce.ecomProject.exceptions.ResourceNotFoundException;
import com.ecommerce.ecomProject.model.Category;
import com.ecommerce.ecomProject.model.Product;
import com.ecommerce.ecomProject.payload.ProductDTO;
import com.ecommerce.ecomProject.payload.ProductResponse;
import com.ecommerce.ecomProject.repositories.CategoryRepository;
import com.ecommerce.ecomProject.repositories.ProductRepository;
import jdk.internal.access.JavaLangInvokeAccess;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService{

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private ModelMapper modelMapper;
    private JavaLangInvokeAccess file;

    @Value("${project.image")
    private String fileName;

    @Override
    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));

        //check if product is already added

        boolean isProductNotPresent = true;
        List<Product> productList = category.getProducts();

        for (Product value : productList) {
            if (value.getProductName().equals(productDTO.getProductName())) {
                isProductNotPresent = false;
                break;
            }
        }
        if(!isProductNotPresent) {
            Product product = modelMapper.map(productDTO, Product.class);
            product.setImage("default.png");
            product.setCategory(category);
            double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
            product.setSpecialPrice(specialPrice);

            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
        }
        else {
            throw new APIException("Product is already present");
        }
    }



    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Sort sortByAndOrder =sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize, sortByAndOrder);
        Page<Product> productPage =    productRepository.findAll(pageDetails);



        //check if product size is 0 else raise exception
        List<Product> productList=productPage.getContent();
        List<ProductDTO> productDTOS = productList.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
             //   .collect(toList());
        .toList(); //in place of .collect(toList());

        if(productDTOS.isEmpty()){
            throw new APIException("No product is present");
        }
        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        productResponse.setPageNumber(productPage.getNumber());
        productResponse.setPageSize((productPage.getSize()));
        productResponse.setTotalElements((productPage.getTotalElements()));
        productResponse.setTotalPages(productPage.getTotalPages());
        productResponse.setLastPage((productPage.isLast()));

        return  productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category", "categoryId", categoryId)
                );

        Sort sortByAndOrder =sortOrder.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber,pageSize, sortByAndOrder);
        Page<Product> productPage =    productRepository.findByCategoryOrderByPriceAsc(category, pageDetails);



        //check if product size is 0 else raise exception
        List<Product> productList=productPage.getContent();
        ProductResponse productResponse = new ProductResponse();
        List<ProductDTO> productDTOS = productList.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                //   .collect(toList());
                .toList(); //in place of .collect(toList());
        productResponse.setContent(productDTOS);
        return  productResponse;

    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {

        List<Product> productList=productRepository.findByproductNameLikeIgnoreCase('%' + keyword + '%');

        List<ProductDTO> productDTOS = productList.stream()
                .map(product -> modelMapper.map(product,ProductDTO.class))
                //   .collect(toList());
                .toList(); //in place of .collect(toList());

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOS);
        return  productResponse;
    }


    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {

        //check if exist of not if null or not

        /*
          @param productId
         * @param product
         * @return
         *
         * Logic = Take products from the DB
         * Update the product info as the user shared
         * Update/save it to DB
         */

        //Take products from the DB
        Product productfromDB = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));


        //Update the product info as the user shared as request body
        //Here we will have to update few fields which are their in th product

        Product product = modelMapper.map(productDTO, Product.class);

        productfromDB.setProductName(product.getProductName());
        productfromDB.setDescription(product.getDescription());
        productfromDB.setQuantity((product.getQuantity()));
        productfromDB.setDiscount((product.getDiscount()));
        productfromDB.setPrice((product.getPrice()));
        productfromDB.setSpecialPrice(product.getSpecialPrice());

        //SAVE
        Product savedprodct =productRepository.save(productfromDB);

    //OUTPUT
        return modelMapper.map(savedprodct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));
        productRepository.delete(product);
        return modelMapper.map(product,ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) {
        //get product from DB
        Product productfrommDB = productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product", "product", productId));
        //Upload image to server
        //upload imageString fileName = fileService.uploadImage(path, image);

        //update new filename to product
        productfrommDB.setImage(fileName);
        //save the updated product
        Product updatedproduct = productRepository.save(productfrommDB);
        //return DTO after mapping with product
        return modelMapper.map(updatedproduct,ProductDTO.class);
    }


}
