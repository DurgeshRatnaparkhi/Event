package com.rsl.event.controller;

import java.security.Principal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rsl.event.entity.User;
import com.rsl.event.service.UserService;



@RestController
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private UserService userService;
    
    // Endpoint to retrieve all users
    @RequestMapping("/users")
    public List<User> getUser() {
        // Log to indicate the retrieval of users
        System.out.println("Getting Users.");
        
        // Return the list of users retrieved from the userService
        return userService.getUsers();
    }
     
    // Endpoint to retrieve the currently logged-in user's name
    @GetMapping("/current_user")
    public String getLoggedInUser(Principal principal) {
        // Return the name of the currently authenticated user
        return principal.getName();
    }
}
