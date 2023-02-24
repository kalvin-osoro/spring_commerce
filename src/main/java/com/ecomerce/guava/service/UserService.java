package com.ecomerce.guava.service;

import com.ecomerce.guava.dto.ResponseDto;
import com.ecomerce.guava.dto.user.SigninDto;
import com.ecomerce.guava.dto.user.SigninResponseDto;
import com.ecomerce.guava.dto.user.SignupDto;

public interface UserService {
    ResponseDto signup(SignupDto signupDto);


    SigninResponseDto signin(SigninDto signinDto);
}
