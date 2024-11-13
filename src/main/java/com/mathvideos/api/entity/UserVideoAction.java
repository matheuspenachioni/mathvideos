package com.mathvideos.api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_video_actions")
public class UserVideoAction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;
    
    @Column(name = "liked", nullable = false)
    private boolean liked;

    @Column(name = "disliked", nullable = false)
    private boolean disliked;

    public UserVideoAction() {
    	
    }
    
	public UserVideoAction(User user, Video video, boolean liked, boolean disliked) {
		this.user = user;
		this.video = video;
		this.liked = liked;
		this.disliked = disliked;
	}
    
	public UserVideoAction(Long id, User user, Video video, boolean liked, boolean disliked) {
		this.id = id;
		this.user = user;
		this.video = video;
		this.liked = liked;
		this.disliked = disliked;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}

	public boolean isLiked() {
		return liked;
	}

	public void setLiked(boolean liked) {
		this.liked = liked;
	}

	public boolean isDisliked() {
		return disliked;
	}

	public void setDisliked(boolean disliked) {
		this.disliked = disliked;
	}
    
}
