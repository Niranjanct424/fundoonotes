package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.IColaboratorService;

@RestController
@CrossOrigin("*")
//@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping
public class ColaboratorController {

	@Autowired
	private IColaboratorService colaboratorService;

	@PostMapping("/addCollaborator")
	public ResponseEntity<Response> addColaborator(@RequestParam String email, @RequestParam Long noteId,
			@RequestHeader String token) {
		Note collaboratedNote = colaboratorService.addColaborator(token, noteId, email);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("colaborator added.", 202, collaboratedNote));
		
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//				.body(new Response("Opps...Error adding colborator!", 400));

	}
//	@RequestHeader("token") String token,
//	@PathVariable("noteId") long noteId, @RequestParam("emailId"

	@GetMapping("/getCollaborators")
	public ResponseEntity<Response> getColaborators(@RequestHeader String token,
			@RequestParam long noteId) {
		List<User> fetchedColaboratorsNotes = colaboratorService.getColaboratorsOfNote(token, noteId);
		if (!fetchedColaboratorsNotes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("colaborators : ",200, fetchedColaboratorsNotes));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(new Response("This Note dont have any Collaborators", HttpStatus.NOT_FOUND));
	}

	@DeleteMapping("/deleteCollaborator")
	public ResponseEntity<Response> deleteColaborator(@RequestParam long noteId , @RequestParam String email , @RequestHeader String token) 
	{
		Note collaboratedNote = colaboratorService.removeColaborator(token, noteId, email);
		if(collaboratedNote!= null)
		{
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Response("colaborator removed.", 202, collaboratedNote));

		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...Error removing Colaborator!", 400,collaboratedNote));
	}
}
