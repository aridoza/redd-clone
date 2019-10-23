package com.ga.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ga.entity.Comment;

public class CommentDaoImpl implements CommentDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Comment> listComments() {
		List<Comment> allComments = null;
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			allComments = session.createQuery("FROM Comments").getResultList();
		} finally {
			session.close();
		}
		return allComments;
	}

}
