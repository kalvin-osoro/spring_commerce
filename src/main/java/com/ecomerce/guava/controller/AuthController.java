package com.ecomerce.guava.controller;

import com.ecomerce.guava.dto.ResponseDto;
import com.ecomerce.guava.dto.user.SigninDto;
import com.ecomerce.guava.dto.user.SigninResponseDto;
import com.ecomerce.guava.dto.user.SignupDto;
import com.ecomerce.guava.service.AuthService;
import com.ecomerce.guava.service.UserService;
import com.ecomerce.guava.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.ThreadPoolExecutor;

@RequestMapping("user")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {


    private final AuthService authService;

   }
