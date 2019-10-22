package com.ga.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ga.entity.User;

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
}