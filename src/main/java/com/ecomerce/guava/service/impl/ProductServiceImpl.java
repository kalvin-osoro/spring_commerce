package com.ecomerce.guava.service.impl;

import com.ecomerce.guava.dto.ProductDto;
import com.ecomerce.guava.exceptions.productNotExistException;
import com.ecomerce.guava.model.Category;
import com.ecomerce.guava.model.Product;
import com.ecomerce.guava.repository.ProductRepo;
import com.ecomerce.guava.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
   private ProductRepo productRepo;

    @Override
    public List<ProductDto> listProducts(MultipartFile file) {
        List<Product> products = productRepo.findAll();
        List<ProductDto> productDtos = new ArrayList<>();
        for (Product product: products) {
            ProductDto productDto = getDtoFromProduct(product, file);
            productDtos.add(productDto);
//            productDtos.add(getProductDto(product));
        }
        return productDtos;
    }


    private static ProductDto getDtoFromProduct(Product product, MultipartFile file) {
        ProductDto productDto = new ProductDto(product, file);
        return productDto;
    }
    public static Product getProductFromDto(ProductDto productDto, Category category) {
        Product product = new Product(productDto, category);
        return product;
    }
//    @Override
//    public void addProduct(ProductDto productDto, Category category) {
//        Product product = getProductFromDto(productDto, category);
//        productRepo.save(product);
//    }
    //add product together with the image blob

    public void addProduct(ProductDto productDto, Category category, MultipartFile imageFile) throws IOException {
        byte[] imageBytes = imageFile.getBytes();
//        byte[] imageBytes = Base64.getEncoder().encodeToString();
        Product product = getProductFromDto(productDto, category);
        product.setImageBlob(imageBytes);
        productRepo.save(product);
    }

    public void saveProductToDB(ProductDto productDto, Category category, MultipartFile file) {
//        Product product = new Product();
        Product product = getProductFromDto(productDto, category);
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        if (fileName.contains("..")) {
//
//            System.out.println("Not a valid file");
//        }
//        try {
//            product.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        product.setDescription(description);
//        product.setName(name);
//        product.setPrice(price);

        productRepo.save(product);
    }

    @Override
    public void updateProduct(Long productId, ProductDto productDto, Category category) {
        Product product = getProductFromDto(productDto, category);
        product.setId(productId);
        productRepo.save(product);
    }
    @Override
    public Product getProductById(Long productId) throws productNotExistException {
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if (!optionalProduct.isPresent())
            throw new productNotExistException("Product id is invalid" + productId);
        return optionalProduct.get();
    }


    @Override
    public void createProduct(ProductDto productDto, Category category) {
        Product product = new Product();
        product.setDescription(productDto.getDescription());
//        product.setImageURL(productDto.getImageURL());
        product.setName(productDto.getName());
        product.setCategory(category);
        product.setPrice(productDto.getPrice());
        productRepo.save(product);

    }

    public ProductDto getProductDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setDescription(product.getDescription());
//        productDto.setImageURL(product.getImageURL());
        productDto.setName(product.getName());
        productDto.setCategoryId(product.getCategory().getId());
        productDto.setPrice(product.getPrice());
        productDto.setId(product.getId());
        return productDto;
    }

//    @Override
//    public List<ProductDto> getAllProducts() {
//        return null;
//    }


    @Override
    public void updateProduct(ProductDto productDto, Long productId) throws Exception {
       Optional<Product> optionalProduct = productRepo.findById(productId);
       //throw an exception if product does not exist
        if (!optionalProduct.isPresent()) {
            throw new Exception("Product not present");
        }
        Product product = optionalProduct.get();
        product.setDescription(productDto.getDescription());
//        product.setImageURL(productDto.getImageURL());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        productRepo.save(product);
    }

    @Override
    public Product findById(Long productId) throws productNotExistException{
        Optional<Product> optionalProduct = productRepo.findById(productId);
        if (optionalProduct.isEmpty()) {
            throw new productNotExistException("product id is invalid" + productId);
        }
        return optionalProduct.get();
    }

//    @Override
//    public void saveProductToDB(MultipartFile file, String name, String description, int price) {
//
//    }


}
