package com.ga.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.ga.entity.Post;
import com.ga.entity.User;
import static org.mockito.Mockito.when;

import org.hibernate.Transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;


public class PostDaoTest {

	@Rule
	public MockitoRule rule = MockitoJUnit.rule();
	
	@InjectMocks
	private Post post;
	
	@InjectMocks
	private User user;
	
	@InjectMocks
	private PostDaoImpl postDao;
	
	@Mock
	private SessionFactory sessionFactory;
	
	@Mock
	Session session;
	
	@Mock
	Transaction transaction;
	
	@Before
	public void init() {
		user.setId(1L);
        user.setUsername("batman");
        user.setPassword("robin");
		
		post.setId(1L);
		post.setTitle("new post");
		post.setDescription("post description");
		
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		when(session.getTransaction()).thenReturn(transaction);
	}
	
//	@Before
//	public void initializeDummyPost() {
//		post.setId(1L);
//		post.setTitle("new post");
//		post.setDescription("post description");
//	}
//	
//	@Before
//	public void initializeDummyUser() {
//		user.setId(1L);
//        user.setUsername("batman");
//        user.setPassword("robin");
//	}
	
	@Test
	public void createPost_Post_Success() {
		Post savedPost = postDao.createPost(post);
		
		assertNotNull("Test returned null object, expected non-null", savedPost);
		assertEquals(savedPost, post);
	}
	
	@Test
	public void deletePost_Post_Success() {
		
	}
}
