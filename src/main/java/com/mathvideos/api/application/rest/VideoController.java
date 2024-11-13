package com.mathvideos.api.application.rest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mathvideos.api.application.dto.ChangeVideoVisibilityDTO;
import com.mathvideos.api.application.dto.CreateVideoDTO;
import com.mathvideos.api.application.dto.LikeDislikeDTO;
import com.mathvideos.api.domain.service.VideoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/api/video")
@Tag(name = "Video Controller", description = "Manipulação de vídeos")
public class VideoController {
	private final VideoService videoService;

	public VideoController(VideoService videoService) {
		this.videoService = videoService;
	}
	
	@PostMapping
	@Operation(summary = "Cria um novo vídeo")
	private ResponseEntity<Object> createVideo(@RequestBody CreateVideoDTO dto) {
		return videoService.createVideo(dto);
	}
	
	@GetMapping
	@Operation(summary = "Retorna todos os vídeos públicos por ordem de publicação")
	private ResponseEntity<Object> getAllVideos(@PageableDefault Pageable pageable, 
			@RequestParam(name = "search", defaultValue = "") String search) {
		return videoService.getAllVideos(pageable, search);
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Retorna as informações de um vídeo pelo ID")
	private ResponseEntity<Object> getVideo(@PathVariable(name = "id", required = true) String id) {
		return videoService.getVideo(id);
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Deleta um vídeo e os arquivos associados a ele")
	private ResponseEntity<Object> deleteVideo(@PathVariable(name = "id", required = true) String id) {
		return videoService.deleteVideo(id);
	}
	
	@PostMapping("/like")
	@Operation(summary = "Adiciona um like ao vídeo")
	private ResponseEntity<Object> likeVideo(@RequestBody LikeDislikeDTO dto) {
		return videoService.likeVideo(dto);
	}
	
	@PostMapping("/dislike")
	@Operation(summary = "Adiciona um dislike ao vídeo")
	private ResponseEntity<Object> dislikeVideo(@RequestBody LikeDislikeDTO dto) {
		return videoService.dislikeVideo(dto);
	}
	
	@PostMapping("/{id}/view")
	@Operation(summary = "Adiciona uma view ao vídeo")
	private ResponseEntity<Object> viewVideo(@PathVariable(name = "id") String id) {
		return videoService.viewVideo(id);
	}
	
	@PostMapping("/change-visibility")
	@Operation(summary = "Altera a visibilidade de um vídeo já publicado")
	private ResponseEntity<Object> changeVisibility(@RequestBody ChangeVideoVisibilityDTO dto) {
		return videoService.changeVisibility(dto);
	}
}
