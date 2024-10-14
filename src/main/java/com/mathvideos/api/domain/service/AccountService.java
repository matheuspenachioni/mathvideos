package com.mathvideos.api.domain.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mathvideos.api.application.dto.ChangePasswordDTO;
import com.mathvideos.api.application.dto.CreateAccountDTO;
import com.mathvideos.api.application.dto.UpdateAccountDTO;
import com.mathvideos.api.domain.repository.AccountRepository;
import com.mathvideos.api.domain.service.util.ResponseHandler;
import com.mathvideos.api.entity.User;

@Service
public class AccountService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private final AccountRepository accountRepository;
	private final ResponseHandler responseHandler;
	private final PasswordEncoder passwordEncoder;

	public AccountService(AccountRepository accountRepository, ResponseHandler responseHandler,
			PasswordEncoder passwordEncoder) {
		this.accountRepository = accountRepository;
		this.responseHandler = responseHandler;
		this.passwordEncoder = passwordEncoder;
	}

	public ResponseEntity<Object> createAccount(CreateAccountDTO dto) {
		try {
			List<String> messages = new ArrayList<>();

			if (checkEmptyFields(messages, dto)) {
				return responseHandler.badRequest(messages);
			}

			if (accountRepository.findByUsername(dto.getUsername()).isPresent()) {
				return responseHandler.badRequest("O nome de usuário informado já está em uso");
			}

			if (accountRepository.findByMail(dto.getMail()).isPresent()) {
				return responseHandler.badRequest("O endereço de e-mail informado já está em uso");
			}

			dto.setPassword(passwordEncoder.encode(dto.getPassword()));
			accountRepository.save(dto.convertToUser());

			return responseHandler.ok("Sua conta foi criada com sucesso", null);
		} catch (Exception ex) {
			logger.error("{} - Falha ao criar a conta do usuário", new Date());
			logger.error(ex.getMessage());

			return responseHandler.internalServerError("Ocorreu uma falha ao criar sua conta");
		}
	}

	public ResponseEntity<Object> updateAccount(UpdateAccountDTO dto) {
		try {
			if (dto.getId().isEmpty()) {
				return responseHandler.badRequest("Seu usuário não pôde ser encontrado");
			}

			Optional<User> searchByUsername = accountRepository.findByUsername(dto.getUsername());

			if (searchByUsername.isPresent() 
					&& searchByUsername.get().getUsername().equals(dto.getUsername())
					&& !searchByUsername.get().getId().equals(dto.getId())) {
				return responseHandler.badRequest("O nome de usuário informado já está em uso");
			}

			Optional<User> searchByMail = accountRepository.findByMail(dto.getMail());
			if (searchByMail.isPresent() 
					&& searchByMail.get().getMail().equals(dto.getMail())
					&& !searchByMail.get().getId().equals(dto.getId())) {
				return responseHandler.badRequest("O endereço de e-mail já está em uso");
			}

			accountRepository.saveAndFlush(dto.convertToUser());

			return responseHandler.ok("Suas informações foram atualizadas com sucesso", null);
		} catch (Exception ex) {
			logger.error("{} - Falha ao atualizar informações do usuário ({})", new Date(), dto.getId());
			logger.error(ex.getMessage());

			return responseHandler.internalServerError("Ocorreu uma falha ao atualizar as informações da sua conta");
		}
	}

	public ResponseEntity<Object> changePassword(ChangePasswordDTO dto) {
		try {
			List<String> messages = new ArrayList<>();

			if (validatePassword(messages, dto)) {
				return responseHandler.badRequest(messages);
			}

			if (dto.getId().isEmpty()) {
				return responseHandler.badRequest("Seu usuário não pôde ser encontrado");
			}

			Optional<User> user = accountRepository.findById(dto.getId());
			if (user.isEmpty() || user.get().getId() != dto.getId()) {
				return responseHandler.badRequest("Seu usuário não pôde ser encontrado");
			}
			if (!passwordEncoder.matches(dto.getCurrentPassword(), user.get().getPassword())) {
				return responseHandler.badRequest("A senha atual está incorreta");
			}

			user.get().setPassword(passwordEncoder.encode(dto.getNewPassword()));
			accountRepository.save(user.get());

			return responseHandler.ok("Suas senha foi alterada com sucesso", null);
		} catch (Exception ex) {
			logger.error("{} - Falha ao alterar senha do usuário ({})", new Date(), dto.getId());
			logger.error(ex.getMessage());

			return responseHandler.internalServerError("Ocorreu uma falha ao alterada a senha da sua conta");
		}
	}

	private boolean checkEmptyFields(List<String> messages, CreateAccountDTO dto) {
		if (dto.getUsername().isEmpty()) {
			messages.add("É necessário informar um nome de usuário");
		}
		if (dto.getMail().isEmpty()) {
			messages.add("É necessário informar um endereço de e-mail");
		}
		if (dto.getPassword().isEmpty()) {
			messages.add("É necessário informar uma senha válida");
		}

		return !messages.isEmpty();
	}

	private boolean validatePassword(List<String> messages, ChangePasswordDTO dto) {
		if (dto.getId().isEmpty()) {
			messages.add("Seu usuário não pôde ser encontrado");
		}
		if (dto.getCurrentPassword().isEmpty()) {
			messages.add("É necessário informar a senha atual");
		}
		if (dto.getNewPassword().isEmpty() || dto.getConfirmNewPassword().isEmpty()) {
			messages.add("É necessário informar a nova senha");
		}
		if (dto.getNewPassword().equals(dto.getCurrentPassword())) {
			messages.add("A nova senha não pode ser igual a atual");
		}
		if (!dto.getNewPassword().equals(dto.getConfirmNewPassword())) {
			messages.add("A senha nova e a confirmação não coincidem");
		}

		return !messages.isEmpty();
	}

}
