package com.ecomerce.guava.service;

import com.ecomerce.guava.dto.ProductDto;
import com.ecomerce.guava.exceptions.productNotExistException;
import com.ecomerce.guava.model.Category;
import com.ecomerce.guava.model.Product;
import com.ecomerce.guava.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


public interface ProductService {


//    List<ProductDto> listProducts();
     List<ProductDto> listProducts(MultipartFile file);


//    void addProduct(ProductDto productDto, Category category);
    void addProduct(ProductDto productDto, Category category, MultipartFile imageFile) throws IOException;

    void updateProduct(Long productId, ProductDto productDto, Category category);

    Product getProductById(Long productId) throws productNotExistException;

    void createProduct(ProductDto productDto, Category category);

    public ProductDto getProductDto(Product product);

//    List<ProductDto> getAllProducts();

    void updateProduct(ProductDto productDto, Long productId) throws Exception;


    Product findById(Long productId);

//    public void saveProductToDB(MultipartFile file,String name,String description
//            ,int price);
}
