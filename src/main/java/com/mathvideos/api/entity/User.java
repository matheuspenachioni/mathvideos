package com.mathvideos.api.entity;

import java.time.LocalDateTime;

import com.github.f4b6a3.ulid.UlidCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	@Column(name = "id", updatable = false)
	private String id;
	
	@Column(name = "username", nullable = false)
	private String username;
	
	@Column(name = "display_name")
	private String displayName;
	
	@Column(name = "mail", nullable = false)
	private String mail;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "created_on", updatable = false)
	private LocalDateTime createdOn;
	
	@Column(name = "updated_on", nullable = false)
	private LocalDateTime updatedOn;
	
	public User() {
		
	}
	
	public User(String id, String username, String displayName, String mail, String password, 
			LocalDateTime createdOn, LocalDateTime updatedOn) {
		this.id = id;
		this.username = username;
		this.displayName = displayName;
		this.mail = mail;
		this.password = password;
		this.createdOn = createdOn;
		this.updatedOn = updatedOn;
	}

	@PrePersist
	private void prePersist() {
		this.setId(UlidCreator.getUlid().toString());
		this.setCreatedOn(LocalDateTime.now());
		this.setUpdatedOn(LocalDateTime.now());
	}
	
	@PreUpdate
	private void preUpdate() {
		this.setUpdatedOn(LocalDateTime.now());
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

	public void setCreatedOn(LocalDateTime createdAt) {
		this.createdOn = createdAt;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedAt) {
		this.updatedOn = updatedAt;
	}
	
}
