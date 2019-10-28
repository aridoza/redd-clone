package com.ga.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.ga.config.JwtUtil;
import com.ga.dao.CommentDaoImpl;
import com.ga.entity.Comment;
import com.ga.entity.Post;
import com.ga.entity.User;
import com.ga.service.CommentService;
import com.ga.service.CommentServiceImpl;
import com.ga.service.PostServiceImpl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommentControllerTest {
	
	private MockMvc mockMvc;
	
	@Mock
	Map<String, String> headers;
	
	@InjectMocks
	CommentController commentController;
	
	@Mock
	CommentServiceImpl commentService;
	
	@Mock
	PostServiceImpl postService;
	
	@Mock
	CommentDaoImpl commentDao;

	@Mock
	private Post post;
	
	@Mock
	private JwtUtil jwtUtil;
	
	@Mock
	private UserDetails userDetails;
	
	@Mock
	private User user;
	
	@Mock
	private Comment comment;
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(commentController).build();
		headers.put("Authorization","Bearer 123456");
	}
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Before
	public void initializeDummyPost() {
		post.setId(1L);
		post.setTitle("first post");
		post.setDescription("post description");
		
	}
	
	@Before
	public void initializeDummyComment() {
		comment.setId(1L);
		comment.setPost(post);
		comment.setText("some comment");
		comment.setUser(user);
	}
	
	@Before
	public void initializeDummyUser() {
		user.setId(1L);
        user.setUsername("batman");
        user.setPassword("robin");
	}
	
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
		
		when(commentService.listComments()).thenReturn(Arrays.asList(comment));
		
		MvcResult result = mockMvc.perform(get("/comment/list")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(createCommentInJson(comment, firstPost.getId(), "123456"))
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(jsonPath("$", hasSize(1)))
				.andReturn();
		
		verify(commentService, times(1)).listComments();
		verifyNoMoreInteractions(commentService);
			
	}
	
	@Test
	public void createComment_Comment_Success() throws Exception {
		Post firstPost = new Post();
		firstPost.setId(1L);
		firstPost.setTitle("first post");
		firstPost.setDescription("first post description");
		firstPost.setComments(Arrays.asList(comment));
		
		Comment comment = new Comment();
		comment.setId(1L);
		comment.setPost(firstPost);
		comment.setText("heres a comment");
		comment.setUser(user);
		
		String localToken = jwtUtil.generateToken(userDetails);
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		request.addHeader("Authorization","Bearer 123456");
		
		headers.put("Authorization","Bearer 123456");
		
		when(postService.createPost(firstPost, "batman")).thenReturn(firstPost);
		when(postService.listPosts()).thenReturn(Arrays.asList(firstPost));
		when(commentService.createComment(comment, firstPost.getId(), "batman")).thenReturn(comment);
		
		Comment newComment = commentController.createComment(comment, post.getId(), headers);
		
//		MvcResult result = mockMvc.perform(post("/comment/1")
//				.contentType(MediaType.APPLICATION_JSON_UTF8)
//				.content(createCommentInJson(comment, post.getId(), "batman"))
//				.accept(MediaType.APPLICATION_JSON)
//				)
//				.andExpect(status().isOk())
//				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
//				.andExpect(jsonPath("$", hasSize(1)))
//				.andReturn();
//		
//		System.out.println(result);
		
		assertNotNull(newComment);
		assertEquals(newComment.getText(), comment.getText());
		
		verify(commentService, times(1)).createComment(any(), any(), any());
		verifyNoMoreInteractions(commentService);
		
	}
	
	@Test
	public void deleteComment_CommentId_Success() throws Exception {
		Post firstPost = new Post();
		firstPost.setId(1L);
		firstPost.setTitle("first post");
		firstPost.setDescription("first post description");
		firstPost.setUser(user);
		
		Comment comment = new Comment();
		comment.setId(1L);
		comment.setPost(firstPost);
		comment.setText("heres a comment");
		comment.setUser(user);
		
		when(postService.deletePost(any())).thenReturn(firstPost.getId());
		
		MvcResult result = mockMvc.perform(delete("/comment/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(createCommentInJson(comment, post.getId(), "123456"))
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		
		System.out.println(result);
		
		verify(commentService, times(1)).deleteComment(any());
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
