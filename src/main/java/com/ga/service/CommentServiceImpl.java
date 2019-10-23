package com.ga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ga.dao.CommentDao;
import com.ga.dao.PostDao;
import com.ga.entity.Comment;
import com.ga.entity.Post;

@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	CommentDao commentDao;
	
	@Autowired
	PostDao postDao;

	@Override
	public List<Comment> listComments() {
		return commentDao.listComments();
	}

	@Override
	public Comment createComment(Comment comment, Long postId) {
		Post post = postDao.getPostByPostId(postId);
		comment.setPost(post);
		
		return commentDao.createComment(comment);
	}

	@Override
	public Long deleteComment(Long commentId) {
		return commentDao.deleteComment(commentId);
	}
	
}
