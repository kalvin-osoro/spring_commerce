package com.ecomerce.guava.service;

import com.ecomerce.guava.payload.request.LoginRequest;
import com.ecomerce.guava.payload.request.SignupRequest;
import com.ecomerce.guava.payload.response.MessageResponse;
import com.ecomerce.guava.service.impl.UserDetailsImpl;
import org.springframework.security.core.Authentication;

public interface AuthService {

    public MessageResponse registerUser(SignupRequest signupRequest);

    public String authenticateUser(LoginRequest loginRequest);

    UserDetailsImpl getUserDetails(Authentication authentication);
}
