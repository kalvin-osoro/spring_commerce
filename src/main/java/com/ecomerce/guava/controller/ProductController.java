package com.ecomerce.guava.controller;

import com.ecomerce.guava.common.ApiResponse;
import com.ecomerce.guava.dto.ProductDto;
import com.ecomerce.guava.model.Category;
import com.ecomerce.guava.model.Product;
import com.ecomerce.guava.repository.CategoryRepo;
import com.ecomerce.guava.service.CategoryService;
import com.ecomerce.guava.service.FileStorageService;
import com.ecomerce.guava.service.ImageService;
import com.ecomerce.guava.service.ProductService;
import com.ecomerce.guava.service.impl.CategoryServiceImpl;
import com.ecomerce.guava.service.impl.ImageServiceImpl;
import com.ecomerce.guava.service.impl.ProductServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;
//    private final ProductDto productDto;
    private final FileStorageService fileStorageService;

    private final CategoryService categoryService;


    //get all products
    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    @PostMapping("/add-product")
    public ResponseEntity<ApiResponse> addProduct(@RequestParam("productDetails") String productDetails,
                                                  @RequestParam ("img") MultipartFile img) {
//        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
//        if (!optionalCategory.isPresent()) {
//            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Category does not exist"), HttpStatus.CONFLICT);
//        }
//        Category category = optionalCategory.get();
//        productService.saveProductToDB(productDto,category);
        Object product = productService.addNewProduct(productDetails, img);
        boolean success = product != null;

        //response
        if (success) {
            return new ResponseEntity<>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "Product not added!"), HttpStatus.CREATED);
        }
    }

    @GetMapping("/view-product")
    public ResponseEntity<Resource> getFileByName(@RequestParam (value="fileName")String fileName)
    {
        try {
            Resource file = fileStorageService.loadFileAsResourceByName(fileName);
            if (fileName.endsWith("PNG") || fileName.endsWith("png")) {
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + file.getFilename() + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE)
                        .body(file);
            } else {
                //return pdf
                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + file.getFilename() + "\"")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_PDF_VALUE)
                        .body(file);
            }
//            MediaType mediaType = (file.getFilename().endsWith("PNG")) ? MediaType.IMAGE_PNG : MediaType.IMAGE_JPEG;

//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=\"" + file.getFilename() + "\"")
//                    .contentType(mediaType)
//                    .body(file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //create an api to edit the product
//    @PostMapping("/update/{productId}")
//    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Long productId, @RequestBody @Valid ProductDto productDto) throws Exception {
//        Optional<Category> optionalCategory = categoryService.readCategory(productDto.getCategoryId());
//        if (!optionalCategory.isPresent()) {
//            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "Category does not exist"), HttpStatus.CONFLICT);
//        }
//        Category category = optionalCategory.get();
//        productService.updateProduct(productId, productDto, category );
//        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Product "+ productDto+ " has been updated"),HttpStatus.OK);
//    }
}
