package com.ecomerce.guava.service;

import com.ecomerce.guava.model.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    Object addNewCategory(String productDetails,
                         MultipartFile img
    );

    List<Category> getAllCategories();

    Optional<Category> readCategory(Long categoryId);

//    void editCategory(Long categoryId, Category updateCategory, MultipartFile img);
    ResponseEntity<Boolean> editCategory(Long categoryId, String categoryDetails, MultipartFile img);

    boolean findById(Long categoryId);

    public void deleteCategory(Long categoryId);
}
