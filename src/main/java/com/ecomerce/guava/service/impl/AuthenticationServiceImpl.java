package com.ecomerce.guava.service.impl;

import com.ecomerce.guava.exceptions.AuthenticationFailException;
import com.ecomerce.guava.model.AuthenticationToken;
import com.ecomerce.guava.model.User;
import com.ecomerce.guava.repository.TokenRepository;
import com.ecomerce.guava.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    TokenRepository tokenRepository;

    @Override
    public void saveConfirmationToken(AuthenticationToken authenticationToken) {
        tokenRepository.save(authenticationToken);
    }

    @Override
    public AuthenticationToken getToken(User user) {
        return tokenRepository.findByUser(user);
    }

    @Override
    public void authenticate(String token) {
        //null check
        if (Objects.isNull(token)) {
            //throw an exception
            throw new AuthenticationFailException("token not present");
        }
        if (Objects.isNull(getUser(token))) {
            throw new AuthenticationFailException("token not valid");
        }

    }

    @Override
    public User getUser(String token) {
        final AuthenticationToken authenticationToken = tokenRepository.findByToken(token);
        if (Objects.isNull(authenticationToken)) {
            return null;
        }
        //authenticationToken is not null
        return authenticationToken.getUser();
    }


}
