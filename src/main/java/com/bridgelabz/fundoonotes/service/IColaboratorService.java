package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.model.User;

public interface IColaboratorService {

	
	public boolean addColaborator(String token, long noteId, String emailId);
	
	public List<User> getColaboratorsOfNote(String token, long noteId);
	
	public boolean removeColaborator(String token,Long note);
	
	

}
