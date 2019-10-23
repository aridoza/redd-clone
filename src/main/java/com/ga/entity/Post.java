package com.ga.entity;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="posts")
public class Post {

	@Id
	@Column(name = "post_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;
	
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
//	@JsonIgnore
	private List<Comment> comments;
	
	@ManyToOne(
			cascade = {
					CascadeType.DETACH,
					CascadeType.MERGE, 
					CascadeType.REFRESH
			})
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

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

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	
}
