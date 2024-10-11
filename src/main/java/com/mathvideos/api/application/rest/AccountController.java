package com.mathvideos.api.application.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mathvideos.api.application.dto.ChangePasswordDTO;
import com.mathvideos.api.application.dto.CreateAccountDTO;
import com.mathvideos.api.application.dto.UpdateAccountDTO;
import com.mathvideos.api.domain.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping(path = "/api/account")
@Tag(name = "Account Controller", description = "Gerenciamento de conta do usuário")
public class AccountController {
	private final AccountService accountService;	

	public AccountController(AccountService accountService) {
		this.accountService = accountService;
	}

	@PostMapping
	@Operation(summary = "Cria uma nova conta")
	private ResponseEntity<Object> createAccount(@RequestBody CreateAccountDTO dto) {
		return accountService.createAccount(dto);
	}
	
	@PutMapping
	@Operation(summary = "Atualiza as informações da conta")
	private ResponseEntity<Object> updateAccount(@RequestBody UpdateAccountDTO dto) {
		return accountService.updateAccount(dto);
	}
	
	@PatchMapping
	@Operation(summary = "Altera a senha da conta")
	private ResponseEntity<Object> changePassword(@RequestBody ChangePasswordDTO dto) {
		return accountService.changePassword(dto);
	}
	
}
