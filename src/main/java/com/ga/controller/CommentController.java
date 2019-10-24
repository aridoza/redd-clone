package com.ga.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.ga.service.CommentService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/comment")
public class CommentController {
	
	private List<Comment> comments;

	@Autowired
	CommentService commentService;
	
	// Public
	@GetMapping("/list")
	public List<Comment> getComments() {
		return commentService.listComments();
	}
	
	// Authenticated
	@PreAuthorize("isAuthenticated()")
//	@PostMapping("/{username}/{postId}")
	@PostMapping("/{postId}")
	public Comment createComment(
			@RequestBody Comment comment, 
			@PathVariable Long postId, 
			@RequestHeader Map<String, String> headers) {
		String jwtToken = headers.get("authorization").substring(7);
		
		return commentService.createComment(comment, postId, jwtToken);
	}
	
	// Authenticated
	@PreAuthorize("isAuthenticated()")
	@DeleteMapping("/{commentId}")
	public Long deleteComment(@PathVariable Long commentId) {
		return commentService.deleteComment(commentId);
	}
	
}
