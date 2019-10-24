package com.ga.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ga.entity.Comment;
import com.ga.entity.Post;
import com.ga.entity.User;

@Repository
public class PostDaoImpl implements PostDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public List<Post> listPosts() {
		List<Post> allPosts = null;
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			allPosts = session.createQuery("FROM Post").getResultList();
		} finally {
			session.close();
		}
		
		return allPosts;
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
	
	@Override
	public Post createPost(Post post) {
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			session.save(post);
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return post;
	}
	
	@Override
	public Long deletePost(Long postId) {
		Session session = sessionFactory.getCurrentSession();
		
		Post savedPost = null;
		
		try {
			session.beginTransaction();
			savedPost = session.get(Post.class, postId);
			session.delete(savedPost);
			
			session.getTransaction().commit();
		} finally {
			session.close();
		}
		return savedPost.getPostId();
	}

	@Override
	public Post getPostByPostId(Long postId) {
		Post post = null;
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			
			post = (Post)session.createQuery("FROM Post p WHERE p.postId = '" + postId + "'").uniqueResult();
		} finally {
			session.close();
		}
		return post;
	}

	@Override
	public List<Post> listPostsByUsername(User user) {
		List<Post> userPosts = null;
		
		Session session = sessionFactory.getCurrentSession();
		
		try {
			session.beginTransaction();
			userPosts = session.createQuery("FROM Post WHERE user_id = '" + user.getUserId() + "'").getResultList();
		} finally {
			session.close();
		}
		return userPosts;
	}
	
	
}
