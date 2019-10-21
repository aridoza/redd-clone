package com.ga.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ga.entity.User;

@RestController
@RequestMapping("/user")
public class UserController {

	@GetMapping("/hello")
	public String hello() {
		return "Hello World!!";
	}
	
private List<User> users;
	
	@PostConstruct
	public void initializeUsers() {
		users = new ArrayList<User>();
		
		users.add(new User("batman", "bat", "batman@superhero.com"));
		users.add(new User("spiderman", "spider", "spiderman@superhero.com"));
		
	}

	@GetMapping("/users")
	public List<User> getUsers(){
		return users;
	}
	
	@GetMapping("/users/{username}")
	public User getUser(@PathVariable String username) {	
		User foundUser = users.stream().filter(user -> 
			user.getUsername().equalsIgnoreCase(username)).findFirst().orElse(null);
		
		return foundUser;
	}
}
