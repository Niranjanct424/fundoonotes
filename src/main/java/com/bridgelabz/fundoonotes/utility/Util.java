package com.bridgelabz.fundoonotes.utility;

import org.springframework.http.HttpStatus;

/**
 * @author Niranjan c.t
 * @version 1.0
 * @Date : 29-02-2019
 */
public class Util {
	/**
	 * These are the constants which we have declared having {@link HttpStatus}:
	 * http codes and string messages
	 */
	public static final String EMAIL_REGEX_PATTERN = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	public static final int OK_RESPONSE_CODE = 200;
	public static final int CREATED_RESPONSE_CODE = 201;
	public static final int ALREADY_EXIST_EXCEPTION_STATUS = 208;
	public static final int NOTE_NOT_FOUND_EXCEPTION_STATUS = 300;
	public static final int BAD_REQUEST_RESPONSE_CODE = 400;
	public static final int USER_AUTHENTICATION_EXCEPTION_STATUS = 401;
	public static final int NOT_FOUND_RESPONSE_CODE = 404;	
	public static final String NO_NOTES_FOUND_MESSAGE = "Opps...No notes Found!";
	public static final String REGISTRATION_EMAIL_SUBJECT = "Registration Verification Link from RabbitMq";
	public static final String IP_ADDRESS = "http://localhost:";
	public static final String REGESTATION_VERIFICATION_LINK = "/user/verification";
	public static final String USER_NOT_FOUND_EXCEPTION_MESSAGE = "User not found!";
	public static final String NOTE_NOT_FOUND_EXCEPTION_MESSAGE = "Note not found!";
	public static final String LABEL_NOT_FOUND_EXCEPTION_MESSAGE = "Label not found!";
	public static final String USER_AUTHORIZATION_EXCEPTION_MESSAGE = "Authorization failed!";
	public static final String LABEL_ALREADY_EXIST_EXCEPTION_MESSAGE = "Label already exist!";
	// environment variable
	public static final String SENDER_EMAIL_ID = System.getenv("email");
	public static final String SENDER_PASSWORD = System.getenv("password");

	/**
	 * preventing from creating instance of the class
	 */
	private Util() {

	}

	/**
	 * this function just concatenate two parameter.
	 * 
	 * @param url   as String input parameter
	 * @param token as String input parameter
	 * @return String
	 */
	public static String createLink(String url, String token) {

		return url + "/" + token;
	}
	


}
