package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.model.User;

public interface IColaboratorService {

	
	public Note addColaborator(String token, long noteId, String emailId);
	
	public List<User> getColaboratorsOfNote(String token, long noteId);
	
	public Note removeColaborator(String token,Long noteId,String emailId);
	
	

}
