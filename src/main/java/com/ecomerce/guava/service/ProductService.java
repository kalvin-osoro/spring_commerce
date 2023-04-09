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

    Object addNewProduct(String productDetails,
                         MultipartFile img
    );

    List<Product> getAllProducts();

    Product findById(Long productId) throws productNotExistException;

}
