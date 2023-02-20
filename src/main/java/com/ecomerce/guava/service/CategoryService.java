package com.ecomerce.guava.service;

import com.ecomerce.guava.model.Category;
import com.ecomerce.guava.repository.CategoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {

    Category createCategory(Category category);

    List<Category> getAllCategories();

    Category getCategoryById(Integer id);

    Category updateCategory(Category category, Integer id);

    void deleteCategory(Integer id);

   void editCategory(int categoryId, Category category);

    boolean findById(int categoryId);

    void deleteCategory(int categoryId);


//    List<Category> listCategory();
}
