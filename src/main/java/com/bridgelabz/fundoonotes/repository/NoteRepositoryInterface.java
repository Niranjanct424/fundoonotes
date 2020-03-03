package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import com.bridgelabz.fundoonotes.model.Note;

public interface NoteRepositoryInterface {

	/**
	 * @Desciption:this method takes Note as input parameter stores note in database
	 * @param newNote as input parameter
	 * @return Note object
	 */
	public Note saveOrUpdate(Note newNote);

	/**
	 * @Desciption:this method takes noteId as input parameter and gives the note 
	 * @param newNote as input parameter
	 * @return Note object
	 */
	public Note getNote(long noteId);

	
	public boolean isDeletedNote(long noteId);


	public List<Note> getAllNotes(long userId);

	
	public List<Note> getAllTrashedNotes(long userId);

	
	
	public List<Note> getAllPinnedNotes(long userId);


	public List<Note> getAllArchivedNotes(long userId);

	
	public List<Note> searchBy(String noteTitle);
}
