package com.ga.controller;

import org.junit.runner.RunWith;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ga.entity.Post;
import com.ga.service.PostService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

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
	
	@Mock
	private PostService postService;
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(postController).build();
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
	@Test
	public void listPosts_Post_Success() throws Exception {
		Post firstPost = new Post();
				firstPost.setTitle("first post");
				firstPost.setDescription("heres a post description");
				
		System.out.println(firstPost);
		
		when(postService.listPosts()).thenReturn(Arrays.asList(firstPost));
		
		mockMvc.perform(get("/post/posts"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$[0].title", is("first post")));

		
		verify(postService, times(1)).listPosts();
		verifyNoMoreInteractions(postService);
	}
	
	
}
