package com.ecomerce.guava.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductDto {
    //for create it can be optional
    //we need it for update
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;
    private @NotNull String name;
    private @NotNull String imageURL;
    private @NotNull double price;
    private @NotNull String description;
    private @NotNull Integer categoryId;
}
