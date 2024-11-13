package com.mathvideos.api.domain.service;

import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mathvideos.api.application.dto.ReturnLikesDislikesDTO;
import com.mathvideos.api.domain.repository.AccountRepository;
import com.mathvideos.api.domain.repository.UserVideoActionRepository;
import com.mathvideos.api.domain.repository.VideoRepository;
import com.mathvideos.api.domain.service.util.ResponseHandler;
import com.mathvideos.api.entity.UserVideoAction;

@Service
public class UserVideoActionService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private final UserVideoActionRepository userVideoActionRepository;
	private final VideoRepository videoRepository;
	private final AccountRepository accountRepository;
	private final ResponseHandler responseHandler;

	public UserVideoActionService(UserVideoActionRepository userVideoActionRepository,
			VideoRepository videoRepository, AccountRepository accountRepository, 
			ResponseHandler responseHandler) {
		this.userVideoActionRepository = userVideoActionRepository;
		this.videoRepository = videoRepository;
		this.accountRepository = accountRepository;
		this.responseHandler = responseHandler;
	}
	
	public ResponseEntity<Object> getUserAction(String userId, String videoId) {
		try {
			if (userId.isEmpty()) {
				return responseHandler.badRequest("É necessário estar logado para dar like");
			}
			if (videoId.isEmpty()) {
				return responseHandler.badRequest("É necessário informar o ID do vídeo");
			}
			if (accountRepository.findById(userId).isEmpty()) {
				return responseHandler.badRequest("Seu usuário não pôde ser encontrado");
			}
			if (videoRepository.findById(videoId).isEmpty()) {
				return responseHandler.notFound("Nenhum vídeo foi encontrado");
			}
			
			Optional<UserVideoAction> action = userVideoActionRepository.getByUserIdAndVideoId(userId, videoId);
			if (action.isEmpty()) {
				return responseHandler.ok("Não há informações para recuperar", null);
			}
			
			return responseHandler.ok("Informações recuperadas com sucesso", new ReturnLikesDislikesDTO(action.get().isLiked(), 
					action.get().isDisliked()));
		} catch (Exception ex) {
			logger.error("{} - Falha ao recuperar informações do usuário ({}) no vídeo ({})", 
					new Date(), userId, videoId);
			logger.error(ex.getMessage());

			return responseHandler.internalServerError("Ocorreu uma falha ao recuperar suas informações para este vídeo");
		}
	}
}
