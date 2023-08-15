package com.ecomerce.guava.service;

import com.ecomerce.guava.dto.cart.AddToCartDto;
import com.ecomerce.guava.dto.cart.CartDto;
import com.ecomerce.guava.model.Product;
import com.ecomerce.guava.model.User;
import org.springframework.stereotype.Service;

@Service
public interface CartService {

    void addToCart(AddToCartDto addToCartDto, User user);

    CartDto listCartItems(User user);

    void deleteCartItem(Long itemId, User user);
}
