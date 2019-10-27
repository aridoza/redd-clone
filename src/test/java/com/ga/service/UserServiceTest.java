package com.ga.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.ga.config.JwtUtil;
import com.ga.dao.UserDao;
import com.ga.entity.JwtResponse;
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
    
    @Mock
    private List<GrantedAuthority> authorities;
    
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
        
        JwtResponse actualToken = userService.signup(user);
        
        assertEquals(actualToken.getToken(), expectedToken);
    }
    
    @Test
    public void signup_UserNotFound_Error() {
        User tempUser = user;
        tempUser.setId(null);

        when(userDao.signup(any())).thenReturn(tempUser);

        JwtResponse token = userService.signup(user);

        assertEquals(token, null);
    }
    
	@Test // TODO: Fix returns for token return refactor
    public void login_ReturnsJwt_Success() {
        String expectedToken = "fakeToken12345";
        
        when(userDao.login(any())).thenReturn(user);
        when(bCryptPasswordEncoder.matches(any(), any())).thenReturn(true);
        when(userDao.getUserByUsername(anyString())).thenReturn(user);
        when(jwtUtil.generateToken(any())).thenReturn(expectedToken);
        when(bCryptPasswordEncoder.encode(user.getPassword())).thenReturn("testpass");
        
        JwtResponse actualToken = userService.login(user);
        
        assertEquals(actualToken.getToken(), expectedToken);
    }
    
    @Test
    public void login_UserNotFound_Error() {    	
    	User tempUser = user;
    	tempUser.setId(null);

        when(userDao.login(any())).thenReturn(null);

        JwtResponse token = userService.login(user);

        assertEquals(token, null);
    }
    
    @Test
    public void delete_UserId_Success() {
    	Long expectedUserId = 1L;
    	
    	when(userDao.deleteUser(anyLong())).thenReturn(1L);
    	
    	Long actualUserId = userService.deleteUser(1L);
    	
    	assertEquals(expectedUserId, actualUserId);
    }

    @Test // TODO: Finish
    public void loadUserByUsername_UserDetails_Success() {
    	User tempUser = user;
    	tempUser.setUsername("testuser");
    	
    	when(userDao.getUserByUsername(anyString())).thenReturn(user);
    	
    	UserDetails loadedUser = userService.loadUserByUsername("testuser");
    	
    	assertEquals(tempUser.getUsername(), loadedUser.getUsername());
    }
    
    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByUsername_ExpectException_Error() {
    	User tempUser = user;
    	tempUser.setId(null);
    	
    	when(userService.loadUserByUsername(tempUser.getUsername()))
    		.thenThrow(UsernameNotFoundException.class);
    }
    
    @Test // TODO: Finish
    public void loadUserByEmail_UserDetails_Success() {
    	User tempUser = user;
    	tempUser.setUsername("testuser");
    	
    	when(userDao.getUserByEmail(anyString())).thenReturn(user);
    	
    	UserDetails loadedUser = userService.loadUserByEmail("test@testmail.com");
    	
    	assertEquals(tempUser.getUsername(), loadedUser.getUsername());
    }
    
    @Test(expected = UsernameNotFoundException.class)
    public void loadUserByEmail_ExpectException_Error() {
    	User tempUser = user;
    	tempUser.setId(null);
    	
    	when(userService.loadUserByEmail(tempUser.getEmail()))
    		.thenThrow(UsernameNotFoundException.class);
    }
}