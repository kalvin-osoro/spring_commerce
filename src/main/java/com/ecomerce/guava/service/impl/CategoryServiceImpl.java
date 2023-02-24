package com.ecomerce.guava.service.impl;

import com.ecomerce.guava.exceptions.ResourceNotFoundException;
import com.ecomerce.guava.model.Category;
import com.ecomerce.guava.repository.CategoryRepo;
import com.ecomerce.guava.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;


//    public void createCategory(Category category) {
//        categoryRepo.save(category);
//    }
//    public List<Category> listCategory() {
//       return categoryRepo.findAll();
//    }
//    public void editCategory()


    @Override
    public Category createCategory(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        return categoryRepo.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "Id", categoryId));
    }

    @Override
    public Category updateCategory(Category category, Integer id) {
        //check whether category with given id exixts
        Category existingCategory = categoryRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category", "Id", id));
        existingCategory.setCategoryName(category.getCategoryName());
        existingCategory.setDescription(category.getDescription());
        existingCategory.setImageUrl(category.getImageUrl());
        //save existing category to database
        categoryRepo.save(existingCategory);
        return existingCategory;
    }
    @Override
    public void editCategory(int categoryId, Category updateCategory) {
        Category category = categoryRepo.getById(categoryId);
        category.setCategoryName(category.getCategoryName());
        category.setDescription(category.getDescription());
        category.setImageUrl(category.getImageUrl());
        categoryRepo.save(category);
    }

    @Override
    public void deleteCategory(Integer id) {
        //check if category exists in DB
        categoryRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Category","Id",id));
        categoryRepo.deleteById(id);
    }

    @Override
    public boolean findById(int categoryId) {
        return categoryRepo.findById(categoryId).isPresent();
    }

    @Override
    public void deleteCategory(int categoryId) {
        categoryRepo.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category","categoryId",categoryId));
        categoryRepo.deleteById(categoryId);

    }
}
