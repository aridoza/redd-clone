package com.ga.service;

import java.util.List;

import com.ga.entity.User;

public interface UserService {

	public List<User> listUsers();
	
	public User signup(User user);
		
}