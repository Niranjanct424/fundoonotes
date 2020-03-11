package com.bridgelabz.fundoonotes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.bridgelabz.fundoonotes.response.Response;

@RestControllerAdvice
public class GlobalResponseExceptionHandler extends ResponseEntityExceptionHandler {
	
	
	@ExceptionHandler({ UserException.class, RemainderException.class, EmailSentFailedException.class })
	public ResponseEntity<Response> handleAllUserException(Exception exception) {
		return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(new Response(exception.getMessage(), 502));
	}
	
	@ExceptionHandler(NoteException.class)
	public ResponseEntity<Response> handleAllNoteException(NoteException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response(exception.getMessage(), exception.getStatus()));
	}
	
	@ExceptionHandler(AuthorizationException.class)
	public ResponseEntity<Response> handleAllNoteException(AuthorizationException authorizationException) {
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
				.body(new Response(authorizationException.getMessage(), authorizationException.getStatus()));
	}
	
	@ExceptionHandler(InvalidCredentialException.class)
	public ResponseEntity<Response> handelAllInvalidCredentialException(
			InvalidCredentialException invalidCredentialsException) {
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response(invalidCredentialsException.getMessage(), invalidCredentialsException.getStatus()));
	}
	
	@ExceptionHandler(LabelAlreadyExistException.class)
	public ResponseEntity<Response> handleAllLabelException(LabelAlreadyExistException labelException) {
		return ResponseEntity.status(HttpStatus.ALREADY_REPORTED)
				.body(new Response(labelException.getMessage(), labelException.getStatus()));
	}
	
	@ExceptionHandler(ColaboratorException.class)
	public ResponseEntity<Response> handleAllColaboratorException(ColaboratorException exception) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response(exception.getMessage(), exception.getStatus()));
	}


}
