package com.ecomerce.guava.dto.checkout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckoutItemDto {
    private String productName;
    private int quantity;
    private double price;
    private long productId;
    private long userId;
}
