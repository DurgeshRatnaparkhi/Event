package com.rsl.event.service;

import java.util.List;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import jakarta.validation.Valid;

import org.springframework.util.StringUtils;

import com.rsl.event.dao.UserRepository;
import com.rsl.event.entity.User;

/**
 * Service class for handling user-related operations.
 */
@Service
public class UserService {

    // Logger for logging events in this service
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    // Repository for accessing user data
    @Autowired
    private UserRepository userRepository;
    
    // Encoder for encrypting user passwords
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**    
     * Retrieves all users from the repository.
     * 
     * @return List of all users
     */
    public List<User> getUsers() {
        // Fetches all users from the repository
        return userRepository.findAll();
    }
   
    /**
     * Creates a new user with a unique ID and encrypted password.
     * 
     * @param user the user to be created
     * @return the saved user
     */
    public User createUser(@Valid User user) {
        // Validate if password and confirmPassword match
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            throw new IllegalArgumentException("Password and confirm password do not match.");
        }

        // Set a unique ID for the new user
        user.setId(UUID.randomUUID().toString());
        
        // Encrypt the user's password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        // Save the user to the repository and return the saved entity
        return userRepository.save(user);
    }

	public User getUserByEmailAndPassword(String mail, String pass) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<User> getAllUserList() {
		// TODO Auto-generated method stub
		return null;
	}

	public User addUser(@Valid User user) {
		// TODO Auto-generated method stub
		return null;
	}

	public void deleteUser(int userId) {
		// TODO Auto-generated method stub
		
	}

	public static void updateUser(User user, int userId) {
		// TODO Auto-generated method stub
		
	}
}
