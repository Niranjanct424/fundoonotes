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

	/**
	 * @Desciption:this method takes noteId as input parameter and delete the Note
	 * @param noteId as input parameter
	 * @return boolean value
	 */
	public boolean isDeletedNote(long noteId);

	/**
	 * @Desciption:this method takes noteId as input parameter and fetching all
	 *                  user's note which are not trashed and archived from database
	 *                  by taking noteId as input parameter.
	 * @param noteId as input parameter
	 * @return boolean value
	 */
	public List<Note> getAllNotes(long userId);

	/**
	 * @Desciption:this method takes noteId as input parameter and fetching all
	 *                  user's note which are trashed from database.
	 * @param noteId as input parameter
	 * @return List of Notes.
	 */

	public List<Note> getAllTrashedNotes(long userId);

	/**
	 * @Desciption:This method takes userId as input parameter and gets all the
	 *                  user's note which are pinned from database.
	 * @param userId as Long input parameter
	 * @return List<Note>
	 */
	public List<Note> getAllPinnedNotes(long userId);

	/**
	 * @Desciption:This method gets all user's note which are archived from database
	 *                  by taking noteId as input parameter.
	 * @param userId
	 * @return List<Note>
	 */
	public List<Note> getAllArchivedNotes(long userId);
	
	/**
	 * @Desciption:This method takes String type note title as parameter returns the Note.
	 * @param userId
	 * @return List<Note>
	 */
	public List<Note> searchBy(String noteTitle);
}
