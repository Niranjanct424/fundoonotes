package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoonotes.dto.RegisterDto;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping("/registration")
	public ResponseEntity<Response> registration(@RequestBody RegisterDto newUserDTO) {
		boolean resultStatus = userService.register(newUserDTO);
		if (!resultStatus) {
			return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
					.body(new Response("Email already exist", 208, resultStatus));
		}
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("registration successful", 201, resultStatus));
	}

}
