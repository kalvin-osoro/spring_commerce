package com.ecomerce.guava.model;

import com.ecomerce.guava.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.validation.constraints.NotNull;
import java.util.List;

@RequiredArgsConstructor
@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Table(name = "products")
public class Product {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
   private @NotNull String name;


    @Column(name = "price", nullable = false)
    private @NotNull double price;

    @Column(length = 1000, name = "description", nullable = false)
    private @NotNull String description;


    private String imagePath;

//    private @NotNull Long categoryId;

    //Many-to-many relationship
 @JsonIgnore
 @ManyToOne(fetch = FetchType.LAZY, optional = false)
 @JoinColumn(name = "category_id", nullable = false)
    Category category;

}

