package com.ecomerce.guava.repository;

import com.ecomerce.guava.model.Cart;
import com.ecomerce.guava.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);

}
