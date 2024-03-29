package com.ecomerce.guava.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@RequiredArgsConstructor
@Getter
@Setter

@Entity
@Table(name = "cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "created_date")
    private Date createdDate;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private int quantity;
}
