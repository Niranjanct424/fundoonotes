package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.bridgelabz.fundoonotes.dto.RemainderDto;
import com.bridgelabz.fundoonotes.exception.NoteException;
import com.bridgelabz.fundoonotes.exception.NoteNotFoundException;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.INoteService;
import com.bridgelabz.fundoonotes.utility.Util;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("notes")
public class NoteController {

	/**
	 * @author Niranjan c.t
	 * @created 7-3-2020
	 * @version 1.0
	 * @Description: Note controller APIs
	 */

	@Autowired
	private INoteService noteService;

	@ApiOperation(value = "create a new note for valid user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "note created"),
			@ApiResponse(code = 400, message = "Opps... Error creating note") })
	@PostMapping("Create")
	public ResponseEntity<Response> createNote(@RequestBody NoteDto noteDto, @RequestHeader("token") String token) {
		if (noteService.createNote(noteDto, token)) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("note created", Util.CREATED_RESPONSE_CODE));
		}
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//				.body(new Response("Opps... Error creating note!", Util.BAD_REQUEST_RESPONSE_CODE));
		
		throw new NoteException("Opps... Error creating note!", 400);
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
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//				.body(new Response("Opps...Error updating note!", Util.BAD_REQUEST_RESPONSE_CODE));
		throw new NoteException("Opps...Error updating note!", 502);
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
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//				.body(new Response("Opps...Error deleting note!", Util.BAD_REQUEST_RESPONSE_CODE));
		throw new NoteException("Opps...Error deleting note!", 502);

	}

	@ApiOperation(value = "archive an existing note for valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "note archived"),
			@ApiResponse(code = 300, message = "Opps...Note not found!"),
			@ApiResponse(code = 400, message = "Opps...Already archived!"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!") })
	@PatchMapping("{id}/archieve")
	public ResponseEntity<Response> archieveNote(@PathVariable("id") long noteId,
			@RequestHeader("token") String token) {
		if (noteService.archieveNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note archieved", Util.OK_RESPONSE_CODE));
		}
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//				.body(new Response("Opps...Already archived!", Util.BAD_REQUEST_RESPONSE_CODE));
		throw new NoteException("Opps...Already archived!", 502);
		
	}

	@ApiOperation(value = "pin/unpin operation of existing note for valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "note pinned"),
			@ApiResponse(code = 201, message = "note unpinned"),
			@ApiResponse(code = 300, message = "Opps...Note not found!"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!") })
	@PatchMapping("{id}/pin")
	public ResponseEntity<Response> pinNote(@PathVariable("id") long noteId, @RequestHeader("token") String token) {
		if (noteService.isPinnedNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note pinned", Util.OK_RESPONSE_CODE));
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(new Response("note unpinned", Util.OK_RESPONSE_CODE));
	}

	@ApiOperation(value = "trash operation for an existing note for valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "note pinned"),
			@ApiResponse(code = 300, message = "Opps...Note not found!"),
			@ApiResponse(code = 400, message = "Opps...Already trashed!"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!") })
	@DeleteMapping("{id}/trash")
	public ResponseEntity<Response> trashNote(@PathVariable("id") long noteId, @RequestHeader("token") String token) {
		if (noteService.trashNote(noteId, token)) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("note trashed", Util.OK_RESPONSE_CODE));
		}
//		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//				.body(new Response("Opps...Already trashed!", Util.BAD_REQUEST_RESPONSE_CODE));
		throw new NoteException("Opps...Already trashed!", 502);
	}

	@GetMapping("fetch/notes")
	public ResponseEntity<Response> getAllNotes(@RequestHeader String token) {
		List<Note> notes = noteService.getallNotes(token);
		if (!notes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(new Response("found", 200, notes));
		}
//		return ResponseEntity.status(HttpStatus.NOT_FOUND)
//				.body(new Response(Util.NO_NOTES_FOUND_MESSAGE, Util.NOT_FOUND_RESPONSE_CODE));
		throw new NoteNotFoundException("Opps...Note Not Found!", 404);
	}

	@ApiOperation(value = "fetch all trashed notes for valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Trashed notes are"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!"),
			@ApiResponse(code = 404, message = "Opps...No notes Found!") })
	@GetMapping("fetch/notes/trashed")
	public ResponseEntity<Response> fetchAllTrashedNotes(@RequestHeader("token") String token) {
		List<Note> trashedNotes = noteService.getAllTrashedNotes(token);
		if (!trashedNotes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("Trashed notes are", Util.OK_RESPONSE_CODE, trashedNotes));
		}
//		return ResponseEntity.status(HttpStatus.NOT_FOUND)
//				.body(new Response(Util.NO_NOTES_FOUND_MESSAGE, Util.NOT_FOUND_RESPONSE_CODE));
		throw new NoteNotFoundException("Opps...Trashed Notes Not Found!", 404);
	}

	@ApiOperation(value = "fetch all pinned notes for valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Pinned notes are"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!"),
			@ApiResponse(code = 404, message = "Opps...No notes Found!") })
	@GetMapping("fetch/notes/pinned")
	public ResponseEntity<Response> fetchAllPinnedNotes(@RequestHeader("token") String token) {

		List<Note> pinnedNotes = noteService.getAllPinnedNotes(token);
		if (!pinnedNotes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("Pinned notes are", Util.OK_RESPONSE_CODE, pinnedNotes));
		}
//		return ResponseEntity.status(HttpStatus.NOT_FOUND)
//				.body(new Response(Util.NO_NOTES_FOUND_MESSAGE, Util.NOT_FOUND_RESPONSE_CODE));
		throw new NoteNotFoundException("Opps...Pinned Notes Not Found!", 404);
	}

	@ApiOperation(value = "fetch all archived notes for valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Archived notes are"),
			@ApiResponse(code = 300, message = "Opps...Note not found!"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!"),
			@ApiResponse(code = 404, message = "Opps...No notes Found!") })
	@GetMapping("fetch/notes/archived") 
	public ResponseEntity<Response> fetchAllArchivedNotes(@RequestHeader("token") String token) {

		List<Note> archivedNotes = noteService.getAllArchivedNotes(token);
		if (!archivedNotes.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK)
					.body(new Response("Archived notes are", Util.OK_RESPONSE_CODE, archivedNotes));
		}
