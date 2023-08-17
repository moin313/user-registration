package com.registration.advice;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.registration.payload.UtilResponse;
import com.registration.util.ResponseMsg;


@RestControllerAdvice
public class ExceptionHandlerAdvice {

	private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);
	
	@ExceptionHandler(UsernameNotFoundException.class)
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<?> handleUsernameNotFoundException(UsernameNotFoundException exception) {
		logger.debug("Excetption Advice : {} ", exception);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new UtilResponse(null, ResponseMsg.UNAUTHORIZE, HttpStatus.UNAUTHORIZED));
	}

	
	@ExceptionHandler(AccessDeniedException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException exception) {
		logger.debug("Excetption Advice : {} ", exception);
		return ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(new UtilResponse(null, ResponseMsg.FORBIDDEN_ERROR, HttpStatus.FORBIDDEN));
	}

	@ExceptionHandler(HttpRequestMethodNotSupportedException.class)
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
	public String handleMethodNotAllowed(HttpRequestMethodNotSupportedException exception) {
		logger.debug("Excetption Advice : {} ", exception);
		return "The requested method is not supported";
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<?> globalExceptionHandling(Exception exception) {
		logger.debug("Excetption Advice : {} ", exception);
		return ResponseEntity.internalServerError()
				.body(new UtilResponse(null, ResponseMsg.INTERNAL_ERROR, HttpStatus.INTERNAL_SERVER_ERROR));
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
		logger.debug("Excetption Advice : {} ", exception);
		Map<String, String> advices = new HashMap<>();
		exception.getBindingResult().getFieldErrors().forEach(advice -> {
			advices.put(advice.getField(), advice.getDefaultMessage());
		});
		return ResponseEntity.badRequest()
				.body(new UtilResponse(advices, ResponseMsg.INVALID_ARGUMENT, HttpStatus.BAD_REQUEST));
	}

	@ExceptionHandler(IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ResponseEntity<?> illegalArgumentException(IllegalArgumentException exception) {
		logger.debug("Excetption Advice : {} ", exception);
		return ResponseEntity.badRequest().body(new UtilResponse(null, exception.getMessage(), HttpStatus.BAD_REQUEST));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DuplicateFieldException.class)
	public ResponseEntity<?> handleDuplicateEmailException(DuplicateFieldException exception) {
		logger.debug("Excetption Advice : {} ", exception);
		return ResponseEntity.badRequest().body(new UtilResponse(null, exception.getMessage(), HttpStatus.BAD_REQUEST));
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException exception) {
		logger.debug("Excetption Advice : {} ", exception);
		return ResponseEntity.badRequest().body(new UtilResponse(null, exception.getMessage(), HttpStatus.BAD_REQUEST));
	}
}