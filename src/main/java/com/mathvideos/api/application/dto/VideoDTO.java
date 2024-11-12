package com.mathvideos.api.application.dto;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

import com.mathvideos.api.entity.Video;
import com.mathvideos.api.entity.enumerated.VideoVisibility;

public class VideoDTO {
	private String id;
	private String title;
	private String description;

	private String videoUrl;
	private String thumbnailUrl;

	private AtomicInteger likes;
	private AtomicInteger dislikes;
	private AtomicInteger views;

	private VideoVisibility visibility;

	private AuthorDTO author;
	
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
	
	public VideoDTO(Video video) {
		this.id = video.getId();
		this.title = video.getTitle();
		this.description = video.getDescription();
		
		this.videoUrl = video.getVideoUrl();
		this.thumbnailUrl = video.getThumbnailUrl();
		
		this.likes = video.getLikes();
		this.dislikes = video.getDislikes();
		this.views = video.getViews();
		
		this.visibility = video.getVisibility();
		
		this.author = new AuthorDTO(
				video.getAuthor().getId(), 
				video.getAuthor().getUsername(), 
				video.getAuthor().getDisplayName()
		);
		
		this.createdOn = video.getCreatedOn();
		this.updatedOn = video.getUpdatedOn();
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

	public AuthorDTO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorDTO author) {
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
