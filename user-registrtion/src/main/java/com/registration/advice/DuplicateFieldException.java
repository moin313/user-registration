package com.registration.advice;

public class DuplicateFieldException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public DuplicateFieldException(String message) {
		super(message);
	}
}
