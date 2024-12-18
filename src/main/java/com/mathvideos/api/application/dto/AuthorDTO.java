package com.mathvideos.api.application.dto;

public class AuthorDTO {
	private String id;
	private String username;
	private String displayName;
	
	public AuthorDTO(String id, String username, String displayName) {
		this.id = id;
		this.username = username;
		this.displayName = displayName;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getDisplayName() {
		return displayName;
	}
	
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}
