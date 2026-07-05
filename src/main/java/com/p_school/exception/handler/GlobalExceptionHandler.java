package com.p_school.exception.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.p_school.exception.ErrorResponse;
import com.p_school.exception.BadRequestException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ErrorResponse> BadRequestException(BadRequestException res) {
		return ResponseEntity.ok(new ErrorResponse());
	}

}
