package com.ecomerce.guava.controller;

import com.ecomerce.guava.common.ApiResponse;
import com.ecomerce.guava.dto.ProductDto;
import com.ecomerce.guava.model.Category;
import com.ecomerce.guava.model.Product;
import com.ecomerce.guava.repository.CategoryRepo;
import com.ecomerce.guava.service.ProductService;
import com.ecomerce.guava.service.impl.CategoryServiceImpl;
import com.ecomerce.guava.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    CategoryServiceImpl categoryService;

    //replace products with body
    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> products = productService.listProducts();
        return new ResponseEntity<List<ProductDto>>(products, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addProduct(@RequestBody ProductDto productDto) {
        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
       if (!optionalCategory.isPresent()) {
           return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Category does not exist"), HttpStatus.CONFLICT);
       }
       Category category = optionalCategory.get();
        productService.addProduct(productDto, category);
       return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product "+ productDto+ " has been added"),HttpStatus.CREATED);
    }

    //create an api to edit the product
    @PostMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Long productId, @RequestBody @Valid ProductDto productDto) throws Exception {
        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Category does not exist"), HttpStatus.CONFLICT);
        }
        Category category = optionalCategory.get();
        productService.updateProduct(productId, productDto, category );
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product "+ productDto+ " has been updated"),HttpStatus.OK);
    }
}
