package com.ga.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ga.entity.Comment;
import com.ga.entity.JwtResponse;
import com.ga.entity.Post;
import com.ga.entity.User;
import com.ga.exception.EntityNotFoundException;
import com.ga.exception.LoginException;
import com.ga.service.CommentService;
import com.ga.service.PostService;
import com.ga.service.UserService;

import antlr.Token;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	PostService postService;
	
	// Test route for sanity checking
	@GetMapping("/hello")
	public String hello() {
		return "Hello World!!";
	}
	
	// Authenticated
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/list")
	public List<User> listUsers() {
		return userService.listUsers();
	}
	
	// Public
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@Valid @RequestBody User user) {
        return ResponseEntity.ok(userService.signup(user));
	}
	
	// Public
	@PostMapping("/login")
//	public ResponseEntity<?> login(@RequestBody User user) throws LoginException, EntityNotFoundException {
	public ResponseEntity<?> login(@RequestBody User user) {
        return ResponseEntity.ok(userService.login(user));
    }
	
	// Authenticated
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/{userId}")
	public Long deleteUser(@PathVariable Long userId) {
		return userService.deleteUser(userId);
	}
	
	//Authenticated
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/post")
	public List<Post> getPostsByUsername(@RequestHeader Map<String, String> headers) {
		String jwtToken = headers.get("authorization").substring(7);
		
		List<Post> userPosts = postService.listPostsByUsername(jwtToken);
		return userPosts;
	}

	//Authenticated
	@PreAuthorize("isAuthenticated()")
	@GetMapping("/comment")
	public List<Comment> getCommentsByUsername(@RequestHeader Map<String, String> headers) {
		String jwtToken = headers.get("authorization").substring(7);
		
		List<Comment> userComments = commentService.listCommentsByUsername(jwtToken);
		return userComments;
	}
	
}
