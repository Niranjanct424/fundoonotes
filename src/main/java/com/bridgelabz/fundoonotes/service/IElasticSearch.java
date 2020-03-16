package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.model.Note;

public interface IElasticSearch {
	
	public String createNote(Note noteInfo);
	
	public String updateNote(Note noteInfo);
	
	public String deleteNote(Note noteInfo);
	
	List<Note> searchByTitle(String title);

}
