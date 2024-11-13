package com.mathvideos.api.entity;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.f4b6a3.ulid.UlidCreator;
import com.mathvideos.api.entity.enumerated.VideoVisibility;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "videos")
public class Video {
	@Id
	@Column(name = "id", updatable = false)
	private String id;
	
	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", length = 3000)
	private String description;

	@Column(name = "videoUrl", nullable = false)
	private String videoUrl;
	
	@Column(name = "thumbnailUrl", nullable = false)
	private String thumbnailUrl;

	@Column(name = "likes", nullable = false)
	private AtomicInteger likes;

	@Column(name = "dislikes", nullable = false)
	private AtomicInteger dislikes;

	@Column(name = "views", nullable = false)
	private AtomicInteger views;
	
	@Column(name = "visibility", nullable = false)
	private VideoVisibility visibility;
	
	@ManyToOne
	@JoinColumn(name = "author_id")
	private User author;
	
	@Column(name = "created_on", updatable = false)
	private LocalDateTime createdOn;
	
	@Column(name = "updated_on", nullable = false)
	private LocalDateTime updatedOn;

	public Video() {
		
	}
	
	public Video(String id) {
		this.id = id;
	}

	public Video(String id, String title, String description, String videoUrl, String thumbnailUrl, AtomicInteger likes,
			AtomicInteger dislikes, AtomicInteger views, VideoVisibility visibility, User author, LocalDateTime createdOn, 
			LocalDateTime updatedOn) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.videoUrl = videoUrl;
		this.thumbnailUrl = thumbnailUrl;
		this.likes = likes;
		this.dislikes = dislikes;
		this.views = views;
		this.visibility = visibility;
		this.author = author;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	@PrePersist
	private void prePersist() {
		this.setId(UlidCreator.getUlid().toString());
		this.setLikes(new AtomicInteger(0));
		this.setDislikes(new AtomicInteger(0));
		this.setViews(new AtomicInteger(0));
		this.setCreatedOn(LocalDateTime.now());
		this.setUpdatedOn(LocalDateTime.now());
	}
	
	@PreUpdate
	private void preUpdate() {
		this.setUpdatedOn(LocalDateTime.now());
	}
	
	public void incrementLikes() {
		 this.likes.incrementAndGet();
    }

    public void decrementLikes() {
    	 this.likes.decrementAndGet();
    }

    public void incrementDislikes() {
    	 this.dislikes.incrementAndGet();
    }

    public void decrementDislikes() {
    	 this.dislikes.decrementAndGet();
    }

    public void incrementViews() {
        this.views.incrementAndGet();
    }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getThumbnailUrl() {
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}

	public AtomicInteger getLikes() {
		return likes;
	}

	public void setLikes(AtomicInteger likes) {
		this.likes = likes;
	}

	public AtomicInteger getDislikes() {
		return dislikes;
	}

	public void setDislikes(AtomicInteger dislikes) {
		this.dislikes = dislikes;
	}

	public AtomicInteger getViews() {
		return views;
	}

	public void setViews(AtomicInteger views) {
		this.views = views;
	}

	public VideoVisibility getVisibility() {
		return visibility;
	}

	public void setVisibility(VideoVisibility visibility) {
		this.visibility = visibility;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}
	
}
