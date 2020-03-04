package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.NoteUpdation;
import com.bridgelabz.fundoonotes.dto.RemainderDto;
import com.bridgelabz.fundoonotes.model.Note;

public interface AbstractNoteService {
	/**
	 * @Description: Note abstract service methods
	 * @param information
	 * @param token
	 */

	public void createNote(NoteDto information, String token);

	public void updateNote(NoteUpdation information, String token);

	public void deleteNote(long id, String token);

	public List<Note> getAllNotes(String token);

	public List<Note> getTrashedNotes(String token);

	boolean deleteNotePemenetly(long id, String token);

	void archievNote(long id, String token);

	public List<Note> getArchiveNote(String token);

	public void addColour(Long noteId, String token, String colour);

	public void addReminder(Long noteId, String token, RemainderDto reminder);

	public void removeReminder(Long noteId, String token, RemainderDto reminder);

	public void pin(long id, String token);

}
