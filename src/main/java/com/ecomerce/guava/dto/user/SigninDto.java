package com.ecomerce.guava.dto.user;

import jakarta.persistence.Access;
import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
public class SigninDto {
    private String email;
    private String password;


}

