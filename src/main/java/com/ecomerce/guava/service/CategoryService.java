package com.ecomerce.guava.service;

import com.ecomerce.guava.model.Category;
import com.ecomerce.guava.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> listCategories();

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
