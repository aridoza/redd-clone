package com.ga.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ga.entity.Post;
import com.ga.entity.User;
import com.ga.service.PostService;

@RestController
@RequestMapping("/post")
public class PostController {
	
	private List<Post> posts;
	private PostService postService;
	
	@PostConstruct
	public void initializePosts() {
		posts = new ArrayList<Post>();
		
		posts.add(new Post("hello", "this is the first post"));
		posts.add(new Post("good day", "this is the second post"));
		
	}

	@GetMapping("/posts")
	public List<Post> getPosts(){
		return posts;
	}
	
	// update to use post Id after adding auto-generating ID
	@GetMapping("/posts/{title}")
	public Post getPost(@PathVariable String title) {	
		Post foundPost = posts.stream().filter(post -> 
			post.getTitle().equalsIgnoreCase(title)).findFirst().orElse(null);
		
		return foundPost;
	}
	
	@PutMapping("/{postId}")
	public Post updatePost(@RequestBody Post post, @PathVariable Long postId) {
		return postService.updatePost(post, postId);
	}
}
