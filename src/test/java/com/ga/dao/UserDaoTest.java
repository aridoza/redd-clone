package com.ga.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
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
    public void signup_User_Success() {
        User savedUser = userDao.signup(user);
        
        assertNotNull("Test returned null object, expected non-null", savedUser);
        assertEquals(savedUser, user);
    }
}