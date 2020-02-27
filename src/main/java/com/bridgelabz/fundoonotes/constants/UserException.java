package com.bridgelabz.fundoonotes.constants;

public class UserException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int status;

	public UserException(String message, int status) {
		super(message);
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

}
