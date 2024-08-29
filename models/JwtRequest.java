package com.rsl.event.models;

/**
 * Model class representing a JWT request.
 * Contains the user's email and password.
 */
public class JwtRequest {

    // User's email address
    private String email;

    // User's password
    private String password;

    // Getter method for email
    public String getEmail() {
        return email;
    }

    // Setter method for email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter method for password
    public String getPassword() {
        return password;
    }

    // Setter method for password
    public void setPassword(String password) {
        this.password = password;
    }
}
