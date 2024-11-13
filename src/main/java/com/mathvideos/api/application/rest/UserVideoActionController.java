package com.mathvideos.api.application.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mathvideos.api.domain.service.UserVideoActionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/api/user-video")
@Tag(name = "UserVideoAction Controller", description = "Gerenciamento de ações em vídeos")
public class UserVideoActionController {

	private final UserVideoActionService userVideoActionService;

	public UserVideoActionController(UserVideoActionService userVideoActionService) {
		this.userVideoActionService = userVideoActionService;
	}
	
	@GetMapping
	@Operation(summary = "Retorna as interações do usuário no vídeo")
	public ResponseEntity<Object> getUserAction(@RequestParam(name = "userId", required = true) String userId, 
			@RequestParam(name = "videoId", required = true) String videoId) {
		return userVideoActionService.getUserAction(userId, videoId);
	}
	
}
