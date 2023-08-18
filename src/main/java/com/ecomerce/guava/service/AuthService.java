package com.ecomerce.guava.service;

import com.ecomerce.guava.payload.request.SignupRequest;
import com.ecomerce.guava.payload.response.MessageResponse;

public interface AuthService {

    public MessageResponse registerUser(SignupRequest signupRequest);
}