//		return ResponseEntity.status(HttpStatus.NOT_FOUND)
//				.body(new Response(Util.NO_NOTES_FOUND_MESSAGE, Util.NOT_FOUND_RESPONSE_CODE));
		throw new NoteNotFoundException("Opps...Archived Notes Not Found!", 404);
	}

	@ApiOperation(value = "change color of a note for valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Pinned notes are"),
			@ApiResponse(code = 300, message = "Opps...Note not found!"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!") })
	@PostMapping("{id}/colour")
	public ResponseEntity<Response> changeColour(@RequestHeader("token") String token, @PathVariable("id") long noteId,
			@RequestParam("color") String noteColour) {
		noteService.changeColour(token, noteId, noteColour);
		return ResponseEntity.status(HttpStatus.OK).body(new Response("color changed", Util.OK_RESPONSE_CODE));
		
	}

	@ApiOperation(value = "set remainder for a note of valid user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "remainder created"),
			@ApiResponse(code = 300, message = "Opps...Note not found!"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!"),
			@ApiResponse(code = 502, message = "Opps...Remainder already set!") })
	@PutMapping("{id}/remainder/add")
	public ResponseEntity<Response> setRemainder(@RequestHeader("token") String token, @PathVariable("id") long noteId,
			@RequestBody RemainderDto remainderDto) {
		noteService.setRemainderforNote(token, noteId, remainderDto);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("remainder created", Util.CREATED_RESPONSE_CODE));

	}

	@ApiOperation(value = "remove remainder for a note of valid user")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "remainder removed"),
			@ApiResponse(code = 300, message = "Opps...Note not found!"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!"),
			@ApiResponse(code = 502, message = "Opps...Remainder already removed!") })
	@DeleteMapping("{id}/remainder/remove")
	public ResponseEntity<Response> removeRemainder(@RequestHeader("token") String token,
			@PathVariable("id") long noteId) {
		noteService.removeRemainderforNote(token, noteId);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("remainder removed", Util.CREATED_RESPONSE_CODE));
	}

	@ApiOperation(value = "search operation for note based on title of valid user")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "found notes"),
			@ApiResponse(code = 300, message = "Opps...Note not found!"),
			@ApiResponse(code = 401, message = "Opps...Authorization failed!") })
	@GetMapping("search")
	public ResponseEntity<Response> searchByTitle(@RequestHeader("token") String token,
			@RequestParam("title") String noteTitle) {
		List<Note> fetchedNotes = noteService.searchByTitle(token, noteTitle);
		return ResponseEntity.status(HttpStatus.OK)
				.body(new Response("found notes", Util.OK_RESPONSE_CODE, fetchedNotes));
	}

}
