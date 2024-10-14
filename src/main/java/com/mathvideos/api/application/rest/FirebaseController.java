package com.mathvideos.api.application.rest;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mathvideos.api.domain.service.FirebaseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/api/firebase")
@Tag(name = "Firebase Controller", description = "Gerenciamento de arquivos no bucket do Firebase")
public class FirebaseController {
	
	private final FirebaseService firebaseService;
	
	public FirebaseController(FirebaseService firebaseService) {
		this.firebaseService = firebaseService;
	}
	
	@PostMapping(path = "/thumbnail/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@Operation(summary = "Renomeia a thumbnail, salva no bucket e retorna a URL")
	public ResponseEntity<Object> uploadThumbnail(@RequestPart("file") MultipartFile file) throws Exception {		
		return firebaseService.uploadThumbnail(file);
	}

	@PostMapping(path = "/video/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@Operation(summary = "Renomeia o vídeo, salva no bucket e retorna a URL")
	public ResponseEntity<Object> uploadVideo(@RequestPart("file") MultipartFile file) throws Exception {		
		return firebaseService.uploadVideo(file);
	}

	@DeleteMapping(path = "/delete")
	@Operation(summary = "Deleta o arquivo do bucket")
	public ResponseEntity<Object> deleteFile(String fileUrl) {
		return firebaseService.deleteFile(fileUrl);
	}
}
