package com.mathvideos.api.application.dto;

import java.util.concurrent.atomic.AtomicInteger;

public class ReturnViewsDTO {
	private int views;
	
	public ReturnViewsDTO(AtomicInteger views) {
		this.views = views.get();
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}
	
}
