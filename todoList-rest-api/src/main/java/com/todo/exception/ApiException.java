package com.todo.exception;

public class ApiException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 579789186581850073L;
	
	private Error error;

	public ApiException(Error error) {
		this.setError(error);
	}

	public Error getError() {
		return error;
	}

	public void setError(Error error) {
		this.error = error;
	}

}
