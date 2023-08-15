package com.ecomerce.guava.dto;

import com.ecomerce.guava.model.Category;
import com.ecomerce.guava.model.Product;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Base64;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    //for create it can be optional
    //we need it for update

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String categoryName;

    @NotBlank
    private String description;

    private String imagePath;
//
//    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    Set<Product> products;

//    public CategoryDto(Category category, MultipartFile file) {
//        this.setId(category.getId());
//        this.setCategoryName(category.getCategoryName());
//        this.setDescription(category.getDescription());
//
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        if (fileName.contains("..")) {
//            System.out.println("Not a valid file");
//        }
//        try {
//            this.setImage(file.getBytes());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
