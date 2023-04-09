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
import lombok.RequiredArgsConstructor;
import org.hibernate.resource.beans.internal.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
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

    @PostMapping("/add-product")
    public ResponseEntity<ApiResponse> addCategory(@RequestParam("categoryDetails") String categoryDetails,
                                                  @RequestParam ("img") MultipartFile img) {



        Object category = categoryService.addNewCategory(categoryDetails,img);
        boolean success = category != null;

        //response
        if (success) {
            return new ResponseEntity<>(new ApiResponse(true, "category has been added"), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(new ApiResponse(false, "category not added!"), HttpStatus.CREATED);
        }
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
