package com.ecomerce.guava.service;

import com.ecomerce.guava.dto.CategoryDto;
import com.ecomerce.guava.model.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

//    List<Category> listCategories();

    List<CategoryDto> listCategories(MultipartFile file);

    Category createCategory(Category category);

    Category getCategoryById(Integer id);

    Category updateCategory(Category category, Integer id);

    void deleteCategory(Integer id);

   void editCategory(int categoryId, Category category);

    boolean findById(int categoryId);

    void deleteCategory(int categoryId);

    Category readCategory(String categoryName);

    Optional<Category> readCategory(Integer categoryId);


//    List<Category> listCategory();
}
