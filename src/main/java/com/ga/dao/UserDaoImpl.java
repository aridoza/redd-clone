package com.ga.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ga.entity.User;
import com.ga.exception.EntityNotFoundException;

@Repository
public class UserDaoImpl implements UserDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<User> listUsers() {
		List<User> allUsers = null;
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			allUsers = session.createQuery("FROM User").getResultList();
		} finally {
			session.close();
		}
		
		return allUsers;
	}
	
	@Override
	public User signup(User user) {
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			session.save(user);
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		
		return user;
	}
	
	@Override
//	public User login(User user) throws EntityNotFoundException {
	public User login(User user) {
		User savedUser = null;
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			savedUser = (User)session.createQuery("FROM User u WHERE u.email = '" + 
					user.getEmail() + "'").getSingleResult();
		} catch (Exception e) {
//			throw new EntityNotFoundException("User not found");
		} finally {
			session.close();
		}
		
		return savedUser;
	}
	
	@Override
	public Long deleteUser(Long userId) {
		Session session = sessionFactory.getCurrentSession();
		
		User savedUser = null;
		
		try {
			session.beginTransaction();
			
			savedUser = session.get(User.class, userId);
			session.delete(savedUser);
			
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		
		if (savedUser.getId() != null) {			
			return savedUser.getId();
		} else {
			return null;
		}
	}
	
	@Override
	public User getUserByUsername(String username) {
		User user = null;
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			user = (User)session.createQuery("FROM User u WHERE u.username = '" + 
				username + "'").uniqueResult();
		} finally {
			session.close();
		}
		
		return user;
	}

	@Override
	public User getUserByUserId(Long userId) {
		User user = null;
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			user = (User)session.createQuery("FROM User u WHERE u.user_id = '" + 
				userId + "'").uniqueResult();
		} finally {
			session.close();
		}
		
		return user;
	}

	@Override
	public User getUserByEmail(String userEmail) {
		User user = null;
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			user = (User)session.createQuery("FROM User u WHERE u.email = '" +
					userEmail + "'").uniqueResult();
		} finally {
			session.close();
		}
		return user;
	}

}