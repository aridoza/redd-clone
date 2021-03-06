package com.ga.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import com.ga.entity.User;
import com.ga.entity.UserProfile;

@Repository
public class UserProfileDaoImpl implements UserProfileDao {
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public UserProfile createUserProfile(String username, UserProfile userProfile) {
		User user = userDao.getUserByUsername(username);
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			session.save(userProfile);
			user.setUserProfile(userProfile);
			session.update(user);
			
			session.getTransaction().commit();
		} finally {
			session.close();
		}

		return userProfile;
	}

	@Override
	public UserProfile getUserProfile(String username) {
		if (username != null) {			
			User user = userDao.getUserByUsername(username);
			return user.getUserProfile();
		}
		throw new UsernameNotFoundException("Username not found");
	}

}