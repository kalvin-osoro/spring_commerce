package com.ecomerce.guava.repository;

import com.ecomerce.guava.model.Cart;
import com.ecomerce.guava.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findAllByUserOrderByCreatedDateDesc(User user);

}
