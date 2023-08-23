package com.ecomerce.guava.service.impl;

import com.ecomerce.guava.dto.ResponseDto;
import com.ecomerce.guava.dto.user.SigninDto;
import com.ecomerce.guava.dto.user.SigninResponseDto;
import com.ecomerce.guava.dto.user.SignupDto;
import com.ecomerce.guava.exceptions.AuthenticationFailException;
import com.ecomerce.guava.exceptions.CustomException;
import com.ecomerce.guava.model.AuthenticationToken;
import com.ecomerce.guava.model.User;
import com.ecomerce.guava.repository.UserRepository;
import com.ecomerce.guava.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import jakarta.xml.bind.DatatypeConverter;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

     private final AuthenticationServiceImpl authenticationService;

    @Transactional
    @Override
    public ResponseDto signup(SignupDto signupDto) {
        //check if user is already present
        if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail()))) {
            //we have a user
            throw new CustomException("user already present");
        }

        //hash the password
        String encryptedPassword = signupDto.getPassword();
        try {
            encryptedPassword = hashPassword(signupDto.getPassword());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        //save the user
//        User user = new User(signupDto.getUsername(), signupDto.getEmail(),encryptedPassword);
//        userRepository.save(user);
        User user = new User();
        user.setUsername(signupDto.getUsername());
        user.setEmail(signupDto.getEmail());
        user.setPassword(encryptedPassword);

        userRepository.save(user);

        //create the token
        final AuthenticationToken authenticationToken = new AuthenticationToken(user);
        authenticationService.saveConfirmationToken(authenticationToken);


        ResponseDto responseDto = new ResponseDto("success", "User created successfully");
        return responseDto;
    }



    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return hash;
    }
    @Override
    public SigninResponseDto signin(SigninDto signinDto) {
        //find user by email
        User user = userRepository.findByEmail(signinDto.getEmail());

        if (Objects.isNull(user)) {
            throw new AuthenticationFailException("User is not valid");
        }

        //hash the password
        try {
            if(!user.getPassword().equals(hashPassword(signinDto.getPassword())) ) {
                throw new AuthenticationFailException("Wrong password");
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        //compare the password in DB

        //if password match
        AuthenticationToken token = authenticationService.getToken(user);
        //retrieve the token
        if (Objects.isNull(token)) {
            throw new CustomException("Token is not present");
        }
        return new SigninResponseDto("Success",token.getToken());
        //return response
    }
}
