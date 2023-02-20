package com.ecomerce.guava.repository;

import com.ecomerce.guava.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long > {
}
