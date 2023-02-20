package com.ecomerce.guava.model;

import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

   @Column(name = "category_name", nullable = false)
    private @NotBlank String categoryName;

   @Column(name = "description")
   private @NotBlank String description;
   @Column(name = "image_url")
   private @NotBlank String imageUrl;



}
