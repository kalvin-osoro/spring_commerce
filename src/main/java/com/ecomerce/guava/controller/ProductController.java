package com.ecomerce.guava.controller;

import com.ecomerce.guava.common.ApiResponse;
import com.ecomerce.guava.dto.ProductDto;
import com.ecomerce.guava.model.Category;
import com.ecomerce.guava.model.Product;
import com.ecomerce.guava.repository.CategoryRepo;
import com.ecomerce.guava.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryRepo categoryRepo;
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto productDto) {
       Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
       if (!optionalCategory.isPresent()) {
           return new ResponseEntity<>(new ApiResponse(false, "Category does not exist"), HttpStatus.BAD_REQUEST);
       }
        productService.createProduct(productDto, optionalCategory.get());
       return new ResponseEntity<>(new ApiResponse(true, "Product "+ productDto+ " has been added"),HttpStatus.CREATED);
    }


    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);

    }

    //create an api to edit the product
    @PostMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Long productId, @RequestBody ProductDto productDto) throws Exception {
        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "Category does not exist"), HttpStatus.BAD_REQUEST);
        }
        productService.updateProduct(productDto, productId);
        return new ResponseEntity<>(new ApiResponse(true, "Product "+ productDto+ " has been added"),HttpStatus.CREATED);
    }
}
