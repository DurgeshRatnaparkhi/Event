package com.rsl.event.models;

/**
 * Model class representing a JWT response.
 * Contains the JWT token and the associated username.
 */
public class JwtResponse {

    // JWT token string
    private String token;

    // Username associated with the token
    private String username;

    // Constructor to initialize JwtResponse with token and username
    public JwtResponse(String token, String username) {
        this.token = token;
        this.username = username;
    }

    // Getter method for the token
    public String getToken() {
        return token;
    }

    // Setter method for the token
    public void setToken(String token) {
        this.token = token;
    }

    // Getter method for the username
    public String getUsername() {
        return username;
    }

    // Setter method for the username
    public void setUsername(String username) {
        this.username = username;
    }
}
