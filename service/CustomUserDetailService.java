package com.rsl.event.service;

import java.nio.file.attribute.UserPrincipalNotFoundException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rsl.event.dao.UserRepository;
import com.rsl.event.entity.User;


@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		//Load User From Database
		User user = userRepository.findByEmail(username).orElseThrow(() -> new RuntimeException("User Not Found !!"));
		return user;
	}

}
