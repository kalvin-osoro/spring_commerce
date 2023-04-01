package com.ecomerce.guava.dto;

import com.ecomerce.guava.model.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.Base64;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CategoryDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String categoryName;

    @NotBlank
    private String description;

    @Lob
    @Column(name = "image", columnDefinition = "BLOB")
    private byte[] image;

    public CategoryDto(Category category, MultipartFile file) {
        this.setId(category.getId());
        this.setCategoryName(category.getCategoryName());
        this.setDescription(category.getDescription());

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            System.out.println("Not a valid file");
        }
        try {
            this.setImage(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
