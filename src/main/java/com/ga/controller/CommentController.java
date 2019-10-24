package com.ga.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ga.entity.Comment;
import com.ga.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {
	
	private List<Comment> comments;

	@Autowired
	CommentService commentService;
	
	@GetMapping("/list")
	public List<Comment> getComments() {
		return commentService.listComments();
	}
	

	@PostMapping("/{username}/{postId}")
	public Comment createComment(@RequestBody Comment comment, @PathVariable Long postId, @PathVariable String username) {
		return commentService.createComment(comment, postId, username);
	}
	
	@DeleteMapping("/{commentId}")
	public Long deleteComment(@PathVariable Long commentId) {
		return commentService.deleteComment(commentId);
	}
	
}
