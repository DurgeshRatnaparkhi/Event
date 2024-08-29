package com.rsl.event.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class AppConfig {

    // Bean for PasswordEncoder using BCryptPasswordEncoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();  // BCrypt is a hashing function for passwords
    }

    // Example of an in-memory user details service (commented out)
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//                .username("abhi")
//                .password(passwordEncoder().encode("abhi"))
//                .roles("ADMIN")
//                .build();  // Creating a user with username "abhi" and role "ADMIN"
//        return new InMemoryUserDetailsManager(user);  // Returning an in-memory user details manager with the created user
//    }
}
