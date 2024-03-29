package com.ecomerce.guava.dto.cart;

import com.ecomerce.guava.model.Cart;
import com.ecomerce.guava.model.Product;
import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
public class CartItemDto {
    private Long id;
    private Integer quantity;
    private Product product;

    public CartItemDto(Cart cart) {
        this.id = Long.valueOf(cart.getId());
        this.quantity = cart.getQuantity();
        this.setProduct(cart.getProduct());
    }
}
