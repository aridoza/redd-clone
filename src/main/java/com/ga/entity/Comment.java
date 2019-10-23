package com.ga.entity;

import javax.persistence.*;

@Entity
@Table(name = "comments")
public class Comment {
	
	@ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;
	
	@Id
	@Column(name = "comment_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long commentId;

	@Column
	private String text;
	
	public Comment() {}

	public Long getCommentId() {
		return commentId;
	}

	public void setCommentId(Long commentId) {
		this.commentId = commentId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	
}
