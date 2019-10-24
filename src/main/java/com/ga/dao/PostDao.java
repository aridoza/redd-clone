package com.ga.dao;

import java.util.List;

import com.ga.entity.Post;
import com.ga.entity.User;

public interface PostDao {

	public List<Post> listPosts();
	public Post updatePost(Post post, Long postId);
	public Post createPost(Post post);
	public Long deletePost(Long postId);
	public Post getPostByPostId(Long postId);
	public List<Post> listPostsByUsername(User user);
}
