package com.ga.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.springframework.test.web.servlet.MockMvc;


import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.ga.config.JwtUtil;
import com.ga.entity.Comment;
import com.ga.entity.Post;
import com.ga.entity.User;
import com.ga.service.CommentService;

import static org.mockito.Mockito.*;

public class CommentControllerTest {
	
	private MockMvc mockMvc;
	
	@InjectMocks
	CommentController commentController;
	
	@Mock
	CommentService commentService;

	@Mock
	private Post post;
	
	@Mock
	private JwtUtil jwtUtil;
	
	@Mock
	private User user;
	
	@Mock
	private Comment comment;
	
//	@Before
//	public void init() {
//		mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
//	}
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
//	@Before
//	public void initializeDummyPost() {
//		post.setId(1L);
//		post.setTitle("first post");
//		post.setDescription("post description");
//		
//	}
//	
//	@Before
//	public void initializeDummyComment() {
//		comment.setId(1L);
//		comment.setPost(post);
//		comment.setText("some comment");
//		comment.setUser(user);
//	}
	
//	@Before
//	public void initializeDummyUser() {
//		user.setId(1L);
//        user.setUsername("batman");
//        user.setPassword("robin");
//	}
	
	@Test
	public void getComments_Comment_Success() throws Exception {
		
		User user = new User();
		user.setId(1L);
		user.setUsername("batman");
		user.setPassword("robin");
		
		
		Post firstPost = new Post();
		firstPost.setTitle("first post");
		firstPost.setDescription("first post description");
		firstPost.setComments(Arrays.asList(comment));
		
		Comment comment = new Comment();
		comment.setId(1L);
		comment.setPost(firstPost);
		comment.setText("heres a comment");
		comment.setUser(user);
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/comment/list")
				.contentType(MediaType.APPLICATION_JSON)
				.content(createCommentInJson(comment, firstPost.getId(), "123456"));
		
		//when(commentService.createComment(comment, firstPost.getId(), user.getUsername())).thenReturn(comment);
		//when(commentService.listComments()).thenReturn(any());
		
		mockMvc.perform(requestBuilder)	
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$", hasSize(2)));
		
		verify(commentService, times(1)).listComments();
		verifyNoMoreInteractions(commentService);
			
	}
	
	private static String createPostInJson(String title, String description) {
		return "{ \"title\": \"" + title + "\", " +
				"\"description\":\"" + description + "\"}";
	}
	
	private static String createCommentInJson(Comment comment, Long postId, String token) {
		return "{ \"comment\": \"" + comment + "\", " +
				"\"postId\":\"" + postId + "\", " + 
				"\"token\":\"" + token + "\"}";
	}

}
