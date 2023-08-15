package com.ecomerce.guava.service.impl;

import com.ecomerce.guava.dto.CategoryDto;
import com.ecomerce.guava.exceptions.CustomException;
import com.ecomerce.guava.model.Category;
import com.ecomerce.guava.repository.CategoryRepo;
import com.ecomerce.guava.service.CategoryService;
import com.ecomerce.guava.service.FileStorageService;
import com.ecomerce.guava.utils.ImageUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//import static org.hibernate.id.enhanced.StandardOptimizerDescriptor.log;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final FileStorageService fileStorageService;

    @Value("${spring.server.name}")
    private String serverName;



    @Override
    public Object addNewCategory(String categoryDetails,
                                 MultipartFile img) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            CategoryDto categoryDto = mapper.readValue(
                    categoryDetails, CategoryDto.class);
            if (categoryDto == null) throw new RuntimeException("Bad request");
            Category category = new Category();
            category.setCategoryName(categoryDto.getCategoryName());
            category.setDescription(categoryDto.getDescription());


            Category savedcategory = categoryRepo.save(category);

            if (img.isEmpty()) {
                throw new RuntimeException("Image file is empty");
            }

            String imagePath = fileStorageService.saveFileWithSpecificFileNameV(
                    "product_" + savedcategory.getId() + ".PNG", img, ImageUtils.getSubFolder());

            //save
            List<String> filePathList = new ArrayList<>();
            filePathList.add(imagePath);
            List<String> downloadUrlList = new ArrayList<>();
            for (String filePath : filePathList) {

                String fileDownLoadUri = UriComponentsBuilder.fromUriString(serverName)
                        .path("/product/view-product")
                        .queryParam("fileName", filePath)
                        .build()
                        .encode()
                        .toUriString();
                log.info("The composed path: " + fileDownLoadUri);

                //save to db
                category.setImagePath(fileDownLoadUri);
                categoryRepo.save(category);


                log.info("downloadUrl for category is.............. {}", fileDownLoadUri);
                log.info("filePath is for category is.............. {}", filePath);


            }
//            return true;
            return ResponseEntity.ok().body(true);
        } catch (Exception e) {
            log.error("Error occurred while adding category", e);
        }
        return false;
    }
    @Override
//    public void editCategory(Long categoryId, Category updateCategory, MultipartFile img)
    public ResponseEntity<Boolean> editCategory(Long categoryId, String categoryDetails, MultipartFile img)
    {
        try {
            ObjectMapper mapper = new ObjectMapper();
            CategoryDto categoryDto = mapper.readValue(
                    categoryDetails, CategoryDto.class);
//            if (categoryDto == null) throw new RuntimeException("Bad request");
//            Category category = new Category();
            Category category = categoryRepo.getById(categoryId);
            category.setCategoryName(categoryDto.getCategoryName());
            category.setDescription(categoryDto.getDescription());

            Category savedcategory = categoryRepo.save(category);

            if (img.isEmpty()) {
                throw new RuntimeException("Image file is empty");
            }

            String imagePath = fileStorageService.saveFileWithSpecificFileNameV(
                    "category_" + savedcategory.getId() + ".PNG", img, ImageUtils.getSubFolder());

            //save
            List<String> filePathList = new ArrayList<>();
            filePathList.add(imagePath);
            List<String> downloadUrlList = new ArrayList<>();
            for (String filePath : filePathList) {

                String fileDownLoadUri = UriComponentsBuilder.fromUriString(serverName)
                        .path("/category/view-category")
                        .queryParam("fileName", filePath)
                        .build()
                        .encode()
                        .toUriString();
                log.info("The composed path for category:............ " + fileDownLoadUri);

                //save to db
                category.setImagePath(fileDownLoadUri);
                categoryRepo.save(category);


                log.info("downloadUrl for category ................ is {}", fileDownLoadUri);
                log.info("filePath is for category ................ {}", filePath);


            }
//            return true;
//            return ResponseEntity.ok().body(true);
            return ResponseEntity.ok().body(true);
        } catch (Exception e) {
            log.error("Error occurred while adding product", e);
        }
        return ResponseEntity.ok().body(false);
//        return false;
    }

    @Override
    public boolean findById(Long categoryId) {
        return categoryRepo.findById(categoryId).isPresent();
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Optional<Category> optionalCategory = categoryRepo.findById(categoryId);

        if (optionalCategory.isEmpty()) {

            throw new CustomException("category  does not exist " + categoryId);
        }
        Category category = optionalCategory.get();

        categoryRepo.delete(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Optional<Category> readCategory(Long categoryId) {
        return categoryRepo.findById(categoryId);
    }
}
