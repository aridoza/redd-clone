package com.ga.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping("/users")
	public List<User> getUsers() {
		List<User> users = new ArrayList<User>();
		
		users.add(new User("batman", "bat", "batman@superhero.com"));
		users.add(new User("spiderman", "spider", "spiderman@superhero.com"));
		
		return users;
	}
}
