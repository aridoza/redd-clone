package com.ga.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ga.config.JwtUtil;
import com.ga.dao.UserDao;
import com.ga.entity.JwtResponse;
import com.ga.entity.User;
import com.ga.exception.EntityNotFoundException;
import com.ga.exception.LoginException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	JwtUtil jwtUtil;

	@Override
	public List<User> listUsers() {
		return userDao.listUsers();
	}

	@Override
	public JwtResponse signup(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

		if (userDao.signup(user).getId() != null) {
			UserDetails userDetails = loadUserByUsername(user.getUsername());

			return new JwtResponse(jwtUtil.generateToken(userDetails), user.getUsername());
		}

		return null;
	}

	//public String login(User user) throws LoginException, EntityNotFoundException {
	
	@Override
	public JwtResponse login(User user) {
		User foundUser = userDao.login(user);
		if (foundUser != null && foundUser.getId() != null
				&& bCryptPasswordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
			UserDetails userDetails = loadUserByUsername(foundUser.getUsername());

			return new JwtResponse(jwtUtil.generateToken(userDetails), foundUser.getUsername());
		}
		//throw new LoginException("Username or password incorrect.");
		return null;
	}

	@Override
	public Long deleteUser(Long userId) {
		return userDao.deleteUser(userId);
	}

	@Autowired
	@Qualifier("encoder")
	PasswordEncoder bCryptPasswordEncoder;

	@Override 
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDao.getUserByUsername(username);

		if (user == null)
			throw new UsernameNotFoundException("Unknown user: " + username);

		return new org.springframework.security.core.userdetails.User(user.getUsername(),
				bCryptPasswordEncoder.encode(user.getPassword()), true, true, true, true, getGrantedAuthorities(user));
	}
	
	@Override
	public UserDetails loadUserByEmail(String email) {
		User user = userDao.getUserByEmail(email);

		if (user == null)
			throw new UsernameNotFoundException("Unknown user: " + email);

		return new org.springframework.security.core.userdetails.User(user.getUsername(),
				bCryptPasswordEncoder.encode(user.getPassword()), true, true, true, true, getGrantedAuthorities(user));
	}

	private List<GrantedAuthority> getGrantedAuthorities(User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		authorities.add(new SimpleGrantedAuthority(user.getUsername()));

		return authorities;
	}

}