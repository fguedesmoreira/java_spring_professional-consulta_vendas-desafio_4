package com.devsuperior.dsmeta.controllers;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.dsmeta.dto.errors.CustomErrorDTO;
import com.devsuperior.dsmeta.exceptions.DateConvertException;

@ControllerAdvice
public class ExceptionHandlerController {

	@ExceptionHandler(DateConvertException.class)
	public ResponseEntity<CustomErrorDTO> resourceNotFound(DateConvertException e, HttpServletRequest request) {

		HttpStatus status = HttpStatus.BAD_REQUEST;

		CustomErrorDTO err = new CustomErrorDTO(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());

		return ResponseEntity.status(status).body(err);

	}

}