package com.ga.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestHeader;

import com.ga.entity.Comment;
import com.ga.entity.JwtResponse;
import com.ga.entity.Post;
import com.ga.entity.User;
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
	
	@InjectMocks
	User user;
	
	@Mock
	List<User> usersList;
	
	@Mock
	Post post;
	
	@Mock
	List<Post> postsList;

	@Mock
	Comment comment;
	
	@Mock
	List<Comment> commentsList;
	
	@Mock
	Map<String, String> headers;
	
	@Before
	public void init() {
        user.setId(1L);
		user.setUsername("testuser");
		user.setPassword("testpass");
		user.setEmail("test@testmail.com");
		
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
	}
	
	@Test
	public void helloWorld_HelloWorld_Success() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders
			.get("/user/hello")
			.accept(MediaType.APPLICATION_JSON);
		
		System.out.println(user);
		System.out.println(usersList.get(0));
		
		mockMvc.perform(requestBuilder)
			.andExpect(status().isOk())
			.andExpect(content().string("Hello World!!"));
	}
	
	@Test
	public void listUsers_UserList_Succes() throws Exception {           
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/list")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJsonAllFields("testuser", "testpass", "test@testmail.com"));
        
        when(userService.listUsers()).thenReturn(Arrays.asList(user));
        
        mockMvc.perform(requestBuilder) 
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$", hasSize(1)));
			
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
		String testToken = "Bearer testToken12345";
		
		headers = new HashMap<>();
		headers.put("authorization", testToken);
		
		System.out.println("headers: " + headers.get("authorization"));
		
		post.setId(1L);
		post.setUser(user);
		post.setTitle("test title");
		post.setDescription("test description");
		
		postsList.add(post);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders
				.get("/user/post")
				.header("Authorization", testToken)
			    .contentType(MediaType.APPLICATION_JSON)
			    .content("    {\n" + 
			    		"        \"user\": {\n" + 
			    		"            \"username\": \"testuser\",\n" + 
			    		"            \"password\": \"testpass\",\n" + 
			    		"            \"email\": \"test@testmail.com\",\n" + 
			    		"            \"userProfile\": null,\n" + 
			    		"            \"id\": 1\n" + 
			    		"        },\n" + 
			    		"        \"title\": \"test title\",\n" + 
			    		"        \"description\": \"test description\",\n" + 
			    		"        \"id\": 1\n" + 
			    		"    }");
		
		when(postService.listPostsByUsername(anyString())).thenReturn(postsList);
		when(userController.getPostsByUsername(headers)).thenReturn(Arrays.asList(post));
		
		MvcResult result = mockMvc.perform(requestBuilder)
				.andExpect(status().isOk())
				.andReturn();
	}
	
	@Test
	public void getCommentsByUsername_Comments_Success() throws Exception {
		String testToken = "Bearer testToken12345";
		
		headers = new HashMap<>();
		headers.put("authorization", testToken);
		
		comment.setId(1L);
		comment.setUser(user);
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