package com.ga.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ga.config.JwtUtil;
import com.ga.dao.UserDao;
import com.ga.entity.User;

public class UserServiceTest {

    @Mock
    UserDao userDao;
    
    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder bCryptPasswordEncoder;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    @InjectMocks
    private User user;
    
    @Before
    public void initMocks() {
      MockitoAnnotations.initMocks(this);
    }
    
    @Before
    public void initializeDummyUser() {
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("testpass");
        user.setEmail("test@testmail.com");
    }
        
    @Test
    public void signup_ReturnsJwt_Success() {
    	String expectedToken = "fakeToken12345";
        
        when(userDao.signup(any())).thenReturn(user);
        when(userDao.getUserByUsername(anyString())).thenReturn(user);
        when(jwtUtil.generateToken(any())).thenReturn(expectedToken);
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("testpass");
        
        String actualToken = userService.signup(user);
        
        assertEquals(actualToken, expectedToken);
    }
    
    @Test
    public void signup_UserNotFound_Error() {
        User tempUser = user;
        tempUser.setId(null);

        when(userDao.signup(any())).thenReturn(tempUser);

        String token = userService.signup(user);

        assertEquals(token, null);
    }
    
	@Test
    public void login_ReturnsJwt_Success() {
        String expectedToken = "fakeToken12345";
        
        when(userDao.login(any())).thenReturn(user);
        when(bCryptPasswordEncoder.matches(any(), any())).thenReturn(true);
        when(userDao.getUserByUsername(anyString())).thenReturn(user);
        when(jwtUtil.generateToken(any())).thenReturn(expectedToken);
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("testpass");
        
        String actualToken = userService.login(user);
        
        assertEquals(actualToken, expectedToken);
    }
    
    @Test
    public void login_UserNotFound_Error() {
    	
    	User tempUser = user;
    	tempUser.setId(null);

        when(userDao.login(any())).thenReturn(null);

        String token = userService.login(user);

        assertEquals(token, null);
    }

}