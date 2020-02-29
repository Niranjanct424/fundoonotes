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

	public UserDetailResponse(String tokenCode, int statusCode, LoginDto loginInformation) {
		this.tokenCode = tokenCode;
		this.statusCode = statusCode;
		this.loginInformation = loginInformation;
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
