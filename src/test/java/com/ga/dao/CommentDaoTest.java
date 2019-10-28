package com.ga.dao;

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

import com.ga.entity.Comment;
import com.ga.entity.Post;
import com.ga.entity.User;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;

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
	private CommentDaoImpl commentDao;
	
	@Mock
	SessionFactory sessionFactory;
	
	@Mock
	Session session;
	
	@Mock
	Transaction transaction;
	
	@Mock
	Query<Comment> query;
	
	@Before
	public void init() {
		user.setId(1L);
        user.setUsername("batman");
        user.setPassword("robin");
		
		post.setId(1L);
		post.setTitle("new post");
		post.setDescription("post description");
		
		comment.setId(1L);
		comment.setPost(post);
		comment.setText("new comment");
		comment.setUser(user);
		
		
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
	public void listComments_Comment_Success() {
		
		comments.add(comment);
		
		when(session.createQuery(anyString())).thenReturn(query);
		when(query.getResultList()).thenReturn(comments);
		
		List<Comment> localComments = commentDao.listComments();
		
		assertNotNull("Test returned null object, expected non-null", localComments);
		assertEquals(localComments, comments);
	}
	
	@Test
	public void createComment_Comment_Success() {
		Comment savedComment = commentDao.createComment(comment);
		
		assertNotNull("Test returned null object, expected non-null", savedComment);
		assertEquals(savedComment, comment);
	}
	
	@Test
	public void deleteComment_Comment_Success() {	
		Comment comment = new Comment();
		comment.setId(1L);
		comment.setPost(post);
		comment.setText("new comment");
		comment.setUser(user);
		
		when(session.createQuery(anyString())).thenReturn(query);
		when(query.getResultList()).thenReturn(comments);
		when(session.get(Comment.class, 1L)).thenReturn(comment);
		
		Long deletedCommentId = commentDao.deleteComment(comment.getId());
		
		//System.out.println(postDao.deletePost(1L));
		
		assertNotNull("Test returned null object, expected non-null", deletedCommentId);
		assertEquals(deletedCommentId, Long.valueOf(comment.getId()));
	}
	
	@Test
	public void listCommentsByUser_Comments_Success() {
		when(session.createQuery(anyString())).thenReturn(query);
		when(query.getResultList()).thenReturn(comments);
		
		List<Comment> localComments = commentDao.listCommentsByUser(user);
		
		assertNotNull("Test returned null object, expected non-null", localComments);
		assertEquals(localComments, comments);
	}
	
	@Test
	public void getCommentsByPostId_Comments_Success() {
		when(session.createQuery(anyString())).thenReturn(query);
		when(query.getResultList()).thenReturn(comments);
		
		List<Comment> localComments = commentDao.getCommentsByPostId(post.getId());
		
		assertNotNull("Test returned null object, expected non-null", localComments);
		assertEquals(localComments, comments);
	}
}
