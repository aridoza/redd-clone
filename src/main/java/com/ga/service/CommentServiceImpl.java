package com.ga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.ga.dao.CommentDao;
import com.ga.entity.Comment;

public class CommentServiceImpl implements CommentService {
	
	@Autowired
	CommentDao commentDao;

	@Override
	public List<Comment> listComments() {
		return commentDao.listComments();
	}

	
}
