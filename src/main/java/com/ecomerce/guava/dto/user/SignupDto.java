package com.ecomerce.guava.dto.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@RequiredArgsConstructor
@Setter
@Getter
public class SignupDto {
    private String username;
    private String email;
    private String password;

}
