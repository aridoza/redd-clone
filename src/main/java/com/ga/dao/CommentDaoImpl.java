package com.ga.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ga.entity.Comment;
import com.ga.entity.User;

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
	public List<Comment> listCommentsByUser(User user) {
		List<Comment> userComments = null;
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			userComments = session.createQuery("FROM Comment WHERE user_id = '" + user.getId() + "'").getResultList();
		} finally {
			session.close();
		}
		return userComments;
	}

	@Override
	public List<Comment> getCommentsByPostId(Long postId) {
		List<Comment> postComments = null;
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();

			postComments = session.createQuery("FROM Comment WHERE post_id = '" + postId + "'").getResultList();
		} finally {
			session.close();
		}
		
		return postComments;
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

	@Override
	public Long deleteComment(Long commentId) {
		Session session = sessionFactory.getCurrentSession();
		
		Comment savedComment = null;
		
		try {
			session.beginTransaction();
			savedComment = session.get(Comment.class, commentId);
			session.delete(savedComment);
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return savedComment.getId();
	}

}
