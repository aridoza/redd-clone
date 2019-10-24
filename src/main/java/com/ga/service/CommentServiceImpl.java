package com.ga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
	
	private JwtUtil jwtUtil;

	@Override
	public List<Comment> listComments() {
		return commentDao.listComments();
	}

	@Override
	public Comment createComment(Comment comment, Long postId, String username) {
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
	public List<Comment> listCommentsByUser() {
		//UserDetails userDetails = loadUserByUsername(user.getUsername());
		
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String username = ((UserDetails)principal).getUsername();
		
		//String username = "superman";
		
		return commentDao.listCommentsByUser(username);
	}
	
}
