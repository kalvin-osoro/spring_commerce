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
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private final CategoryRepo categoryRepo;



//    get all products
    @GetMapping("/")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> productList = productService.getAllProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }
    //code that does the job
    @GetMapping("/p")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> products = productService.getProducts();
        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @PostMapping("/add-product")
    public ResponseEntity<ApiResponse> addProduct(@RequestParam("productDetails") String productDetails,
                                                  @RequestParam("img") MultipartFile img) {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto;
        try {
            productDto = objectMapper.readValue(productDetails, ProductDto.class);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiResponse(false, "Error converting product details"), HttpStatus.BAD_REQUEST);
        }

        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "Category does not exist"), HttpStatus.BAD_REQUEST);
        }

        productService.createProduct(productDto, img, optionalCategory.get());

        return new ResponseEntity<>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
    }

    //............................................................................................................................


//    @PostMapping("/add-product")
//    public ResponseEntity<ApiResponse> addProduct(@RequestParam("productDetails") String productDetails,
//                                                  @RequestParam("img") MultipartFile img) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        ProductDto productDto;
//        try {
//            productDto = objectMapper.readValue(productDetails, ProductDto.class);
//        } catch (JsonProcessingException e) {
//            return new ResponseEntity<>(new ApiResponse(false, "Error converting product details"), HttpStatus.BAD_REQUEST);
//        }
//
//        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
//        if (!optionalCategory.isPresent()) {
//            return new ResponseEntity<>(new ApiResponse(false, "Category does not exist"), HttpStatus.BAD_REQUEST);
//        }
//
//        productService.createProduct(productDto, img, optionalCategory.get());
//
//        return new ResponseEntity<>(new ApiResponse(true, "Product has been added"), HttpStatus.CREATED);
//    }

    //............................................................................................................................



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

    @PostMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> editProduct(@PathVariable("productId") Long productId,
                                                   @RequestParam("productDetails") String productDetails,
                                                   @RequestParam("img") MultipartFile img) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        ProductDto productDto;
        try {
            productDto = objectMapper.readValue(productDetails, ProductDto.class);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiResponse(false, "Error converting product details"), HttpStatus.BAD_REQUEST);
        }

        Optional<Category> optionalCategory = categoryRepo.findById(productDto.getCategoryId());
        if (!optionalCategory.isPresent()) {
            return new ResponseEntity<>(new ApiResponse(false, "Category does not exist"), HttpStatus.BAD_REQUEST);
        }

        productService.editProduct(productId,productDto, img);
        return new ResponseEntity<>(new ApiResponse(true, "Product has been updated"), HttpStatus.OK);
    }

    @DeleteMapping("delete/{productId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable ("productId") Long productId) {

        //authenticate the token

        productService.deleteProduct(productId);
        return new  ResponseEntity<>(new ApiResponse(true, "product has been removed"), HttpStatus.OK);
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

//    @PostMapping("update/{productId}")
//    public ResponseEntity<ApiResponse> updateProduct(@PathVariable("productId") Long productId,
//                                                     @)
}
