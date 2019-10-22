package com.ga.entity;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="users")
public class User {
	
	@OneToMany(
			mappedBy = "user", 
			cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Post> posts;
	
	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}
	
	public List<Post> getPosts() {
		return posts;
	}

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
	
    @NotBlank(message = "Username cannot be blank")
    @Column(unique = true, nullable = false)
    private String username;
	
    @NotBlank(message = "Password cannot be blank")
    @Column(name = "password", nullable = false)
    private String password;

    @Email(message = "Email invalid")
    @Column(name = "email", nullable = false)
    private String email;
    
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "user_profile_id")
	private UserProfile userProfile;
	
	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
    
    public User() {}
    
    public Long getUserId() {
		return userId;
    }

    public void setUserId(Long userId) {
		this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
		return password;
    }

    public void setPassword(String password) {
		this.password = password;
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
    
    
}