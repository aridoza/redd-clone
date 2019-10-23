package com.ga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ga.dao.PostDao;
import com.ga.dao.UserDao;
import com.ga.entity.Post;
import com.ga.entity.User;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	PostDao postDao;
	
	@Autowired
	UserDao userDao;
	
	@Override
	public List<Post> listPosts() {
		return postDao.listPosts();
	}
	
	@Override
	public Post updatePost(Post post, Long postId) {
		return postDao.updatePost(post, postId);
	}
	
	@Override
	public Post createPost(Post post, String username) {
		User user = userDao.getUserByUsername(username);
		post.setUser(user);
		return postDao.createPost(post);
	}
	
	@Override
	public Long deletePost(Long postId) {
		return postDao.deletePost(postId);
	}
}
