package com.ecomerce.guava.controller;

import com.ecomerce.guava.payload.request.SignupRequest;
import com.ecomerce.guava.payload.response.MessageResponse;
import com.ecomerce.guava.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/auth")
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AuthController {


    private final AuthService authService;


    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        MessageResponse response = authService.registerUser(signupRequest);
        return ResponseEntity.ok(response);
    }

   }
