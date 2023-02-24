package com.ecomerce.guava.repository;

import com.ecomerce.guava.model.AuthenticationToken;
import com.ecomerce.guava.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<AuthenticationToken, Long> {
    AuthenticationToken findByUser(User user);

    AuthenticationToken findByToken(String token);
}
