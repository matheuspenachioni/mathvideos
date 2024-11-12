package com.mathvideos.api.application.dto;

import com.mathvideos.api.entity.User;
import com.mathvideos.api.entity.Video;
import com.mathvideos.api.entity.enumerated.VideoVisibility;

public class CreateVideoDTO {
	private String title;
	private String description;
	private String videoUrl;
	private String thumbnailUrl;
	private String authorId;
	private VideoVisibility visibility;
	
	public Video convertToVideo() {
		Video video = new Video();
		
		video.setTitle(this.getTitle());
		video.setDescription(this.getDescription());
		video.setVideoUrl(this.getVideoUrl());
		video.setThumbnailUrl(this.getThumbnailUrl());
		video.setAuthor(new User(this.authorId));
		video.setVisibility(this.getVisibility());
		
		return video;
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
	
	public String getAuthorId() {
		return authorId;
	}

	public void setAuthorId(String authorId) {
		this.authorId = authorId;
	}

	public VideoVisibility getVisibility() {
		return visibility;
	}
	
	public void setVisibility(VideoVisibility visibility) {
		this.visibility = visibility;
	}

}
