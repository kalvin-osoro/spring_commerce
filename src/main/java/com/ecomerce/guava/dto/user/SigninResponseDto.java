package com.ecomerce.guava.dto.user;

import lombok.*;

@RequiredArgsConstructor
@Getter
@Setter
public class SigninResponseDto {
    private String status;
    private String token;

    public SigninResponseDto(String status, String token) {
        this.status = status;
        this.token = token;
    }
}
