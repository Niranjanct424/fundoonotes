package com.bridgelabz.fundoonotes.dto;

import javax.validation.constraints.Email;

public class LoginDto {

	private String name;
	@Email
	private String emailId;
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
