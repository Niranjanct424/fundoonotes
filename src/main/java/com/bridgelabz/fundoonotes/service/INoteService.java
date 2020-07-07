package com.bridgelabz.fundoonotes.service;

import java.time.LocalDateTime;
import java.util.List;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.RemainderDto;
import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;

public interface INoteService {
	
	public Note createNote(NoteDto noteDto, String token);


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


//	public void setRemainderforNote(String token, long noteId, RemainderDto remainderDTO);


	public void removeRemainderforNote(String token, long noteId);


	public List<Note> searchByTitle(String token, String noteTitle);


	List<Note> fetchAllNotes(String token);


	List<Label> getLabelsOfNote(String token, long noteId);


	boolean restoreNote(long noteId, String token);


	List<Note> getAllRemaindersNotes(String token);


	void setRemainderforNote1(String token, long noteId, RemainderDto remainderDTO);


//	void setRemainderforNotenn(String token, long noteId, LocalDateTime reminderDate);

	

}
