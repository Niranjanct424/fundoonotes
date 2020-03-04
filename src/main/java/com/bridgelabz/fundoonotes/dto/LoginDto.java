package com.bridgelabz.fundoonotes.dto;

import javax.validation.constraints.Email;

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
public class LoginDto {

	@Email
	private String emailId;
	private String password;




}
