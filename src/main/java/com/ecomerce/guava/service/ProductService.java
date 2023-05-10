package com.ecomerce.guava.service;

import com.ecomerce.guava.dto.ProductDto;
import com.ecomerce.guava.exceptions.productNotExistException;
import com.ecomerce.guava.model.Category;
import com.ecomerce.guava.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


public interface ProductService {

    Object addNewProduct(String productDetails,
                         MultipartFile img
    );

    List<Product> getAllProducts();

    Product findById(Long productId) throws productNotExistException;

    void createProduct(ProductDto productDto, MultipartFile img, Category category);
    ResponseEntity<Boolean> createAProduct(String productDetails, MultipartFile img, Category category);

    List<ProductDto> getProducts();

    ProductDto getProductDto(Product product);

    ResponseEntity<Boolean> editProduct(Long productId, ProductDto productDto, MultipartFile img) throws Exception;

    void deleteProduct(Long productId);

//   public static getProductFromDto(ProductDto productDto, Category category);

}
