package com.ecomerce.guava.controller;

import com.ecomerce.guava.common.ApiResponse;
import com.ecomerce.guava.dto.CategoryDto;
import com.ecomerce.guava.dto.ProductDto;
import com.ecomerce.guava.model.Category;
import com.ecomerce.guava.model.Product;
import com.ecomerce.guava.service.CategoryService;
import com.ecomerce.guava.service.FileStorageService;
import com.ecomerce.guava.service.ProductService;
import com.ecomerce.guava.service.impl.CategoryServiceImpl;
import com.ecomerce.guava.utils.Helper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/home/category")
//@CrossOrigin(origins = "http://localhost:8090")
@CrossOrigin(origins = "*")
public class CategoryController {


    private final CategoryService categoryService;
//    private final CategoryDto categoryDto;
    private final FileStorageService fileStorageService;



    //get all products
    @GetMapping("/")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    //create category
    @PostMapping("/add-category")
    public ResponseEntity<ApiResponse> addCategory(@RequestParam("categoryDetails") String categoryDetails,
                                                  @RequestParam ("image") MultipartFile img) {

        ObjectMapper objectMapper = new ObjectMapper();
        Category category;
        try {
            category = objectMapper.readValue(categoryDetails, Category.class);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>(new ApiResponse(false, "Error converting Category details"), HttpStatus.BAD_REQUEST);
        }

        if (categoryService.isCategoryNameExists(category.getCategoryName())) {
            return new ResponseEntity<>(new ApiResponse(false, "Category already exists"), HttpStatus.CONFLICT);
        }

//        if (Helper.notNull(categoryService.readCategory(category.getCategoryName()))) {
//            return new ResponseEntity<>(new ApiResponse(false, "Category already exists"), HttpStatus.CONFLICT);
//        }

//        Category savedCategory = categoryService.addNewCategory(categoryDetails, img);
//        boolean success = savedCategory != null;

        Object category1 = categoryService.addNewCategory(categoryDetails,img);
        boolean success = category1 != null;

        //response
        if (success) {
            return new ResponseEntity<>(new ApiResponse(true, "category has been added"), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "category not added!"), HttpStatus.CREATED);
        }
    }

    @GetMapping("/view-category")
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

    @PostMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable("categoryId") Long categoryId,
                                                      @RequestParam("categoryDetails") String categoryDetails,
                                                      @RequestParam("image") MultipartFile file) {

        if (!categoryService.findById(categoryId)) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false, "category does not exist"), HttpStatus.NOT_FOUND);
        }
        categoryService.editCategory(categoryId, categoryDetails, file );
        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "category has been updated"), HttpStatus.OK);

    }

    @DeleteMapping("delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable ("categoryId") Long categoryId) {

        //authenticate the token

        categoryService.deleteCategory(categoryId);
        return new  ResponseEntity<>(new ApiResponse(true, "category has been removed"), HttpStatus.OK);
    }

    //get category by id
//    @GetMapping("{categoryId}")
//    public ResponseEntity<Category> getCategoryById(@PathVariable("categoryId")Integer categoryId) {
//        categoryService.findById(categoryId);
//        return new ResponseEntity<Category>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
//    }
//    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable("categoryId")Integer categoryId) {
//        if (!categoryService.findById(categoryId)) {
//            return new ResponseEntity<>(new ApiResponse(false,"Category with Id: "+ categoryId+ "does not exist!"),HttpStatus.NOT_FOUND);
//        }
////        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Category with Id: " +categoryId + ""), HttpStatus.OK);
////        return new ResponseEntity<Category>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
//    }

    //http://localhost:8080/category/update
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Category> updtCategory(@PathVariable("id") int categoryId,
//                                 @RequestBody Category category) {
//        System.out.println("category id" + categoryId);
//        return new ResponseEntity<>(categoryService.updateCategory(category,categoryId),HttpStatus.ACCEPTED);

//
//    }
//    @PutMapping("/update/{id}")
//    public ResponseEntity<ApiResponse> updtCategory(@PathVariable("id") int categoryId,
//                                                 @RequestBody Category category) {
//        System.out.println("category id" + categoryId);
//        if (!categoryService.findById(categoryId)) {
//            return new ResponseEntity<ApiResponse>(new ApiResponse(false,"Id: " +categoryId+ " does not exist"),HttpStatus.NOT_FOUND);
//
//        }
//        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"updated "+ category+ "with "+categoryId+ " successfully"),HttpStatus.OK);
//    }
//    @DeleteMapping("/delete/{categoryId}")
//    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") int categoryId) {
//
//        if (categoryService.findById(categoryId)) {
//            categoryService.deleteCategory(categoryId);
//            return new ResponseEntity<String>("Category deleted successfully!", HttpStatus.OK);
//        }
//        return new ResponseEntity<String>("Category does not exist",HttpStatus.NOT_FOUND);
//
//    }


}
