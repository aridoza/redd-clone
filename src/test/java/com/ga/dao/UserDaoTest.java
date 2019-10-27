package com.ga.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.ga.entity.User;

public class UserDaoTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    
    @InjectMocks
    private User user;
    
    @InjectMocks
    private UserDaoImpl userDao;
    
    @Mock
    private SessionFactory sessionFactory;
    
    @Mock
    Session session;
    
    @Mock
    Transaction transaction;
    
    @Mock
    Query<User> query;
    
    @Mock
    private List<User> userList;
    
    @Before
    public void init() {
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("testpass");
        user.setEmail("test@testmail.com");
    
        userList.add(user);
        
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.getTransaction()).thenReturn(transaction);
        when(session.createQuery(anyString())).thenReturn(query);
    }
    
    @Test
    public void listUsers_Users_Success() {
    	// mock below
    	// when( function ( any string ).then or response ( List<anything you want> )
    	when(query.getResultList()).thenReturn(userList);
    	
    	List<User> savedUsers = userDao.listUsers();
    	    	
        assertNotNull("Test returned null object, expected non-null", savedUsers);
        assertEquals(userList, savedUsers);
    }
    
    @Test
    public void signup_User_Success() {
        User savedUser = userDao.signup(user);
        
        assertNotNull("Test returned null object, expected non-null", savedUser);
        assertEquals(user, savedUser);
    }
    
    @Test
    public void login_User_Success() {
    	when(query.getSingleResult()).thenReturn(user);
    	
        User savedUser = userDao.login(user);
        
        assertNotNull("Test returned null object, expected non-null", savedUser);
        assertEquals(user, savedUser);
    }
    
    @Test
    public void delete_UserId_Success() {
    	when(query.getSingleResult()).thenReturn(user);
    	
    	System.out.println(user.getId());
    	
    	Long deletedUserId = userDao.deleteUser(user.getId());
//    	Long deletedUserId = 1L;
    	System.out.println(deletedUserId);
    	assertEquals(user.getId(), deletedUserId);
    }
    
    @Test
    public void getUserByUsername_User_Success() {
    	when(query.getSingleResult()).thenReturn(user);
    	
    	User returnedUser = userDao.getUserByUsername(user.getUsername());
    	
    	assertEquals(user.getUsername(), returnedUser.getUsername());
    }
    
    @Test
    public void getUserByEmail_User_Success() {
    	when(query.getSingleResult()).thenReturn(user);
    	
    	User returnedUser = userDao.getUserByEmail(user.getEmail());
    	
    	assertEquals(user.getEmail(), returnedUser.getEmail());
    }
    
    @Test
    public void getUserByUserId_User_Success() {
    	when(query.getSingleResult()).thenReturn(user);
    	
    	User returnedUser = userDao.getUserByUserId(user.getId());
    	
    	assertEquals(user.getId(), returnedUser.getId());
    }
    
    
}