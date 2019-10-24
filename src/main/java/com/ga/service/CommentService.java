package com.ga.service;

import java.util.List;

import com.ga.entity.Comment;

public interface CommentService {

	public List<Comment> listComments();
	public List<Comment> listCommentsByUser();
	public Comment createComment(Comment comment, Long postId, String username);
	public Long deleteComment(Long commentId);
}
