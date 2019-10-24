package com.ga.service;

import java.util.List;

import com.ga.entity.Post;

public interface PostService {
	
	public List<Post> listPosts();
	public Post updatePost(Post post, Long postId);
	public Post createPost(Post post, String username);
	public Long deletePost(Long postId);
	public List<Post> listPostsByUsername(String token);
}
