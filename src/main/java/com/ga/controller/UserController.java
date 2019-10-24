package com.ga.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ga.entity.JwtResponse;
import com.ga.entity.User;
import com.ga.exception.EntityNotFoundException;
import com.ga.exception.LoginException;
import com.ga.service.UserService;

import antlr.Token;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	// Test route for sanity checking
	@GetMapping("/hello")
	public String hello() {
		return "Hello World!!";
	}
	
	@GetMapping("/list")
	public List<User> listUsers() {
		return userService.listUsers();
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody User user) {
		Map<String, String> data = new HashMap<>();
		
		data.put("token", new JwtResponse(userService.signup(user)).getToken());
		data.put("username", userService.loadUserByUsername(user.getUsername()).getUsername());
		
        return ResponseEntity.ok(data);
	}
	
	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody User user) throws LoginException, EntityNotFoundException {
	public ResponseEntity<?> login(@RequestBody User user) {
		Map<String, String> data = new HashMap<>();
		
		data.put("token", new JwtResponse(userService.login(user)).getToken());
		data.put("username", userService.loadUserByEmail(user.getEmail()).getUsername());
		
        return ResponseEntity.ok(data);
    }
	
	@DeleteMapping("/{userId}")
	public Long deleteUser(@PathVariable Long userId) {
		return userService.deleteUser(userId);
	}
	
}
