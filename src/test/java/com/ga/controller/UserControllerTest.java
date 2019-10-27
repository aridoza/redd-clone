package com.ga.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ga.entity.Comment;
import com.ga.entity.JwtResponse;
import com.ga.entity.Post;
import com.ga.service.CommentService;
import com.ga.service.PostService;
import com.ga.service.UserService;

public class UserControllerTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    
	
	private MockMvc mockMvc;

	@InjectMocks
	private UserController userController;
	
	@Mock
	UserService userService;
	
	@Mock
	PostService postService;
	
	@Mock
	CommentService commentService;
	
	@Mock
	User user;
	
	@Mock
	Post post;
	
	@Mock
	List<Post> postsList;

	@Mock
	Comment comment;
	
	@Mock
	List<Comment> commentsList;
	
	@Before
	public void init() {
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	
	@Test
	public void helloWorld_HelloWorld_Success() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
			.get("/user/hello")
			.accept(MediaType.APPLICATION_JSON);
		
		mockMvc.perform(requestBuilder)
			.andExpect(status().isOk())
			.andExpect(content().string("Hello World!!"));
	}
	
	@Test
	public void signup_User_Success() throws Exception {
		JwtResponse jwtResponse = new JwtResponse("testToken123456", "testuser");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
			.post("/user/signup")
			.contentType(MediaType.APPLICATION_JSON)
			.content(createUserInJsonAllFields("testuser", "testpass", "test@testmail.com"));
		
		when(userService.signup(any())).thenReturn(jwtResponse);
		
		MvcResult result = mockMvc.perform(requestBuilder)
			.andExpect(status().isOk())
			.andExpect(content().json("{\"token\":\"testToken123456\", \"username\":\"testuser\"}"))
			.andReturn();
	      
//	    System.out.println(">>>>>>>>> signup User Success result: " + result.getResponse().getContentAsString());
	}
	
	@Test
	public void login_User_Success() throws Exception {
		JwtResponse jwtResponse = new JwtResponse("testToken123456", "testuser");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
			       .post("/user/login")
			       .contentType(MediaType.APPLICATION_JSON)
			       .content(createUserInJsonUsingEmail("test@testmail.com", "testpass"));
		
		when(userService.login(any())).thenReturn(jwtResponse);
		
		MvcResult result = mockMvc.perform(requestBuilder)
			.andExpect(status().isOk())
			.andExpect(content().json("{\"token\":\"testToken123456\",\"username\":\"testuser\"}"))
			.andReturn();
		
//		System.out.println(">>>>>>>>> signup User Success result: " + result.getResponse().getContentAsString());
	}
	
	@Test
	public void delete_User_Success() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/user/1");
		
		when(userService
				.deleteUser(anyLong()))
				.thenReturn(1L);

		MvcResult result = mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().string("1"))
				.andReturn();
		
//		System.out.println(">>>>>>>>> delete User Success result: " + result.getResponse().getContentAsString());
	}
	
	@Test
	public void getPostsByUsername_Posts_Success() throws Exception {
		String testToken = "testToken12345";
		
		post.setId(1L);
		post.setTitle("test title");
		post.setDescription("test description");
		
		postsList.add(post);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/user/post");
		
		when(postService
				.listPostsByUsername(testToken))
				.thenReturn(postsList);
		
		MvcResult result = mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().json("{\"id\": 1,\"title\":\"test title\",\"description\":\"test description\"}"))
				.andReturn();
	}
	
	@Test
	public void getCommentsByUsername_Comments_Success() throws Exception {
		String testToken = "testToken12345";
		
		comment.setId(1L);
		comment.setText("test comment");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/user/comment");
		
		when(commentService
				.listCommentsByUsername(testToken));
		
		MvcResult result = mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andExpect(content().json("{\"id\": 1,\"text\":\"test comment\"}"))
				.andReturn();
		
	}
	
	private static String createUserInJson(String username, String password) {
        return "{ \"username\": \"" + username + "\", " +
                "\"password\":\"" + password + "\"}";
	}
	
	private static String createUserInJsonUsingEmail(String email, String password) {
        return "{ \"email\": \"" + email + "\", " +
                "\"password\":\"" + password + "\"}";
	}
	
	private static String createUserInJsonAllFields(String username, String password, String email) {
        return "{\"username\": \"" + username + "\", " +
                "\"password\": \"" + password + "\", " +
                "\"email\": \"" + email + "\"}";
    }

}