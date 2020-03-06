package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.AbstractNoteService;

@RestController
public class NoteController {
	
	@Autowired
	private AbstractNoteService noteService;
	
	public ResponseEntity<Response> createNote(NoteDto noteDto,@RequestHeader("token") String token)
	{
		
	}

}
