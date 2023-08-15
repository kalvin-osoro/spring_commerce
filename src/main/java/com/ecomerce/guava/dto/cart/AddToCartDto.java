package com.ecomerce.guava.dto.cart;

import lombok.*;

import javax.validation.constraints.NotNull;

@RequiredArgsConstructor
@Getter
@Setter
public class AddToCartDto {
    private Long id;
    private @NotNull Long productId;
    private @NotNull Long quantity;

}
