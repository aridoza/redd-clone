package com.ga.entity;

import javax.persistence.*;

@Entity
@Table(name="posts")
public class Post {
	
	@ManyToOne(cascade = {CascadeType.DETACH,
			CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "user_id", nullable = false)
	private Post post;
	
	/* ??? Refactor ???
	private User user;
	private Long userId;
	
	public Long getUserId() {
		return user.getUserId();
	}
	
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	*/

	@Id
	@Column(name = "post_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;
	
	@Column(nullable = false)
	private String title;
	
	@Column(nullable = false)
	private String description;
	
	public Post() {}
	
	public Post(String title, String description) {
    	this.title = title;
    	this.description = description;
    }

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
