package com.bridgelabz.fundoonotes.response;

import com.bridgelabz.fundoonotes.dto.LoginDto;

public class Response {

	private String message;
	private int statusCode;
	@SuppressWarnings("unused")
	private LoginDto dto;

	public Response(String message, int statusCode, LoginDto dto) {
		super();
		this.message = message;
		this.statusCode = statusCode;
		this.dto = dto;
	}

	public Response(String message, int statusCode) {
		this.message = message;
		this.statusCode = statusCode;

	}

	public Response(String message) {
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
