package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.RemainderDto;
import com.bridgelabz.fundoonotes.model.Note;

public class NoteServiceImplementation implements AbstractNoteService {

	@Override
	public boolean createNote(NoteDto noteDto, String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateNote(NoteDto noteDto, long noteId, String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteNote(long noteId, String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean archieveNote(long noteId, String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isPinnedNote(long noteId, String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean trashNote(long noteId, String token) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Note> getallNotes(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Note> getAllTrashedNotes(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Note> getAllPinnedNotes(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Note> getAllArchivedNotes(String token) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changeColour(String token, long noteId, String noteColor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setRemainderforNote(String token, long noteId, RemainderDto remainderDTO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeRemainderforNote(String token, long noteId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Note> searchByTitle(String token, String noteTitle) {
		// TODO Auto-generated method stub
		return null;
	}

}
