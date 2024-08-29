//package com.rsl.event.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import com.rsl.event.entity.User;
//import com.rsl.event.service.UserService;
//
//import jakarta.validation.Valid;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@RestController
//public class UserController {
//
//    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
//
//    @Autowired
//    private UserService userService;
//
//    // Get all users
//    @GetMapping("/user")
//    public ResponseEntity<List<User>> getAllUsers() {
//        logger.trace("Entering getAllUsers method.");
//        List<User> list = userService.getAllUserList();
//
//        if (list.isEmpty()) {
//            logger.warn("No users found.");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        logger.info("Found {} users.", list.size());
//        return ResponseEntity.ok(list); // Return 200 with the list of users
//    }
//
//    // Add a new user
//    @PostMapping("/user")
//    public ResponseEntity<User> addUser(@Valid @RequestBody User user) {
//        logger.trace("Entering addUser method with user: {}", user);
//        try {
//            User addedUser = userService.addUser(user);
//            logger.info("User added successfully with ID: {}", addedUser.getId());
//            return ResponseEntity.status(HttpStatus.CREATED).body(addedUser);
//        } catch (Exception e) {
//            logger.error("Error adding user: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    // Get user by email and password
//    @GetMapping("/user/{mail}/{pass}")
//    public ResponseEntity<User> getUser(@PathVariable("mail") String mail, @PathVariable("pass") String pass) {
//        logger.trace("Entering getUser method with mail: {} and pass: {}", mail, pass);
//        User user = userService.getUserByEmailAndPassword(mail, pass);
//
//        if (user == null) {
//            logger.warn("User not found with mail: {}", mail);
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        logger.info("User found with mail: {}", mail);
//        return ResponseEntity.ok(user); // Return 200 with the user
//    }
//
//    // Delete user by id
//    @DeleteMapping("/user/{userId}")
//    public ResponseEntity<Void> deleteUser(@PathVariable("userId") int userId) {
//        logger.trace("Entering deleteUser method with userId: {}", userId);
//        try {
//            userService.deleteUser(userId);
//            logger.info("User deleted successfully with ID: {}", userId);
//            return ResponseEntity.noContent().build(); // Return 204 if deletion is successful
//        } catch (Exception e) {
//            logger.error("Error deleting user: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//
//    // Update user by id
//    @PutMapping("/user/{userId}")
//    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("userId") int userId) {
//        logger.trace("Entering updateUser method with userId: {}", userId);
//        try {
//            UserService.updateUser(user, userId);
//            logger.info("User updated successfully with ID: {}", userId);
//            return ResponseEntity.ok(user); // Return 200 with the updated user
//        } catch (Exception e) {
//            logger.error("Error updating user: {}", e.getMessage(), e);
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//}
