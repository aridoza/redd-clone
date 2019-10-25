package com.ga.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ga.entity.Comment;
import com.ga.entity.Post;
import com.ga.entity.User;
import com.ga.service.CommentService;
import com.ga.service.PostService;
import com.ga.service.UserService;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/post")
public class PostController {
	
	private List<Post> posts;
	
	@Autowired
	PostService postService;
	
	@Autowired
	CommentService commentService;
	
	@GetMapping("/hello")
    public String helloWorld() {
        return "Hello World!!";
    }
	
//	@PostConstruct
//	public void initializePosts() {
//		posts = new ArrayList<Post>();
//		
//		posts.add(new Post("hello", "this is the first post"));
//		posts.add(new Post("good day", "this is the second post"));
//		
//	}
	
	
	// Public
	@GetMapping("/posts")
	public List<Post> getPosts(){
		return postService.listPosts();
	}
	
//	@GetMapping("/posts/{postId}")
//	public Post getPost(@PathVariable Long postId) {	
//		Post foundPost = posts.stream().filter(post -> 
//			post.getPo().equals(pos)).findFirst().orElse(null);
//		
//		return foundPost;
//	}
	
	// Authenticated
	@PutMapping("/{postId}")
	public Post updatePost(@RequestBody Post post, @PathVariable Long postId) {
		return postService.updatePost(post, postId);
	}
	
	// Authenticated
	@PreAuthorize("isAuthenticated()")  
	@PostMapping("/{username}")
	public Post createPost(@RequestBody Post post, @PathVariable String username) {
		return postService.createPost(post, username);
	}
	
	// Authenticated
	@DeleteMapping("/{postId}")
	@PreAuthorize("isAuthenticated()")
	public Long deletePost(@PathVariable Long postId) {
		return postService.deletePost(postId);
	}
	
	// Authenticated
	@GetMapping("/{postId}/comment")
	public List<Comment> getCommentsByPostId(@PathVariable Long postId) {
		return commentService.getCommentsByPostId(postId);
	}
}
