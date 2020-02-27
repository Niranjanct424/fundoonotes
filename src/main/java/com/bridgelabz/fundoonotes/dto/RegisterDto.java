package com.bridgelabz.fundoonotes.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

@Component
public class RegisterDto {

	@NotNull
	private String name;
	@NotNull
	private String password;
	@NotNull()
	private String mobileNumber;
	@Email
	private String emailId;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	@Override
	public String toString() {
		return "RegisterDto [name=" + name + ", password=" + password + ", mobileNumber=" + mobileNumber + ", emailId="
				+ emailId + "]";
	}

}
