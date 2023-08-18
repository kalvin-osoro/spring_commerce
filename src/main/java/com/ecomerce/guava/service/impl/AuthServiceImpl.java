package com.ecomerce.guava.service.impl;

import com.ecomerce.guava.model.ERole;
import com.ecomerce.guava.model.Role;
import com.ecomerce.guava.model.User;
import com.ecomerce.guava.payload.request.SignupRequest;
import com.ecomerce.guava.payload.response.MessageResponse;
import com.ecomerce.guava.repository.RoleRepository;
import com.ecomerce.guava.repository.UserRepository;
import com.ecomerce.guava.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

//@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

   @Autowired
    private  UserRepository userRepository;
   @Autowired
   private  RoleRepository roleRepository;
   @Autowired
   private  PasswordEncoder passwordEncoder;

    @Override
    public MessageResponse registerUser(SignupRequest signupRequest) {
        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return new MessageResponse("Error: Username is already taken!");
        }
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return new MessageResponse("Error: Email is already in use!");
        }
        //create new user's account
        User user = new User(signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(adminRole);
                        break;
                    case "mng":
                        Role mngRole = roleRepository.findByName(ERole.ROLE_MANAGER)
                                .orElseThrow(() ->new RuntimeException("Error: Role is not found"));
                        roles.add(mngRole);
                        break;

                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found"));
                        roles.add(userRole);
                }
            });
        }
        user.setRoles(roles);
        userRepository.save(user);

        return new MessageResponse("User registered successfully!");

    }
}
