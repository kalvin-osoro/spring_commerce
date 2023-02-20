package com.ecomerce.guava.service.impl;

import com.ecomerce.guava.dto.ProductDto;
import com.ecomerce.guava.model.Category;
import com.ecomerce.guava.model.Product;
import com.ecomerce.guava.repository.ProductRepo;
import com.ecomerce.guava.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    ProductRepo productRepo;


    @Override
    public void createProduct(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setDescription(productDto.getDescription());
        product.setImageURL(productDto.getImageURL());
        product.setName(productDto.getName());
        product.setCategory(category);
        product.setPrice(productDto.getPrice());
        productRepo.save(product);

    }

    public ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
        productDto.setImageURL(product.getImageURL());
        productDto.setName(product.getName());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setPrice(product.getPrice());
        productDto.setId(product.getId());
        return productDto;
    }


    public List<ProductDto> getAllProducts() {
       List<Product> allProducts = productRepo.findAll();

       List<ProductDto> productDtos = new ArrayList<>();
       for (Product product: allProducts) {
           productDtos.add(getProductDto(product));
       }
       return productDtos;
    }

    @Override
    public void updateProduct(ProductDto productDto, Long productId) throws Exception {
       Optional<Product> optionalProduct = productRepo.findById(productId);
       //throw an exception if product does not exist
        if (!optionalProduct.isPresent()) {
            throw new Exception("Product not present");
        }
        Product product = optionalProduct.get();
        product.setDescription(productDto.getDescription());
        product.setImageURL(productDto.getImageURL());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        productRepo.save(product);
    }
}
