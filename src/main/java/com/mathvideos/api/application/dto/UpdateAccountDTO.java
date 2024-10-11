package com.mathvideos.api.application.dto;

import java.time.LocalDateTime;

import com.mathvideos.api.entity.User;

public class UpdateAccountDTO {
	private String id;
	private String username;
	private String displayName;
	private String mail;
	private String password;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
	
	public User convertToUser() {
		User user = new User();
		
		user.setId(this.getId());
		user.setUsername(this.getUsername());
		user.setDisplayName(this.getDisplayName());
		user.setMail(this.getMail());
		user.setPassword(this.getPassword());
		user.setCreatedOn(this.getCreatedOn());
		user.setUpdatedOn(this.getUpdatedOn());
		
		return user;
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
	
	public String getMail() {
		return mail;
	}
	
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
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
	
	public void setUpdatedAt(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}
	
}
