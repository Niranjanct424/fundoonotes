package com.bridgelabz.fundoonotes.response;

public class Response {

	private String message;

	private int statusCode;


	public Response(String message, int statusCode) {
		this.message = message;
		this.statusCode = statusCode;
		
	}
	public Response(String message)
	{
		this.message = message;

	}

	/**
	 * Constructor to fetch response if exist any.
	 * 
	 */

	public Response() {

	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}



	@Override
	public String toString() {
		return "Response [message=" + message + ", statusCode=" + statusCode + "]";
	}

}
