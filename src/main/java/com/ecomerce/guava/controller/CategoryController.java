package com.ecomerce.guava.controller;

import com.ecomerce.guava.common.ApiResponse;
import com.ecomerce.guava.model.Category;
import com.ecomerce.guava.service.CategoryService;
import com.ecomerce.guava.service.impl.CategoryServiceImpl;
import org.hibernate.resource.beans.internal.Helper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "http://localhost:8090")
public class CategoryController {
    @Autowired
   private CategoryServiceImpl categoryService;

    //list all categories
    @GetMapping("/list")
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> body = categoryService.listCategories();

        return new ResponseEntity<List<Category>>(body,HttpStatus.OK);
    }
    //create category
    @PostMapping("/create")
//    public String createCategory(@RequestBody Category category) {
//        categoryService.createCategory(category);
//        return "success" + HttpStatus.CREATED;
//    }
    public ResponseEntity<ApiResponse> createCategory(@Valid @RequestBody Category category) {
        categoryService.createCategory(category);
        return new ResponseEntity<>(new ApiResponse(true,"created the category: "+category),HttpStatus.CREATED);
    }

    //get category by id
    @GetMapping("{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable("categoryId")Integer categoryId) {
        categoryService.findById(categoryId);
        return new ResponseEntity<Category>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
    }
//    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable("categoryId")Integer categoryId) {
//        if (!categoryService.findById(categoryId)) {
//            return new ResponseEntity<>(new ApiResponse(false,"Category with Id: "+ categoryId+ "does not exist!"),HttpStatus.NOT_FOUND);
//        }
////        return new ResponseEntity<ApiResponse>(new ApiResponse(true, "Category with Id: " +categoryId + ""), HttpStatus.OK);
////        return new ResponseEntity<Category>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
//    }

    //http://localhost:8080/category/update
    @PutMapping("/update/{id}")
//    public ResponseEntity<Category> updtCategory(@PathVariable("id") int categoryId,
//                                 @RequestBody Category category) {
//        System.out.println("category id" + categoryId);
//        return new ResponseEntity<>(categoryService.updateCategory(category,categoryId),HttpStatus.ACCEPTED);

//
//    }
    public ResponseEntity<ApiResponse> updtCategory(@PathVariable("id") int categoryId,
                                                 @RequestBody Category category) {
        System.out.println("category id" + categoryId);
        if (!categoryService.findById(categoryId)) {
            return new ResponseEntity<ApiResponse>(new ApiResponse(false,"Id: " +categoryId+ " does not exist"),HttpStatus.NOT_FOUND);

        }
        return new ResponseEntity<ApiResponse>(new ApiResponse(true,"updated "+ category+ "with "+categoryId+ " successfully"),HttpStatus.OK);
    }
    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable("categoryId") int categoryId) {

        if (categoryService.findById(categoryId)) {
            categoryService.deleteCategory(categoryId);
            return new ResponseEntity<String>("Category deleted successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Category does not exist",HttpStatus.NOT_FOUND);

    }


}
