package com.ga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ga.config.JwtUtil;
import com.ga.dao.CommentDao;
import com.ga.dao.PostDao;
import com.ga.dao.UserDao;
import com.ga.entity.Comment;
import com.ga.entity.Post;
import com.ga.entity.User;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	CommentDao commentDao;
	
	@Autowired
	PostDao postDao;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	JwtUtil jwtUtil;

	@Override
	public List<Comment> listComments() {
		return commentDao.listComments();
	}

	@Override
	public Comment createComment(Comment comment, Long postId, String token) {
		String username = jwtUtil.getUsernameFromToken(token);
		User user = userDao.getUserByUsername(username);
		Post post = postDao.getPostByPostId(postId);
		comment.setUser(user);
		comment.setPost(post);
		
		return commentDao.createComment(comment);
	}

	@Override
	public Long deleteComment(Long commentId) {
		return commentDao.deleteComment(commentId);
	}
	
	@Override
	public List<Comment> listCommentsByUsername(String token) {
		String username = jwtUtil.getUsernameFromToken(token);
		User user = userDao.getUserByUsername(username);
		
		return commentDao.listCommentsByUser(user);
	}
	
}
