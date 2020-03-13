package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LoginDto;
import com.bridgelabz.fundoonotes.dto.RegisterDto;
import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.exception.EmailSentFailedException;
import com.bridgelabz.fundoonotes.exception.RemainderException;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utility.JWTToken;

/**
 * 
 * @author Niranjan c.t
 * @version 1.0
 * @Date : 29-02-2019
 */

@RestController
@RequestMapping
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private JWTToken jwtToken;

	@SuppressWarnings("unchecked")
	@PostMapping("User/Registration")
	public ResponseEntity<Response> registration(@RequestBody RegisterDto newUserDTO) {
		boolean resultStatus = userService.register(newUserDTO);
		if (!resultStatus) {
			// throw new UserException("User already Registered", 208);
			return (ResponseEntity<Response>) ResponseEntity.status(HttpStatus.ALREADY_REPORTED);
		}
		// throw new UserException("Registertration done successfully", 200);
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("registration successful", 200));
	}

	@GetMapping("User/Verification/{token}")
	public ResponseEntity<Response> VerifyRegisterUser(@PathVariable("token") String token) {
		if (userService.isVerifiedUserToken(token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Verification successful"));
		}
		// throw new UserException("Verification failed", 502);
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Response("Verification Failed"));

	}

	@PostMapping("User/Login")
	public ResponseEntity<Response> login(@RequestBody LoginDto loginDto) {
		User userdatafromdb = userService.login(loginDto);
		// checking user exist
		if (userdatafromdb != null) {
			String newToken = jwtToken.createJwtToken(userdatafromdb.getUserId());
			System.out.println("Token : " + newToken);
			return ResponseEntity.status(HttpStatus.OK).header(newToken).body(new Response("Login Successful ", 200));
		}
		// throw new EmailSentFailedException("check mail for verification");
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("check mail for verification"));

	}

	@PostMapping("User/ForgotPassword")
	public ResponseEntity<Response> forgotPassword(@RequestParam("emailId") String emailId) {
		boolean userEmail = userService.isUserExist(emailId);
		if (userEmail) {
			return ResponseEntity.status(HttpStatus.FOUND).body(new Response(" user Exist "));
		}
		// throw new RemainderException("Unauthorized user check mail for verification",
		// 401);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Response("Unauthorized user"));
	}

	@PutMapping("User/UpdatePassword/{token}")
	public ResponseEntity<Response> updatedPassword(@PathVariable("token") String token,
			@RequestBody() UpdatePassword upadatePassword) {
		boolean updationStatus = userService.updatePassword(upadatePassword, token);
		if (updationStatus) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("Password updated sucessfully"));
		}
//		throw new RemainderException("failed to update the password", 400);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Response("failed to update the password"));

	}

}
