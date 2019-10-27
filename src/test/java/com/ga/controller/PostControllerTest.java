package com.ga.controller;

import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.PostMapping;

import com.ga.dao.CommentDao;
import com.ga.entity.Comment;
import com.ga.entity.Post;
import com.ga.entity.User;
import com.ga.service.CommentService;
import com.ga.service.PostService;
import com.ga.service.PostServiceImpl;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.jayway.jsonpath.InvalidPathException;
import org.springframework.test.util.JsonPathExpectationsHelper;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(MockitoJUnitRunner.class)
public class PostControllerTest {

	private MockMvc mockMvc;
	
	@InjectMocks
	PostController postController;
	
	@InjectMocks
	private Post post;
	
	@InjectMocks
	private User user;
	
	@InjectMocks
	private Comment comment;
	
	@Mock
	PostService postService;
	
	@Mock
	CommentService commentService;
	
	@Mock
	CommentDao commentDao;
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
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
	public void helloWorld_HelloWorld_Success() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/post/hello")
				.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder)
		.andExpect(status().isOk())
		.andExpect(content().string("Hello World!!"));
	}
/*	
//	@Test
//	public void listPosts_Post_Success() throws Exception {
//		RequestBuilder requestBuilder = MockMvcRequestBuilders
//				.get("/post/posts")
//				.contentType(MediaType.APPLICATION_JSON);
//				
//		mockMvc.perform(requestBuilder)
//		.andExpect(status().isOk())
//		.andExpect(jsonPath("$[0].title", is("new post")))
//		.andReturn();
//	}
	
//	.andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
//	.andExpect(jsonPath("$", hasSize(1)))
//	.andExpect(jsonPath("$[0].id", is(1)))
//	.andExpect(jsonPath("$[0].title", is("first post")))
//	.andExpect(jsonPath("$[0].description", is("heres a post description")));
	
//	.andExpect(content().json("{\"title\":\"first post\"}"));
 
	
//	System.out.println(firstPost);
//	
//	when(postService.listPosts()).thenReturn(Arrays.asList(firstPost));
//	Post firstPost = new Post();
//	firstPost.setTitle("first post");
//	firstPost.setDescription("heres a post description");
	
//	.andExpect(jsonPath("$.title", hasItem("new post")))
//	.andExpect(jsonPath("$.description", hasItem("another comment")));
//	@Test
//	public void listPosts_Post_Success() throws Exception {
//		
//		//List<Post> allPosts;
//		
//		RequestBuilder requestBuilder = MockMvcRequestBuilders
//				.get("/post/posts")
//				.accept(MediaType.APPLICATION_JSON);
//		
//				
//		
//		mockMvc.perform(get("/post/posts").accept(MediaType.APPLICATION_JSON_UTF8))
//			.andExpect(status().isOk())
//			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
//			.andExpect(content().json("{\"$[0].title\":\"first post\"}"));
//		
//		verify(postService, times(1)).listPosts();
//		verifyNoMoreInteractions(postService);
//	}
//	.andExpect(content().json("{\"title\":\"first post\"}"));
	
*/	
	
	
	@Test
	public void getPosts_Post_Success() throws Exception {	
		
		Post firstPost = new Post();
		firstPost.setTitle("first post");
		firstPost.setDescription("first post description");
		
		Post secondPost = new Post();
		secondPost.setTitle("second post");
		secondPost.setDescription("second post description");
		
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/post/posts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(createPostInJson("first post", "heres a post description"));
		
		when(postService.listPosts()).thenReturn(Arrays.asList(firstPost, secondPost));
		
		mockMvc.perform(requestBuilder)	
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
			.andExpect(jsonPath("$", hasSize(2)));
		
		verify(postService, times(1)).listPosts();
		verifyNoMoreInteractions(postService);
			
	}
	
	@Test
	public void updatePost_Post_Success() throws Exception {
		Post firstPost = new Post();
		firstPost.setId(1L);
		firstPost.setTitle("first post");
		firstPost.setDescription("first post description");
		firstPost.setUser(user);
		
		when(postService.updatePost(any(), eq(1L))).thenReturn(firstPost);
		
		MvcResult result = mockMvc.perform(put("/post/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(createPostInJson("first post", "heres an updated post description"))
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		
		System.out.println(result);
		
		verify(postService, times(1)).updatePost(any(), eq(1L));
		verifyNoMoreInteractions(postService);
	}
	
	//@PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@Test
	public void createPost_Post_Success() throws Exception {
		Post firstPost = new Post();
		firstPost.setId(1L);
		firstPost.setTitle("first post");
		firstPost.setDescription("first post description");
		firstPost.setUser(user);
		
		when(postService.createPost(any(), eq("batman"))).thenReturn(firstPost);
		
		MvcResult result = mockMvc.perform(post("/post/batman")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(createPostInJson("first post", "heres a post description"))
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		
		System.out.println(result);
		
		verify(postService, times(1)).createPost(any(), eq("batman"));
		verifyNoMoreInteractions(postService);
		
	}
	
	@Test
	public void deletePost_PostId_Success() throws Exception {
		Post firstPost = new Post();
		firstPost.setId(1L);
		firstPost.setTitle("first post");
		firstPost.setDescription("first post description");
		firstPost.setUser(user);
		
		when(postService.deletePost(any())).thenReturn(firstPost.getId());
		
		MvcResult result = mockMvc.perform(delete("/post/1")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(createPostInJson("first post", "heres a post description"))
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		
		System.out.println(result);
		
		verify(postService, times(1)).deletePost(any());
		verifyNoMoreInteractions(postService);
	}
	
	@Test
	public void getCommentsByPostId_Comments_Success() throws Exception {
		
		Post firstPost = new Post();
		firstPost.setId(1L);
		firstPost.setTitle("first post");
		firstPost.setDescription("first post description");
		firstPost.setUser(user);
		
		Comment comment = new Comment();
		comment.setId(1L);
		comment.setPost(firstPost);
		comment.setText("some comment");
		comment.setUser(user);
		
		firstPost.setComments(Arrays.asList(comment));
		
		//when(postService.createPost(any(), eq("batman"))).thenReturn(firstPost);
		//when(commentService.createComment(any(), any(), eq("batman"))).thenReturn(comment);
		//when(postService.listPosts()).thenReturn(Arrays.asList(firstPost));
		when(commentService.getCommentsByPostId(any())).thenReturn(Arrays.asList(comment));
		
		MvcResult result = mockMvc.perform(get("/post/1/comment")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.content(createCommentInJson(comment, firstPost.getId(), "123456"))
				.accept(MediaType.APPLICATION_JSON)
				)
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
				.andReturn();
		
		System.out.println(result);
		
		verify(commentService, times(1)).getCommentsByPostId(any());
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
