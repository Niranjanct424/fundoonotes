package com.bridgelabz.fundoonotes.response;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * @author Niranjan c.t
 * @version 1.0
 * @Date : 29-02-2019
 */
@Data
public class MailObject implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;

	private String subject;

	private String message;

	public MailObject(String email, String subject, String message) {
		this.email = email;
		this.subject = subject;
		this.message = message;
	}

	



}