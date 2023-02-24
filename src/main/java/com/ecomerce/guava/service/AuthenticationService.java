package com.ecomerce.guava.service;

import com.ecomerce.guava.model.AuthenticationToken;
import com.ecomerce.guava.model.User;

public interface AuthenticationService {

    void saveConfirmationToken(AuthenticationToken authenticationToken);

    AuthenticationToken getToken(User user);

    void authenticate(String token);

    User getUser(String token);
}
