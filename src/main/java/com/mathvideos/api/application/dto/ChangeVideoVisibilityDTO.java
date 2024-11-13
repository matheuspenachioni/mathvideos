package com.mathvideos.api.application.dto;

import com.mathvideos.api.entity.enumerated.VideoVisibility;

public class ChangeVideoVisibilityDTO {
	private String id;
	private VideoVisibility visibility;
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public VideoVisibility getVisibility() {
		return visibility;
	}
	
	public void setVisibility(VideoVisibility visibility) {
		this.visibility = visibility;
	}
	
}
