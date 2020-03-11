package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.RemainderDto;
import com.bridgelabz.fundoonotes.model.Note;

public interface INoteService {
	
	public boolean createNote(NoteDto noteDto, String token);


	public boolean updateNote(NoteDto noteDto, long noteId, String token);

	
	public boolean deleteNote(long noteId, String token);

	
	public boolean archieveNote(long noteId, String token);

	
	public boolean isPinnedNote(long noteId, String token);

	
	public boolean trashNote(long noteId, String token);

	
	public List<Note> getallNotes(String token);

	
	public List<Note> getAllTrashedNotes(String token);

	
	public List<Note> getAllPinnedNotes(String token);

	
	public List<Note> getAllArchivedNotes(String token);


	public void changeColour(String token, long noteId, String noteColor);


	public void setRemainderforNote(String token, long noteId, RemainderDto remainderDTO);


	public void removeRemainderforNote(String token, long noteId);


	public List<Note> searchByTitle(String token, String noteTitle);

	

}
