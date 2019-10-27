package com.ga.dao;

import static org.mockito.Mockito.when;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Before;
import org.junit.Rule;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.ga.entity.Comment;
import com.ga.entity.Post;
import com.ga.entity.User;

public class CommentDaoTest {

	@Rule
	public MockitoRule rule = MockitoJUnit.rule();
	
	@Mock
	private List<Comment> comments;
	
	@InjectMocks
	private Comment comment;
	
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
	
	
}
