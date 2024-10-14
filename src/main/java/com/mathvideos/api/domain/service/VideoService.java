package com.mathvideos.api.domain.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mathvideos.api.application.dto.ChangeVideoVisibilityDTO;
import com.mathvideos.api.application.dto.CreateVideoDTO;
import com.mathvideos.api.domain.repository.VideoRepository;
import com.mathvideos.api.domain.service.util.ResponseHandler;
import com.mathvideos.api.entity.Video;
import com.mathvideos.api.entity.enumerated.VideoVisibility;

@Service
public class VideoService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private final VideoRepository videoRepository;
	private final FirebaseService firebaseService;
	private final ResponseHandler responseHandler;

	public VideoService(VideoRepository videoRepository, FirebaseService firebaseService, 
			ResponseHandler responseHandler) {
		this.videoRepository = videoRepository;
		this.firebaseService = firebaseService;
		this.responseHandler = responseHandler;
	}
	
	public ResponseEntity<Object> createVideo(CreateVideoDTO dto) {
		try {
			List<String> messages = new ArrayList<>();
			
			if (checkEmptyFields(messages, dto)) {
				return responseHandler.badRequest(messages);
			}
			
			String id = videoRepository.save(dto.convertToVideo()).getId();
			
			return this.responseHandler.ok("Vídeo criado com sucesso", id);
		} catch (Exception ex) {
			logger.error("{} - Falha ao salvar vídeo", new Date());
			logger.error(ex.getMessage());

			return this.responseHandler.internalServerError("Ocorreu uma falha ao criar o vídeo");
		}
	}
	
	public ResponseEntity<Object> getAllVideos(Pageable pageable, String search) {
		try {
			Page<Video> videos = videoRepository.listAll(pageable, search);
			
			if (videos.isEmpty()) {
				return responseHandler.notFound("Nenhum vídeo foi encontrado");
			}

			return this.responseHandler.ok("Vídeos retornados com sucesso", videos);
		} catch (Exception ex) {
			logger.error("{} - Falha ao recuperar os vídeos", new Date());
			logger.error(ex.getMessage());

			return this.responseHandler.internalServerError("Ocorreu uma falha ao recuperar os vídeos");
		}
	}
	
	public ResponseEntity<Object> getVideo(String id) {
		try {
			if (id.isEmpty()) {
				return responseHandler.badRequest("É necessário informar o ID do vídeo");
			}
			
			Optional<Video> video = videoRepository.findById(id);
			
			if (video.isEmpty()) {
				return responseHandler.notFound("Nenhum vídeo foi encontrado");
			}
			
			return this.responseHandler.ok("Vídeo retornado com sucesso", video.get());
		} catch (Exception ex) {
			logger.error("{} - Falha ao recuperar o vídeo ({})", new Date(), id);
			logger.error(ex.getMessage());

			return this.responseHandler.internalServerError("Ocorreu uma falha ao recuperar as informações do vídeo");
		}
	}
	
	public ResponseEntity<Object> likeVideo(String id) {
		try {
			if (id.isEmpty()) {
				return responseHandler.badRequest("É necessário informar o ID do vídeo");
			}
			
			Optional<Video> video = videoRepository.findById(id);
			
			if (video.isEmpty()) {
				return responseHandler.notFound("Nenhum vídeo foi encontrado");
			}
			
			video.get().incrementLikes();
			videoRepository.saveAndFlush(video.get());
			
			return this.responseHandler.ok("Você deu like neste vídeo", null);
		} catch (Exception ex) {
			logger.error("{} - Falha ao dar like no vídeo ({})", new Date(), id);
			logger.error(ex.getMessage());

			return this.responseHandler.internalServerError("Ocorreu uma falha ao dar like no vídeo");
		}
	}
	
	public ResponseEntity<Object> dislikeVideo(String id) {
		try {
			if (id.isEmpty()) {
				return responseHandler.badRequest("É necessário informar o ID do vídeo");
			}
			
			Optional<Video> video = videoRepository.findById(id);
			
			if (video.isEmpty()) {
				return responseHandler.notFound("Nenhum vídeo foi encontrado");
			}
			
			video.get().incrementDislikes();
			videoRepository.saveAndFlush(video.get());
			
			return this.responseHandler.ok("Você deu dislike neste vídeo", null);
		} catch (Exception ex) {
			logger.error("{} - Falha ao dar dislike no vídeo ({})", new Date(), id);
			logger.error(ex.getMessage());

			return this.responseHandler.internalServerError("Ocorreu uma falha ao dar dislike no vídeo");
		}
	}
	
	public ResponseEntity<Object> changeVisibility(ChangeVideoVisibilityDTO dto) {
		try {
			if (dto.getId().isEmpty()) {
				return responseHandler.badRequest("É necessário informar o ID do vídeo");
			}
			if (dto.getVisibility() == null || !Arrays.asList(VideoVisibility.values()).contains(dto.getVisibility())) {
				return responseHandler.badRequest("É necessário informar a visibilidade do vídeo");
			}
			
			Optional<Video> video = videoRepository.findById(dto.getId());
			
			if (video.isEmpty()) {
				return responseHandler.notFound("Nenhum vídeo foi encontrado");
			}
			
			video.get().setVisibility(dto.getVisibility());
			videoRepository.saveAndFlush(video.get());

			return this.responseHandler.ok("Este vídeo agora é "+ dto.getVisibility().getDescription(), null);
		} catch (Exception ex) {
			logger.error("{} - Falha ao alterar visibilidade do vídeo ({})", new Date(), dto.getId());
			logger.error(ex.getMessage());

			return this.responseHandler.internalServerError("Ocorreu uma falha alterar a visibilidade deste vídeo");
		}
	}
	
	public ResponseEntity<Object> deleteVideo(String id) {
		try {
			if (id.isEmpty()) {
				return responseHandler.badRequest("É necessário informar o ID do vídeo");
			}
			
			Optional<Video> video = videoRepository.findById(id);
			
			if (video.isEmpty()) {
				return responseHandler.notFound("Nenhum vídeo foi encontrado");
			}
			
			firebaseService.deleteFile(video.get().getVideoUrl());
			firebaseService.deleteFile(video.get().getThumbnailUrl());
			
			videoRepository.deleteById(id);

			return this.responseHandler.ok("Vídeo deletado com sucesso", null);
		} catch (Exception ex) {
			logger.error("{} - Falha ao deletar vídeo ({})", new Date(), id);
			logger.error(ex.getMessage());

			return this.responseHandler.internalServerError("Ocorreu uma falha ao deletar este vídeo");
		}
	}
	
	private boolean checkEmptyFields(List<String> messages, CreateVideoDTO dto) {
		if (dto.getTitle().isEmpty()) {
			messages.add("É necessário informar o título");
		}
		if (dto.getVideoUrl().isEmpty()) {
			messages.add("É necessário fazer o upload do vídeo");
		}
		if (dto.getThumbnailUrl().isEmpty()) {
			messages.add("É necessário fazer o upload da thumbnail");
		}
		if (dto.getVisibility() == null || !Arrays.asList(VideoVisibility.values()).contains(dto.getVisibility())) {
			messages.add("É necessário informar a visibilidade do vídeo");
		}
		
		return !messages.isEmpty();
	}
	
}
