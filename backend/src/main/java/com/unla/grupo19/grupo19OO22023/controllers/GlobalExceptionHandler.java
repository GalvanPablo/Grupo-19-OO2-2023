package com.unla.grupo19.grupo19OO22023.controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.unla.grupo19.grupo19OO22023.Exception.DispositivoDeRiegoServiceException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	public static class ErrorResponse {
		private int status;
		private String message;
		private List<String> errors;

		ErrorResponse(int status, String message, List<String> errors) {
			this.status = status;
			this.message = message;
			this.errors = errors;
		}
	}

	@ExceptionHandler(DispositivoDeRiegoServiceException.class)
	public ResponseEntity<ErrorResponse> handleDispositivoDeRiegoException(DispositivoDeRiegoServiceException e) {
		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(),
				Collections.emptyList());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		List<String> errors = new ArrayList<>();
		
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}

		ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Fallo Validaciones", errors);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

}
