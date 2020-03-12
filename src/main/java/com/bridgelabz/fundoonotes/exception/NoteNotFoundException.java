package com.bridgelabz.fundoonotes.exception;

public class NoteNotFoundException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	private final int status;

	
	public NoteNotFoundException(String message, int status) {
		super(message);
		this.status = status;
	}

	public int getStatus() {
		return status;
	}
}
