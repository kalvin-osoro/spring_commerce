package com.ecomerce.guava.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;


@RequiredArgsConstructor
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   @Column(name = "category_name", nullable = false)
    private @NotBlank String categoryName;

   @Column(length = 1000, name = "description")
   private @NotBlank String description;

   private @NotBlank String imagePath;

//    @JsonManagedReference
   @OneToMany(mappedBy = "category", fetch = FetchType.EAGER,
    cascade = CascadeType.ALL)
    Set<Product> products;

}
