package com.ecomerce.guava.dto.cart;

import lombok.*;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
public class CartDto {
    private List<CartItemDto> cartItems;
    private double totalCost;

}
