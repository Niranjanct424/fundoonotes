package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.model.Note;

public interface IElasticSearch {
	
	public void createNote(Note noteInfo);
	
	public void updateNote(Note noteInfo);
	
	public void deleteNote(Note noteInfo);
	
	List<Note> searchByTitle(String title);

}
