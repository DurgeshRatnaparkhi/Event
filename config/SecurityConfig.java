package com.rsl.event.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rsl.event.security.JwtAuthenticationEntryPoint;
import com.rsl.event.security.JwtAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEntryPoint entryPoint;

    @Autowired
    private JwtAuthenticationFilter filter;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())  // Disabling CSRF protection
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/home/**").authenticated()
                        .requestMatchers("/auth/login").permitAll()
                        .requestMatchers("/auth/create_user").permitAll()
                        .requestMatchers("/invoice").permitAll()
                        .requestMatchers("/allinvoices/**").permitAll()  // Allow access to all endpoints under /allinvoices
                        .requestMatchers("/allinvoices/{id}/pdf").permitAll()  // Allow access to PDF generation endpoint
                        .requestMatchers("/allquotation").permitAll()
                        .requestMatchers("/quotation").permitAll()
                        .requestMatchers("/vendor").permitAll()
                        .requestMatchers("/allvendor").permitAll()
                        .requestMatchers("/notification/token").permitAll() // Assuming you want this to be accessible
                        .requestMatchers("/invoice/{id}/pdf").permitAll()


                        .anyRequest().authenticated()
                )
                .exceptionHandling(ex -> ex.authenticationEntryPoint(entryPoint))  // Using custom entry point
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);  // Adding JWT filter before the default authentication filter
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }
}
