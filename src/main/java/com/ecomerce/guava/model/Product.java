package com.ecomerce.guava.model;

import com.ecomerce.guava.dto.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;

//@Data
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
   private @NotNull String name;

//    @Column(name = "brand", nullable = false)
//    private @NotNull String brand;
//    @Column(name = "imageURL", nullable = false)
//    private @NotNull String imageURL;
    @Column(name = "price", nullable = false)
    private @NotNull double price;
    @Column(name = "description", nullable = false)
    private @NotNull String description;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;

    //Many-to-many relationship
 @JsonIgnore
 @ManyToOne(fetch = FetchType.LAZY, optional = false)
 @JoinColumn(name = "category_id", nullable = false)
    Category category;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
    private List<Cart> carts;

    public Product(ProductDto productDto, Category category) {
        this.name = productDto.getName();
//        this.brand = productDto.getBrand();
//        this.imageURL = productDto.getImageURL();
        this.description = productDto.getDescription();
        this.price = productDto.getPrice();
        this.category = category;
    }

    public Product(String name, double price, String description, String image, Category category) {
        super();
        this.name = name;
//        this.brand = brand;
//        this.imageURL = imageURL;
        this.price = price;
        this.description = description;
        this.image = image;
        this.category = category;
    }

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public String getBrand() {
//        return name;
//    }
//
//    public void setBrand(String name) {
//        this.name = name;
//    }

//    public String getImageURL() {
//        return imageURL;
//    }
//
//    public void setImageURL(String imageURL) {
//        this.imageURL = imageURL;
//    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
//                ", brand='" + brand + '\'' +
//                ", imageURL='" + imageURL + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }


    public void setImageBlob(byte[] imageBytes) {
    }
}

