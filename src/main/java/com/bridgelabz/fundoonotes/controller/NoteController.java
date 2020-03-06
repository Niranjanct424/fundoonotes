package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.AbstractNoteService;
import com.bridgelabz.fundoonotes.utility.Util;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("notes")
public class NoteController {

	@Autowired
	private AbstractNoteService noteService;

	@ApiOperation(value = "create a new note for valid user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "note created"),
			@ApiResponse(code = 400, message = "Opps... Error creating note") })
	@PostMapping("Create")
	public ResponseEntity<Response> createNote(@RequestBody NoteDto noteDto, @RequestHeader("token") String token) {
		if (noteService.createNote(noteDto, token)) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("note created", Util.CREATED_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps... Error creating note!", Util.BAD_REQUEST_RESPONSE_CODE));
	}
	
	
	
	@ApiOperation(value = "update an existing note for valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "note updated"),
			@ApiResponse(code = 300, message = "Opps...Note not found!"),
			@ApiResponse(code = 400, message = "Opps...Error updating note!"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!") })
	@PutMapping("update")
	public ResponseEntity<Response> updateNote(@RequestBody NoteDto noteDto, @RequestParam("id") long noteId,
			@RequestHeader("token") String token) {
		if (noteService.updateNote(noteDto, noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note updated", Util.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...Error updating note!", Util.BAD_REQUEST_RESPONSE_CODE));
	}
	
	

	@ApiOperation(value = "delete an existing note for valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "note deleted"),
			@ApiResponse(code = 300, message = "Opps...Note not found!"),
			@ApiResponse(code = 400, message = "Opps...Error deleting note!"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!") })
	@DeleteMapping("{id}/delete")
	public ResponseEntity<Response> deleteNote(@PathVariable("id") long noteId, @RequestHeader("token") String token) {
		if (noteService.deleteNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note deleted", Util.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(new Response("Opps...Error deleting note!", Util.BAD_REQUEST_RESPONSE_CODE));

	}
	
	
	


}
