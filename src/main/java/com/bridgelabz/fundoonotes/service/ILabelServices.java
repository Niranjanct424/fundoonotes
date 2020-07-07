package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;    

public interface ILabelServices {

	public Label createLabel(String token, LabelDto labelDto);

	public boolean createLabelAndMap(String token, long noteId, LabelDto labelDto);

	public boolean addNoteToLabel(String token, long noteId, long labelId);
	
	public boolean removeNoteFromLabel(String token, long noteId, long labelId);

	public boolean updateLabelName(String token, LabelDto labelDto, long labelId);

	public boolean deleteLabel(String token, long labelId);

	public List<Label> listOfLabels(String token);

	public List<Note> listOfNotesOfLabel(String token, long labelId);

	boolean idDeletedLabel(String token, long labelId);

}
