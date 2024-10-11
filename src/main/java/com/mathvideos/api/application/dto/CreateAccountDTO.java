package com.mathvideos.api.application.dto;

import com.mathvideos.api.entity.User;

public class CreateAccountDTO {
	private String username;
	private String mail;
	private String password;
	
	public User convertToUser() {
		User user = new User();
		
		user.setUsername(this.getUsername());
		user.setMail(this.getMail());
		user.setPassword(this.getPassword());
		
		return user;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
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
	
}
