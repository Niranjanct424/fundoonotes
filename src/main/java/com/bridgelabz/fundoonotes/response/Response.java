package com.bridgelabz.fundoonotes.response;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.LoginDto;

public class Response {

	private String message;
	private int statusCode;
	@SuppressWarnings("unused")
	private LoginDto dto;

	private Object object;

	List<String> details;

	/**
	 * Constructor to fetch response if exist any.
	 * 
	 * @param message    as String input parameter
	 * @param statusCode as integer input parameter
	 * @param object     as User Object
	 */
	public Response(String message, int statusCode, Object object) {
		this.message = message;
		this.statusCode = statusCode;
		this.object = object;
	}

	public Response(String message) {
		this.message = message;

	}

	public Response(String message, int statusCode) {
		this.message = message;
		this.statusCode = statusCode;

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
