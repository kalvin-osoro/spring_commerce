//package com.ecomerce.guava.controller;
//
//import com.ecomerce.guava.dto.ResponseDto;
//import com.ecomerce.guava.dto.user.SigninDto;
//import com.ecomerce.guava.dto.user.SigninResponseDto;
//import com.ecomerce.guava.dto.user.SignupDto;
//import com.ecomerce.guava.service.UserService;
//import com.ecomerce.guava.service.impl.UserServiceImpl;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.concurrent.ThreadPoolExecutor;
//
//@RequestMapping("user")
//@RestController
//@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
//public class UserController {
//
//
//   private final UserServiceImpl userService;
//
//    //signup
//    @PostMapping("/signup")
//    public ResponseDto signup(@RequestBody SignupDto signupDto) {
//        return userService.signup(signupDto);
//    }
//    //signin
//    @PostMapping("/signin")
//    SigninResponseDto signin(@RequestBody SigninDto signinDto) {
//        return userService.signin(signinDto);
//    }
//}
