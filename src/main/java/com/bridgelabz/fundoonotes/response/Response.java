package com.bridgelabz.fundoonotes.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.bridgelabz.fundoonotes.dto.LoginDto;

import lombok.Data;

@Data
public class Response {

	private String message;
	private Object object;
	private String token;
	
	private int statusCode;
	private HttpStatus httpStatus;
	private LoginDto dto;

	

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
	public Response(String message, Object object) {
		super();
		this.message = message;
		this.object = object;
	}


	public Response(String message) {
		this.message = message;

	}

	public Response(String message, int statusCode) {
		this.message = message;
		this.statusCode = statusCode;

	}
	
	public Response(String message, HttpStatus statusCode) {
		this.message = message;
		httpStatus = statusCode;

	}
	public Response(String message , String token) {
		this.message = message;
		this.token = token;

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
