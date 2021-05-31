package com.todo.exception;

public class NotFoundException extends ApiException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3963051377086939075L;
	private Error error;

	public NotFoundException(Error error) {
		super(error);
		this.error = error;
	}
}
