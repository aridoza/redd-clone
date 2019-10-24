package com.ga.dao;

import java.util.List;

import com.ga.entity.User;
import com.ga.exception.EntityNotFoundException;

public interface UserDao {
	
	public List<User> listUsers();

	public User signup(User user);

//	public User login(User user) throws EntityNotFoundException;
	
	public User login(User user);
	
	public Long deleteUser(Long userId);
	
	public User getUserByUsername(String username);

	public User getUserByUserId(Long userId);
	
	public User getUserByEmail(String userEmail);
	
}