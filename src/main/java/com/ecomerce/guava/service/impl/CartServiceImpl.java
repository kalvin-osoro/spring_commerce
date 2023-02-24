package com.ecomerce.guava.service.impl;

import com.ecomerce.guava.dto.cart.AddToCartDto;
import com.ecomerce.guava.dto.cart.CartDto;
import com.ecomerce.guava.dto.cart.CartItemDto;
import com.ecomerce.guava.exceptions.CustomException;
import com.ecomerce.guava.model.Cart;
import com.ecomerce.guava.model.Product;
import com.ecomerce.guava.model.User;
import com.ecomerce.guava.repository.CartRepository;
import com.ecomerce.guava.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    ProductServiceImpl productService;

    @Autowired
    CartRepository cartRepository;

    @Override
    public void addToCart(AddToCartDto addToCartDto, User user) {

        //validate if the product id is valid
       Product product =   productService.findById(addToCartDto.getProductId());

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(Math.toIntExact(addToCartDto.getQuantity()));
        cart.setCreatedDate(new Date());

        //save the cart
        cartRepository.save(cart);
    }

    @Override
    public CartDto listCartItems(User user) {
      List<Cart> cartList = cartRepository.findAllByUserOrderByCreatedDateDesc(user);

      //change cart into cartItemDto
      List<CartItemDto> cartItems = new ArrayList<>();
      double totalCost =0;
      for (Cart cart: cartList) {
          CartItemDto cartItemDto = new CartItemDto(cart);
          totalCost += cartItemDto.getQuantity() * cart.getProduct().getPrice();
          cartItems.add(cartItemDto);
      }
      CartDto cartDto = new CartDto();
      cartDto.setTotalCost(totalCost);
      cartDto.setCartItems(cartItems);
      return cartDto;

    }

    @Override
    public void deleteCartItem(Long cartItemId, User user) {
        //the item id belongs to user
        Optional<Cart> optionalCart = cartRepository.findById(cartItemId);
        if (optionalCart.isEmpty()) {
            throw new  CustomException("cart item is invalid: " + cartItemId);
        }
        Cart cart = optionalCart.get();

        if (cart.getUser() !=user) {
            throw new CustomException("cart item does not belong to user: " + cartItemId);
        }
        cartRepository.delete(cart);
    }
}
