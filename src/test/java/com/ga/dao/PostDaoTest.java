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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;

import com.ga.entity.Post;
import com.ga.entity.User;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import java.util.List;

import org.hibernate.Transaction;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import org.hibernate.query.Query;


public class PostDaoTest {

	@Rule
	public MockitoRule rule = MockitoJUnit.rule();
	
	
	@Mock
	private List<Post> postList;
	
	@InjectMocks
	private Post post;
	
	@InjectMocks
	private User user;
	
	@InjectMocks
	private PostDaoImpl postDao;
	
	@Mock
	SessionFactory sessionFactory;
	
	@Mock
	Session session;
	
	@Mock
	Transaction transaction;
	
	@Mock
	Query<Post> query;
	
	
//	@Before
//	public void initMocks() {
//		MockitoAnnotations.initMocks(this);
//	}
	
	@Before
	public void init() {
		user.setId(1L);
        user.setUsername("batman");
        user.setPassword("robin");
		
		post.setId(1L);
		post.setTitle("new post");
		post.setDescription("post description");
		
		postList.add(post);
		
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		when(session.getTransaction()).thenReturn(transaction);
		when(session.get(Post.class, 1L)).thenReturn(post);
	}
	
	@Before
	public void initializeDummyPost() {
		post.setId(1L);
		post.setTitle("new post");
		post.setDescription("post description");
	}
	
	@Before
	public void initializeDummyUser() {
		user.setId(1L);
        user.setUsername("batman");
        user.setPassword("robin");
	}
	

	@Test
	public void listPosts_Post_Success() {
		
		when(session.createQuery(anyString())).thenReturn(query);
		when(query.getResultList()).thenReturn(postList);
		
		List<Post> localPosts = postDao.listPosts();
		
		assertNotNull("Test returned null object, expected non-null", localPosts);
		assertEquals(localPosts, postList);
	}
	
	@Test
	public void createPost_Post_Success() {
		Post savedPost = postDao.createPost(post);
		
		assertNotNull("Test returned null object, expected non-null", savedPost);
		assertEquals(savedPost, post);
	}
	
	@Test
	public void deletePost_Post_Success() {		
		Long deletedPostId = postDao.deletePost(post.getId());
		
		//System.out.println(postDao.deletePost(1L));
		
		assertNotNull("Test returned null object, expected non-null", deletedPostId);
		assertEquals(deletedPostId, Long.valueOf(post.getId()));
	}
	
	@Test
	public void updatePost_Post_Success() {
		post.setDescription("updated description");
		
		Post savedPost = postDao.updatePost(post, post.getId());
		
		assertNotNull("Test returned null object, expected non-null", savedPost);
		assertEquals(post.getDescription(), "updated description");
	}
	
	@Test
	public void getPostByPostId_Post_Success() {
		when(session.createQuery(anyString())).thenReturn(query);
		when(query.uniqueResult()).thenReturn(post);
		
		Post localPost = postDao.getPostByPostId(post.getId());
		
		assertNotNull("Test returned null object, expected non-null", localPost);
		assertEquals(localPost, post);
	}
	
	@Test
	public void listPostsByUsername_Post_Success() {
		when(session.createQuery(anyString())).thenReturn(query);
		when(query.getResultList()).thenReturn(postList);
		
		List<Post> localPost = postDao.listPostsByUsername(user);
		
		assertNotNull("Test returned null object, expected non-null", localPost);
		assertEquals(localPost, postList);
	}
}
