package com.bridgelabz.fundoonotes.exception;

public class UserException extends RuntimeException {

	/**
	 * Created custom exception in which we can pass our own messages and status
	 * codes
	 * 
	 * @author Niranjan C.t
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
