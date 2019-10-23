package com.ga.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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
	

	@PostMapping("")
	public Comment createComment(@RequestBody Comment comment) {
		//TODO: refactor for postId after Join
		return commentService.createComment(comment);
	}
	
}
