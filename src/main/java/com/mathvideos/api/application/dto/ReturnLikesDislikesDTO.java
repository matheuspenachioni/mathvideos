package com.mathvideos.api.application.dto;

import java.util.concurrent.atomic.AtomicInteger;

public class ReturnLikesDislikesDTO {
	private int likes;
	private int dislikes;
	private boolean liked;
	private boolean disliked;
	
	public ReturnLikesDislikesDTO(boolean liked, boolean disliked) {
        this.liked = liked;
        this.disliked = disliked;
	}
	
	public ReturnLikesDislikesDTO(AtomicInteger likes, AtomicInteger dislikes, boolean liked, 
			boolean disliked) {
        this.likes = likes.get();
        this.dislikes = dislikes.get();
        this.liked = liked;
        this.disliked = disliked;
    }

	public int getLikes() {
		return likes;
	}

	public int getDislikes() {
		return dislikes;
	}

	public boolean isLiked() {
		return liked;
	}

	public boolean isDisliked() {
		return disliked;
	}

}
