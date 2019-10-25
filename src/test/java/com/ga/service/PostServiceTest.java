package com.ga.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ga.config.JwtUtil;
import com.ga.dao.PostDao;
import com.ga.entity.Post;

import junit.framework.Assert;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

public class PostServiceTest {
	
	@Mock
	PostDao postDao;
	
	@Mock
	private JwtUtil jwtUtil;
	
	@InjectMocks
	private PostServiceImpl postService;
	
	@InjectMocks
	private Post post;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Before
	public void initializeDummyPost() {
		post.setId(1L);
		post.setTitle("new dummy post");
		post.setDescription("dummy post description");
	}
	
	@Test
	public void listPosts_Post_Success() {
		Post post = new Post();
		post.setId(1L);
		post.setTitle("new post");
		post.setDescription("post description");
		
		System.out.println(post);
		
		List<Post> posts = postService.listPosts();
		posts.add(post);
		
		System.out.println(posts);
		
		Assert.assertEquals(posts.get(0).getTitle(), post.getTitle())
;	}
}
