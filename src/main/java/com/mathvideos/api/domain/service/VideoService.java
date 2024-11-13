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
import com.mathvideos.api.application.dto.LikeDislikeDTO;
import com.mathvideos.api.application.dto.ReturnLikesDislikesDTO;
import com.mathvideos.api.application.dto.ReturnViewsDTO;
import com.mathvideos.api.application.dto.VideoDTO;
import com.mathvideos.api.domain.repository.AccountRepository;
import com.mathvideos.api.domain.repository.UserVideoActionRepository;
import com.mathvideos.api.domain.repository.VideoRepository;
import com.mathvideos.api.domain.service.util.ResponseHandler;
import com.mathvideos.api.entity.User;
import com.mathvideos.api.entity.UserVideoAction;
import com.mathvideos.api.entity.Video;
import com.mathvideos.api.entity.enumerated.VideoVisibility;

@Service
public class VideoService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private final VideoRepository videoRepository;
	private final UserVideoActionRepository userVideoActionRepository;
	private final AccountRepository accountRepository;
	private final FirebaseService firebaseService;
	private final ResponseHandler responseHandler;

	public VideoService(VideoRepository videoRepository, UserVideoActionRepository userVideoActionRepository,
			AccountRepository accountRepository, FirebaseService firebaseService, 
			ResponseHandler responseHandler) {
		this.videoRepository = videoRepository;
		this.userVideoActionRepository = userVideoActionRepository;
		this.accountRepository = accountRepository;
		this.firebaseService = firebaseService;
		this.responseHandler = responseHandler;
	}
	
	public ResponseEntity<Object> createVideo(CreateVideoDTO dto) {
		try {
			List<String> messages = new ArrayList<>();
			
			if (checkEmptyFields(messages, dto)) {
				return responseHandler.badRequest(messages);
			}
			
			if (this.accountRepository.findById(dto.getAuthorId()).isEmpty()) {
				return responseHandler.badRequest("Seu usuário não pôde ser encontrado");
			}
			
			String id = videoRepository.save(dto.convertToVideo()).getId();
			
			return responseHandler.ok("Vídeo criado com sucesso", id);
		} catch (Exception ex) {
			logger.error("{} - Falha ao salvar vídeo", new Date());
			logger.error(ex.getMessage());

			return responseHandler.internalServerError("Ocorreu uma falha ao criar o vídeo");
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

			return responseHandler.internalServerError("Ocorreu uma falha ao recuperar os vídeos");
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
			
			return this.responseHandler.ok("Vídeo retornado com sucesso", new VideoDTO(video.get()));
		} catch (Exception ex) {
			logger.error("{} - Falha ao recuperar o vídeo ({})", new Date(), id);
			logger.error(ex.getMessage());

			return responseHandler.internalServerError("Ocorreu uma falha ao recuperar as informações do vídeo");
		}
	}
	
	public ResponseEntity<Object> likeVideo(LikeDislikeDTO dto) {
		try {
			if (dto.getUserId().isEmpty()) {
				return responseHandler.badRequest("É necessário estar logado para dar like");
			}
			if (dto.getVideoId().isEmpty()) {
				return responseHandler.badRequest("É necessário informar o ID do vídeo");
			}
			if (accountRepository.findById(dto.getUserId()).isEmpty()) {
				return responseHandler.badRequest("Seu usuário não pôde ser encontrado");
			}
			
			Optional<Video> video = videoRepository.findById(dto.getVideoId());
			if (video.isEmpty()) {
				return responseHandler.notFound("Nenhum vídeo foi encontrado");
			}
			
			Optional<UserVideoAction> action = userVideoActionRepository.getByUserIdAndVideoId(dto.getUserId(), dto.getVideoId());
			
			if (action.isPresent() && action.get().isLiked()) {
				video.get().decrementLikes();
				videoRepository.saveAndFlush(video.get());
				
				action.get().setLiked(false);
				action.get().setDisliked(false);
				userVideoActionRepository.saveAndFlush(action.get());
				
				return responseHandler.ok("Você removeu o like deste vídeo", 
						new ReturnLikesDislikesDTO(video.get().getLikes(), video.get().getDislikes(),
								action.get().isLiked(), action.get().isDisliked()));
			}
			
			if (action.isPresent() && action.get().isDisliked()) {
				video.get().incrementLikes();
				video.get().decrementDislikes();
				videoRepository.saveAndFlush(video.get());
				
				action.get().setLiked(true);
				action.get().setDisliked(false);
				userVideoActionRepository.saveAndFlush(action.get());
				
				return responseHandler.ok("Você deu like neste vídeo", 
						new ReturnLikesDislikesDTO(video.get().getLikes(), video.get().getDislikes(),
								action.get().isLiked(), action.get().isDisliked()));
			}
			
			if (action.isPresent() && !action.get().isLiked() && !action.get().isDisliked()) {
				video.get().incrementLikes();
				videoRepository.saveAndFlush(video.get());
				
				action.get().setLiked(true);
				action.get().setDisliked(false);
				userVideoActionRepository.saveAndFlush(action.get());
				
				return responseHandler.ok("Você deu like neste vídeo", 
						new ReturnLikesDislikesDTO(video.get().getLikes(), video.get().getDislikes(),
								action.get().isLiked(), action.get().isDisliked()));
			}
			
			video.get().incrementLikes();
			videoRepository.saveAndFlush(video.get());

			UserVideoAction newAction = new UserVideoAction(new User(dto.getUserId()), new Video(dto.getVideoId()), 
					true, false);
			userVideoActionRepository.saveAndFlush(newAction);
			
			return responseHandler.ok("Você deu like neste vídeo", 
					new ReturnLikesDislikesDTO(video.get().getLikes(), video.get().getDislikes(),
							newAction.isLiked(), newAction.isDisliked()));
		} catch (Exception ex) {
			logger.error("{} - Falha ao dar like no vídeo ({})", new Date(), dto.getVideoId());
			logger.error(ex.getMessage());

			return responseHandler.internalServerError("Ocorreu uma falha ao dar like no vídeo");
		}
	}
	
	public ResponseEntity<Object> dislikeVideo(LikeDislikeDTO dto) {
		try {
			if (dto.getUserId().isEmpty()) {
				return responseHandler.badRequest("É necessário estar logado para dar like");
			}
			if (dto.getVideoId().isEmpty()) {
				return responseHandler.badRequest("É necessário informar o ID do vídeo");
			}
			if (accountRepository.findById(dto.getUserId()).isEmpty()) {
				return responseHandler.badRequest("Seu usuário não pôde ser encontrado");
			}
			
			Optional<Video> video = videoRepository.findById(dto.getVideoId());
			if (video.isEmpty()) {
				return responseHandler.notFound("Nenhum vídeo foi encontrado");
			}

			Optional<UserVideoAction> action = userVideoActionRepository.getByUserIdAndVideoId(dto.getUserId(), dto.getVideoId());
			
			if (action.isPresent() && action.get().isDisliked()) {
				video.get().decrementDislikes();
				videoRepository.saveAndFlush(video.get());
				
				action.get().setLiked(false);
				action.get().setDisliked(false);
				userVideoActionRepository.saveAndFlush(action.get());
				
				return responseHandler.ok("Você removeu o dislike deste vídeo", 
						new ReturnLikesDislikesDTO(video.get().getLikes(), video.get().getDislikes(),
								action.get().isLiked(), action.get().isDisliked()));
			}
			
			if (action.isPresent() && action.get().isLiked()) {
				video.get().incrementDislikes();
				video.get().decrementLikes();
				videoRepository.saveAndFlush(video.get());

				action.get().setDisliked(true);
				action.get().setLiked(false);
				userVideoActionRepository.saveAndFlush(action.get());
				
				return responseHandler.ok("Você deu dislike neste vídeo", 
						new ReturnLikesDislikesDTO(video.get().getLikes(), video.get().getDislikes(),
								action.get().isLiked(), action.get().isDisliked()));
			}
			
			if (action.isPresent() && !action.get().isLiked() && !action.get().isDisliked()) {
				video.get().incrementDislikes();
				videoRepository.saveAndFlush(video.get());
				
				action.get().setLiked(false);
				action.get().setDisliked(true);
				userVideoActionRepository.saveAndFlush(action.get());
				
				return responseHandler.ok("Você deu dislike neste vídeo", 
						new ReturnLikesDislikesDTO(video.get().getLikes(), video.get().getDislikes(),
								action.get().isLiked(), action.get().isDisliked()));
			}
			
			video.get().incrementDislikes();
			this.videoRepository.saveAndFlush(video.get());
			
			UserVideoAction newAction = new UserVideoAction(new User(dto.getUserId()), new Video(dto.getVideoId()), 
					true, false);
			this.userVideoActionRepository.saveAndFlush(newAction);
			
			return responseHandler.ok("Você deu dislike neste vídeo", 
					new ReturnLikesDislikesDTO(video.get().getLikes(), video.get().getDislikes(),
							newAction.isLiked(), newAction.isDisliked()));
		} catch (Exception ex) {
			logger.error("{} - Falha ao dar dislike no vídeo ({})", new Date(), dto.getVideoId());
			logger.error(ex.getMessage());

			return this.responseHandler.internalServerError("Ocorreu uma falha ao dar dislike no vídeo");
		}
	}
	
	public ResponseEntity<Object> viewVideo(String videoId) {
		try {
			if (videoId.isEmpty()) {
				return responseHandler.badRequest("É necessário informar o ID do vídeo");
			}
			
			Optional<Video> video = videoRepository.findById(videoId);
			if (video.isEmpty()) {
				return responseHandler.notFound("Nenhum vídeo foi encontrado");
			}
			
			video.get().incrementViews();
			videoRepository.saveAndFlush(video.get());
			
			return responseHandler.ok("Visualização contabilizada com sucesso", 
					new ReturnViewsDTO(video.get().getViews()));
		} catch (Exception ex) {
			logger.error("{} - Falha ao contabilizar visualização no vídeo ({})", new Date(), videoId);
			logger.error(ex.getMessage());

			return this.responseHandler.internalServerError("Ocorreu ao contabilizar sua visualização neste vídeo");
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
		if (dto.getAuthorId().isEmpty()) {
			messages.add("É necessário estar lgoado para criar um vídeo");
		}
		
		return !messages.isEmpty();
	}
	
}
