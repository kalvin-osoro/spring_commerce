package com.kalvin.auth;

import com.kalvin.auth.models.User;
import com.kalvin.auth.repository.RoleRepository;
import com.kalvin.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.kalvin.auth.models.Role;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
@EnableTransactionManagement
public class AuthApplication {


	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder){
		return args->{

			if (roleRepository.findByAuthority("ADMIN").isEmpty()) return;

			Role adminRole = roleRepository.save(new Role("ADMIN"));
			roleRepository.save(new Role("USER"));

			Set<Role> roles = new HashSet<>();
			roles.add(adminRole);

			User admin = new User(1,"admin",passwordEncoder.encode("password"),roles);

			userRepository.save(admin);

		};
	}

}
