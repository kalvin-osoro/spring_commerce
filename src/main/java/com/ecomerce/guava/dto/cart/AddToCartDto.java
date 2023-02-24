package com.ecomerce.guava.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddToCartDto {
    private Long id;
    private @NotNull Long productId;
    private @NotNull Long quantity;

}
