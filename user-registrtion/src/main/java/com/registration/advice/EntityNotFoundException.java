package com.registration.advice;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -1916186151541579172L;

	public EntityNotFoundException(String msg) {
		super(msg);
	}
}
