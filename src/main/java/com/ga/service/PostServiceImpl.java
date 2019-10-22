package com.ga.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ga.dao.PostDao;
import com.ga.entity.Post;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	PostDao postDao;
	
	@Override
	public List<Post> listPosts() {
		return postDao.listPosts();
	}
	
	@Override
	public Post updatePost(Post post, Long postId) {
		return postDao.updatePost(post, postId);
	}
}
