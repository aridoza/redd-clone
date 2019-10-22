package com.ga.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ga.entity.Post;

@Repository
public class PostDaoImpl implements PostDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Post> listPosts() {
		// TODO - return list of all posts
		return null;
	}
	
	@Override
	public Post updatePost(Post post, Long postId) {
		Post savedPost = null;
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			savedPost = session.get(Post.class, postId);
			
			savedPost.setDescription(post.getDescription());
			
			session.update(savedPost);
			
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		
		return savedPost;
	}
}
