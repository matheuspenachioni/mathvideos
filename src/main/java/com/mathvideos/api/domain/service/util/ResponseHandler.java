package com.mathvideos.api.domain.service.util;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseHandler {
	// HTTP 200 - OK
	public ResponseEntity<Object> ok(String message, Object result) {
		ResponseBody response = new ResponseBody();
		response.setStatus(HttpStatus.OK.value());
		response.setMessage(message);
		response.setResult(result);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	public ResponseEntity<Object> ok(List<String> messages) {
		ResponseBody response = new ResponseBody();
		response.setStatus(HttpStatus.OK.value());
		response.setMessages(messages);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	// HTTP 201 - CREATED
	public ResponseEntity<Object> created(String message, Object result) {
		ResponseBody response = new ResponseBody();
		response.setStatus(HttpStatus.CREATED.value());
		response.setMessage(message);
		response.setResult(result);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	public ResponseEntity<Object> created(List<String> messages) {
		ResponseBody response = new ResponseBody();
		response.setStatus(HttpStatus.CREATED.value());
		response.setMessages(messages);

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	// HTTP 202 - ACCEPTED
	public ResponseEntity<Object> accepted(String message) {
		ResponseBody response = new ResponseBody();
		response.setStatus(HttpStatus.ACCEPTED.value());
		response.setMessage(message);

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}

	public ResponseEntity<Object> accepted(List<String> messages) {
		ResponseBody response = new ResponseBody();
		response.setStatus(HttpStatus.ACCEPTED.value());
		response.setMessages(messages);

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}

	// HTTP 400 - BAD REQUEST
	public ResponseEntity<Object> badRequest(String message) {
		ResponseBody response = new ResponseBody();
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setMessage(message);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	public ResponseEntity<Object> badRequest(List<String> messages) {
		ResponseBody response = new ResponseBody();
		response.setStatus(HttpStatus.BAD_REQUEST.value());
		response.setMessages(messages);

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	}

	// HTTP 404 - NOT FOUND
	public ResponseEntity<Object> notFound(String message) {
		ResponseBody response = new ResponseBody();
		response.setStatus(HttpStatus.NOT_FOUND.value());
		response.setMessage(message);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	public ResponseEntity<Object> notFound(List<String> messages) {
		ResponseBody response = new ResponseBody();
		response.setStatus(HttpStatus.NOT_FOUND.value());
		response.setMessages(messages);

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}

	// HTTP 500 - INTERAL SERVER ERROR
	public ResponseEntity<Object> internalServerError(String message) {
		ResponseBody response = new ResponseBody();
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setMessage(message);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}

	public ResponseEntity<Object> internalServerError(List<String> messages) {
		ResponseBody response = new ResponseBody();
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		response.setMessages(messages);

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	}
}
