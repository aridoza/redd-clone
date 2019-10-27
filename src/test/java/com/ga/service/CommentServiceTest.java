package com.ga.service;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import com.ga.config.JwtUtil;
import com.ga.dao.CommentDao;
import com.ga.dao.PostDao;
import com.ga.dao.PostDaoImpl;
import com.ga.dao.UserDao;
import com.ga.dao.UserDaoImpl;
import com.ga.entity.Comment;
import com.ga.entity.Post;
import com.ga.entity.User;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.Assert;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.eq;

import java.util.Arrays;
import java.util.List;

public class CommentServiceTest {
	
	@Rule
	public MockitoRule rule = MockitoJUnit.rule();

	@Mock
	private CommentDao commentDao;
	
	@Mock
	private JwtUtil jwtUtil;
	
	@InjectMocks
	private CommentServiceImpl commentService;
	
	@InjectMocks
	private User user;
	
	@InjectMocks
	private Post post;
	
	@InjectMocks
	private Comment comment;
	
	@Mock
	private UserDaoImpl userDao;
	
	@Mock
	private PostDaoImpl postDao;
	
	@Mock
	SessionFactory sessionFactory;
	
	@Mock
	Session session;
	
	@Mock
	Transaction transaction;
	
	@Before
	public void initMocks() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Before
	public void init() {
		when(sessionFactory.getCurrentSession()).thenReturn(session);
		when(session.getTransaction()).thenReturn(transaction);
		when(session.get(User.class, 1L)).thenReturn(user);
	}
	
	@Before
	public void initializeDummyPost() {
		post.setId(1L);
		post.setTitle("new dummy post");
		post.setDescription("dummy post description");
	}
	
	@Before
    public void initializeDummyUser() {
        user.setId(1L);
        user.setUsername("batman");
        user.setPassword("robin");
    }
	
	@Before
	public void initializeDummyComment() {
		comment.setId(1L);
		comment.setPost(post);
		comment.setText("heres a comment");
		comment.setUser(user);
	}
	
	@Test
	public void listComments_Comment_Success() {
		User user = new User();
		user.setId(1L);
		user.setUsername("batman");
		user.setPassword("robin");
		
		
		Post firstPost = new Post();
		firstPost.setTitle("first post");
		firstPost.setDescription("first post description");
		firstPost.setComments(Arrays.asList(comment));
		
		Comment comment = new Comment();
		comment.setId(1L);
		comment.setPost(firstPost);
		comment.setText("heres a comment");
		comment.setUser(user);
		
		List<Comment> comments = commentService.listComments();
		comments.add(comment);
		
		when(commentDao.listComments()).thenReturn(comments);
		
		assertEquals(comments.get(0).getText(), comment.getText());
	}
	
	@Test
	public void createComment_AddsComment_Success() {
		Comment newComment = new Comment();
		newComment.setId(1L);
		newComment.setPost(post);
		newComment.setText("heres a comment");
		newComment.setUser(user);
		
		User newUser = new User();
		newUser.setId(1L);
		newUser.setUsername("batman");
		newUser.setPassword("robin");
		
		when(userDao.getUserByUsername(newUser.getUsername())).thenReturn(newUser);
		when(commentDao.createComment(newComment)).thenReturn(newComment);
		
		Comment tempComment = commentService.createComment(newComment, post.getId(), "123456");
		
		assertNotNull("Test returned null object, expected non-null", newComment);
		assertEquals(tempComment.getText(), newComment.getText());
	}
	
	@Test
	public void deleteComment_DeletesComment_Success() {
		Comment newComment = new Comment();
		newComment.setId(1L);
		newComment.setPost(post);
		newComment.setText("heres a comment");
		newComment.setUser(user);
		
		Long deletedCommenId = commentDao.deleteComment(newComment.getId());
		
		when(commentService.deleteComment(newComment.getId())).thenReturn(newComment.getId());
		
		verify(commentDao, times(1)).deleteComment(eq(newComment.getId()));
	}
	
	@Test
	public void getCommentsByPostId_Comments_Success() {
		Comment newComment = new Comment();
		newComment.setId(1L);
		newComment.setPost(post);
		newComment.setText("heres a comment");
		newComment.setUser(user);
		
		List<Comment> comments = commentService.getCommentsByPostId(post.getId());
		comments.add(newComment);
		
		when(commentDao.listComments()).thenReturn(comments);
		when(commentService.getCommentsByPostId(post.getId())).thenReturn(Arrays.asList(newComment));
		
		assertEquals(comments.get(0).getPost().getTitle(), post.getTitle());
	}
	
	@Test
	public void listCommentsByUsername_Comments_Success() {
		Comment newComment = new Comment();
		newComment.setId(1L);
		newComment.setPost(post);
		newComment.setText("heres a comment");
		newComment.setUser(user);
		
		User newUser = new User();
		newUser.setId(1L);
		newUser.setUsername("batman");
		newUser.setPassword("robin");
		
		List<Comment> comments = commentService.listCommentsByUsername("123456");
		comments.add(newComment);
		
		when(userDao.getUserByUsername(newUser.getUsername())).thenReturn(newUser);
		when(commentDao.listComments()).thenReturn(comments);
		when(commentService.listCommentsByUsername("123456")).thenReturn(comments);
		
		assertEquals(comments.get(0).getPost().getTitle(), post.getTitle());
	}
}
