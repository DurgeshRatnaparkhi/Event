package com.rsl.event.controller;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rsl.event.entity.User;
import com.rsl.event.models.JwtRequest;
import com.rsl.event.models.JwtResponse;
import com.rsl.event.security.JwtHelper;
import com.rsl.event.service.UserService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private JwtHelper helper;

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody JwtRequest request) {
        this.doAuthenticate(request.getEmail(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        JwtResponse response = new JwtResponse(token, userDetails.getUsername());
        return ResponseEntity.ok(response);
    }

    private void doAuthenticate(String email, String password) {
        try {
            manager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or Password", e);
        }
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentialsException(BadCredentialsException e) {
        logger.error("Authentication failed: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials provided!");
    }
    
    @PostMapping("/create_user")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        // Ensure passwords match before creating user
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}
