package com.ecomerce.guava.service.impl;

import com.ecomerce.guava.dto.CategoryDto;
import com.ecomerce.guava.exceptions.ResourceNotFoundException;
import com.ecomerce.guava.model.Category;
import com.ecomerce.guava.repository.CategoryRepo;
import com.ecomerce.guava.service.CategoryService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
//    @Autowired
    private final CategoryRepo categoryRepo;

    public CategoryServiceImpl(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }
//    @Override
//    public List<Category> listCategories() {
//        return categoryRepo.findAll();
//    }


    @Override
    public List<CategoryDto> listCategories(MultipartFile file) {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryDto> categoryDtos = new ArrayList<>();
        for (Category category: categories) {
            CategoryDto categoryDto = getDtoFromCategory(category, file);
            categoryDtos.add(categoryDto);
//            productDtos.add(getProductDto(product));
        }
        return categoryDtos;
    }

    private static CategoryDto getDtoFromCategory(Category category, MultipartFile file) {
        CategoryDto categoryDto = new CategoryDto(category, file);
        return categoryDto;
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepo.save(category);
    }
    @Override
    public Category readCategory(String categoryName) {
        return categoryRepo.findByCategoryName(categoryName);
    }

    @Override
    public Optional<Category> readCategory(Integer categoryId) {
        return categoryRepo.findById(categoryId);
    }

    @Override
    public Category getCategoryById(Integer categoryId) {
        return categoryRepo.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "Id", categoryId));
    }

    @Override
    public Category updateCategory(Category category, Integer categoryId) {
        //check whether category with given id exixts
        Category existingCategory = categoryRepo.findById(categoryId).orElseThrow(
                () -> new ResourceNotFoundException("Category", "Id", categoryId));
        existingCategory.setCategoryName(category.getCategoryName());
        existingCategory.setDescription(category.getDescription());
//        existingCategory.setProducts(category.getProducts());
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
