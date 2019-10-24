package com.ga.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.ga.entity.User;
import com.ga.exception.EntityNotFoundException;
import com.ga.exception.LoginException;

public interface UserService extends UserDetailsService {

	public List<User> listUsers();
	
	public String signup(User user);
	
//	public String login(User user) throws LoginException, EntityNotFoundException;
	
	public String login(User user);
	
	public Long deleteUser(Long userId);

	UserDetails loadUserByEmail(String email);
		
}