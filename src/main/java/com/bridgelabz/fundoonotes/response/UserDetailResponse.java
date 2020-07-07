package com.bridgelabz.fundoonotes.response;

import com.bridgelabz.fundoonotes.dto.LoginDto;

/**
 * 
 * @author Niranjan c.t
 * @version 1.0
 * @Date : 29-02-2019
 */

public class UserDetailResponse {

	private String tokenCode;
	private int statusCode;
	private LoginDto loginInformation;
	private String message;
	private String token;
	private String name;

	public UserDetailResponse(String tokenCode, int statusCode, LoginDto loginInformation) {
		this.tokenCode = tokenCode;
		this.statusCode = statusCode;
		this.loginInformation = loginInformation;
	}
	
	public UserDetailResponse(String message, int statusCode, String token, String name) {
		this.message = message;
		this.statusCode = statusCode;
		this.token = token;
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Constructor takes token code and status code as input parameter
	 */
	public UserDetailResponse(String tokenCode, int statusCode) {
		this.tokenCode = tokenCode;
		this.statusCode = statusCode;
	}

	/**
	 * super class non parameterized constructor
	 */
	public UserDetailResponse() {
		super();

	}

	public String getTokenCode() {
		return tokenCode;
	}

	public void setTokenCode(String tokenCode) {
		this.tokenCode = tokenCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public LoginDto getLoginInformation() {
		return loginInformation;
	}

	public void setLoginInformation(LoginDto loginInformation) {
		this.loginInformation = loginInformation;
	}

}
