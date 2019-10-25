package com.ga.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ga.service.UserService;


@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

	private MockMvc mockMvc;

	@InjectMocks
	UserController userController;
	
	@Mock
	UserService userService;

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
		RequestBuilder requestBuilder = MockMvcRequestBuilders
			.post("/user/signup")
			.contentType(MediaType.APPLICATION_JSON)
			.content(createUserInJsonWithEmail("testuser", "testpass", "test@testmail.com"));
		
		when(userService.signup(any())).thenReturn("testToken123456", "testuser");

		
		MvcResult result = mockMvc.perform(requestBuilder)
			.andExpect(status().isOk())
			.andExpect(content().json("{\"token\":\"testToken123456\", \"username\":\"testuser\"}"))
			.andReturn();
	      
	    System.out.println(">>>>>>>>> signup User Success result: " + result.getResponse().getContentAsString());
	}
	
	@Test
	public void login_User_Success() throws Exception{
		RequestBuilder requestBuilder = MockMvcRequestBuilders
			       .post("/user/login")
			       .contentType(MediaType.APPLICATION_JSON)
			       .content(createUserInJsonUsingEmail("test@testmail.com", "testpass"));
		
		when(userService.login(any())).thenReturn("testToken123456");
		
		MvcResult result = mockMvc.perform(requestBuilder)
          .andExpect(status().isOk())
          .andExpect(content().json("{\"token\":\"testToken123456\", \"username\":\"testuser\"}"))
          .andReturn();
		
		System.out.println(">>>>>>>>> signup User Success result: " + result.getResponse().getContentAsString());
	}
	
	private static String createUserInJson(String username, String password) {
        return "{ \"username\": \"" + username + "\", " +
                "\"password\":\"" + password + "\"}";
	}
	
	private static String createUserInJsonUsingEmail(String email, String password) {
        return "{ \"email\": \"" + email + "\", " +
                "\"password\":\"" + password + "\"}";
	}
	
	private static String createUserInJsonWithEmail(String username, String password, String email) {
        return "{\"username\": \"" + username + "\", " +
                "\"password\": \"" + password + "\", " +
                "\"email\": \"" + email + "\"}";
    }

}