package com.rsl.event.dao;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rsl.event.entity.User;
	// Repository interface for User entity
	@Repository
	public interface UserRepository extends JpaRepository<User, String> {
	    // Method to find a user by email and password
	    public Optional<User> findByEmail(String email);
	}          


