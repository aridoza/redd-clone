package com.ga.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.Serializable;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ga.entity.User;
import com.ga.entity.UserProfile;

public class UserProfileDaoTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();
    
    @InjectMocks
    private UserProfile userProfile;
    
    @InjectMocks
    private UserProfileDaoImpl userProfileDao;
    
    @Mock
    private SessionFactory sessionFactory;
    
    @Mock
    Session session;
    
    @Mock
    Transaction transaction;
    
    @Mock
    Query<UserProfile> query;
    
    @Mock
    private User user;
    
    @Mock
    private UserDaoImpl userDao;
    
    // https://www.baeldung.com/junit-assert-exception
    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();
	
    @Before
    public void init() {
        user.setId(1L);
        user.setUsername("testuser");
        user.setPassword("testpass");
        user.setEmail("test@testmail.com");
    	
        userProfile.setId(1L);
        userProfile.setAdditionalEmail("test@testuser.com");
        userProfile.setAddress("testpass");
        userProfile.setMobile("test@testmail.com");
        
        user.setUserProfile(userProfile);
        
        when(sessionFactory.getCurrentSession()).thenReturn(session);
        when(session.getTransaction()).thenReturn(transaction);
        when(session.createQuery(anyString())).thenReturn(query);
    }
    
    @Test
    public void createUserProfile_returnUserProfile_Success() {
    	when(userDao.getUserByUsername("testuser")).thenReturn(user);
    	
    	UserProfile savedUserProfile = userProfileDao.createUserProfile("testuser", userProfile);
    	
        assertNotNull("Test returned null object, expected non-null", savedUserProfile);
        assertEquals(userProfile, savedUserProfile);
    }
    
    @Test
    public void getUserProfile_returnUserProfile_Success() {
    	when(userDao.getUserByUsername("testuser")).thenReturn(user);
    	when(query.getSingleResult()).thenReturn(userProfile);
    	
        UserProfile savedUserProfile = userProfileDao.getUserProfile("testuser");
        
        assertNotNull("Test returned null object, expected non-null", savedUserProfile);
        assertEquals(userProfile, savedUserProfile);
    }
    
    // Still not covering exception throwing
    @Test(expected = UsernameNotFoundException.class)
    public void getUserProfile_throwUsernameNotFoundException_Failure() {
    	when(query.getSingleResult()).thenReturn(userProfile);
    	
        UserProfile savedUserProfile = userProfileDao.getUserProfile(null);
        
        assertNull(savedUserProfile);
        exceptionRule.expect(UsernameNotFoundException.class);
        exceptionRule.expectMessage("Username not found");
    }
}
