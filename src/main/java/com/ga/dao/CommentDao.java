package com.ga.dao;

import java.util.List;

import com.ga.entity.Comment;
import com.ga.entity.User;

public interface CommentDao {
	
	public List<Comment> listComments();
	public Comment createComment(Comment comment);
	public Long deleteComment(Long commentId);
	List<Comment> listCommentsByUser(User user);
	public List<Comment> getCommentsByPostId(Long postId);
	
}
