package com.ga.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ga.entity.Comment;

@Repository
public class CommentDaoImpl implements CommentDao {
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<Comment> listComments() {
		List<Comment> allComments = null;
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			allComments = session.createQuery("FROM Comment").getResultList();
		} finally {
			session.close();
		}
		return allComments;
	}

	@Override
	public Comment createComment(Comment comment) {
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			session.save(comment);
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return comment;
	}

}
