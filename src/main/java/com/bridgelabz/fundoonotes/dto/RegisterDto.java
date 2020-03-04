package com.bridgelabz.fundoonotes.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
 * @author Niranjan c.t
 * @version 1.0
 * @Date : 29-02-2019
 */
@Data
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

	

}
