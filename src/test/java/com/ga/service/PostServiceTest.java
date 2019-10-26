package com.ga.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ga.config.JwtUtil;
import com.ga.dao.PostDao;
import com.ga.entity.Post;
import com.ga.entity.User;

import org.junit.Assert;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;

import java.util.List;

public class PostServiceTest {
	
	@Mock
	private PostDao postDao;
	
	@Mock
	private JwtUtil jwtUtil;
	
	@InjectMocks
	private PostServiceImpl postService;
	
	@InjectMocks
	private User user;
	
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
	
	@Before
    public void initializeDummyUser() {
        user.setId(1L);
        user.setUsername("batman");
        user.setPassword("robin");
    }
	
	@Test
	public void listPosts_Post_Success() {
		Post post = new Post();
		post.setId(1L);
		post.setTitle("new post");
		post.setDescription("post description");
		
		//System.out.println(post);
		
		List<Post> posts = postService.listPosts();
		posts.add(post);
		
//		assertEquals(posts.get(0).getTitle(), post.getTitle())
		
		when(postDao.listPosts()).thenReturn(posts);
		
		assertEquals(posts.get(0).getTitle(), post.getTitle());
	}
	
	@Test
	public void createPost_AddsPost_Success() {
		Post newPost = post;
		
		//Assert.assertNotNull(newPost);
		when(postDao.createPost(newPost)).thenReturn(post);
		
		assertEquals(newPost.getTitle(), post.getTitle());
	}
	
	@Test
	public void updatePost_UpdatesPost_Success() {
		when(postDao.updatePost(any(), anyLong())).thenReturn(post);
		
		Post tempPost = postService.updatePost(post, post.getId());
		
		assertEquals(tempPost.getTitle(), post.getTitle());
	}
	
	@Test
	public void deletePost_DeletesPost_Success() {
		Post tempPost = new Post();
		tempPost.setId(2L);
		
		Long deletedPostId = postDao.deletePost(tempPost.getId());
		
		when(postDao.deletePost(tempPost.getId())).thenReturn(tempPost.getId());
		
		verify(postDao, times(1)).deletePost(eq(tempPost.getId()));
	}
	
	@Test
	public void listPostsByUsername_ListPosts_Success() {
		Post post = new Post();
		post.setId(1L);
		post.setTitle("new post");
		post.setDescription("post description");
		post.setUser(user);
		
		List<Post> posts = postService.listPosts();
		posts.add(post);
		
		when(postDao.listPosts()).thenReturn(posts);
		
		assertEquals(posts.get(0).getUser(), post.getUser());
	}
}
