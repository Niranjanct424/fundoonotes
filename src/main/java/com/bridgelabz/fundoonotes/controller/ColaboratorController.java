package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.IColaboratorService;

@RestController
@RequestMapping("colaborators")
public class ColaboratorController {

	@Autowired
	private IColaboratorService colaboratorService;

	@PostMapping("/{noteId}")
	public ResponseEntity<Response> addColaborator(@RequestHeader("token") String token,
			@PathVariable("noteId") long noteId, @RequestParam("emailId") String emailId) {
		if (colaboratorService.addColaborator(token, noteId, emailId)) {
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("colaborator added.", 202, emailId));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...Error adding colborator!", 400));

	}

	@GetMapping("/{noteId}")
	public ResponseEntity<Response> getColaborators(@RequestHeader("token") String token,
			@PathVariable("noteId") long noteId) {
		List<User> fetchedColaborators = colaboratorService.getColaboratorsOfNote(token, noteId);
		if (!fetchedColaborators.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("colaborators : ",200, fetchedColaborators));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response("Notes not found", HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("/{noteId}")
	public ResponseEntity<Response> deleteColaborator(@RequestHeader("token") String token,
			@PathVariable("noteId") long noteId, @RequestParam("emailId") String emailId) {
		if (!colaboratorService.removeColaborator(token, noteId, emailId)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
					.body(new Response("Opps...Error removing Colaborator!", 400));
		}
		return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("colaborator removed.", 202, emailId));
	}
}
