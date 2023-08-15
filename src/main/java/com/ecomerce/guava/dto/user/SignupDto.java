package com.ecomerce.guava.dto.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@RequiredArgsConstructor
@Setter
@Getter
//@Entity
public class SignupDto {


    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
