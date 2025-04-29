package com.saar.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.saar.blog.entity.User;
import com.saar.blog.exception.ResourceNotFoundException;
import com.saar.blog.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user=this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User", "email "+username, 0));
		
		return user;
	}
	/*
	 loadUserByUsername(String username): This method is part of the UserDetailsService interface. It is used to load the user details (like username, password, roles) based on the provided username (email).
	 */
	

}
