package com.ecomerce.guava.model;

import jakarta.persistence.*;
import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;


    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    public User(String username,String email, String password) {
       this.username = username;
       this.email = email;
       this.password = password;

    }
}
