package com.ecomerce.guava.dto;

import com.ecomerce.guava.model.Product;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Base64;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductDto {
    //for create it can be optional
    //we need it for update
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private @NotNull String name;


    private @NotNull double price;
    private @NotNull String description;
    private String path;

    private @NotNull Long categoryId;


    public ProductDto(Product product, MultipartFile file) {
        this.setId(product.getId());
        this.setName(product.getName());
//        this.setBrand(product.getBrand());
//        this.setImageURL(product.getImageURL());
//        this.setImage(product.getImage());
        this.setDescription(product.getDescription());
        this.setPrice(product.getPrice());
//        this.setCategoryId(product.getCategory().getId());
//        this.setCategoryId(Math.toIntExact(product.getCategory().getId()));

//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        if (fileName.contains("..")) {
//
//            System.out.println("Not a valid file");
//        }
//        try {
//            product.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}
