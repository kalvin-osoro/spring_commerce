package com.ecomerce.guava.repository;

import com.ecomerce.guava.model.ERole;
import com.ecomerce.guava.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
