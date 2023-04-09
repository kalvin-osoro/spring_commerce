package com.ecomerce.guava.dto.checkout;

import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
public class CheckoutItemDto {
    private String productName;
    private int quantity;
    private double price;
    private long productId;
    private long userId;
}
