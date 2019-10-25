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
    
    @Before
    public void init() {
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("testpass");
        user.setEmail("test@testmail.com");
    
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.getTransaction()).thenReturn(transaction);
    }
    
    @Test
    public void listUsers_Users_Success() {
    	userDao.signup(user);
    	
    	List<User> savedUsers = userDao.listUsers();
    	
    	System.out.println(savedUsers.get(0).toString());
    	
        assertNotNull("Test returned null object, expected non-null", savedUsers);
    }
    
    @Test
    public void signup_User_Success() {
        User savedUser = userDao.signup(user);
        
        assertNotNull("Test returned null object, expected non-null", savedUser);
        assertEquals(savedUser, user);
    }
    
    @Test
    public void login_User_Success() {
    	when(session.createQuery(anyString())).thenReturn(query);
    	when(query.getSingleResult()).thenReturn(user);
    	
        User savedUser = userDao.login(user);
        
        assertNotNull("Test returned null object, expected non-null", savedUser);
        assertEquals(savedUser, user);
    }
    
    @Test
    public void delete_UserId_Success() {
    	User savedUser = userDao.signup(user);
    	System.out.println(savedUser);
    	userDao.deleteUser(savedUser.getId());
    	
    	assertNotNull(savedUser);
    }
}