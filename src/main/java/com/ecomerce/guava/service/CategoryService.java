package com.ecomerce.guava.service;

import com.ecomerce.guava.dto.CategoryDto;
import com.ecomerce.guava.model.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface CategoryService {

    Object addNewCategory(String productDetails,
                         MultipartFile img
    );

    List<Category> getAllCategories();

    Optional<Category> readCategory(Long categoryId);
}
