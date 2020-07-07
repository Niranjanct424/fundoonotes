package com.bridgelabz.fundoonotes.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LabelDto;
import com.bridgelabz.fundoonotes.exception.AuthorizationException;
import com.bridgelabz.fundoonotes.exception.LabelAlreadyExistException;
import com.bridgelabz.fundoonotes.exception.LabelException;
import com.bridgelabz.fundoonotes.exception.LabelNotFoundException;
import com.bridgelabz.fundoonotes.exception.NoteException;
import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.repository.ILabelRepository;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utility.JWTToken;

@Service
public class LabelServiceImple implements ILabelServices {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NoteRepository noteRepository;

	@Autowired
	private ILabelRepository labelRepository;

	@Autowired
	private JWTToken jwtToken;

	public User userAuthentication(String token) {
		User fetchedUser = userRepository.getUser(jwtToken.decodeToken(token));
		if (fetchedUser != null)
			return fetchedUser;
		throw new AuthorizationException("Authorization faild", 401);

	}

	public Note verifyNote(long noteId) {
		Note note = noteRepository.getNote(noteId);
		if (note != null) {
			return note;
		}
		throw new NoteException("Note Not Found ", 404);

	}

	@SuppressWarnings("null")
	@Override
	public Label createLabel(String token, LabelDto labelDto) {
		User fetchedUser = userAuthentication(token);
		Label fetchedLabel = labelRepository.findOneBylabelName(labelDto.getLabelName());
		if (fetchedLabel == null) {
			Label newLabel = new Label();
			BeanUtils.copyProperties(labelDto, newLabel);
			newLabel.setCreatedDate(LocalDateTime.now());
			fetchedUser.getLabels().add(newLabel);
			labelRepository.save(newLabel);
			return newLabel;
		}
		throw new LabelAlreadyExistException("Label Alreay exist in the database",208);
	}

	@SuppressWarnings("null")
	@Override
	public boolean createLabelAndMap(String token, long noteId, LabelDto labelDto) {
		User fetchedUser = userAuthentication(token);
		Note fetchedNote = verifyNote(noteId);
		Label fetchedLabel = labelRepository.findOneBylabelName(labelDto.getLabelName());
		if (fetchedLabel == null) {
			Label newLabel = new Label();
			BeanUtils.copyProperties(labelDto, newLabel);
			newLabel.setCreatedDate(LocalDateTime.now());
			fetchedUser.getLabels().add(newLabel);
			fetchedNote.getLabelsList().add(newLabel);
			labelRepository.save(newLabel);
			return true;
		}
		throw new LabelAlreadyExistException("Label Alreay exist in the database",208);
	}

	@Override
	public boolean addNoteToLabel(String token, long noteId, long labelId) {
		userAuthentication(token);
		Note fetchedNote = verifyNote(noteId);
		Optional<Label> fetchedLabel = labelRepository.findById(labelId);
	//	System.out.println("inside fetched label ");
		if (fetchedLabel.isPresent()) {
			//System.out.println("fetched label is present is true");
			fetchedNote.getLabelsList().add(fetchedLabel.get());
		//	System.out.println("added");
			labelRepository.save(fetchedLabel.get());
			//System.out.println("done successfully");
			return true;

		}
		throw new LabelAlreadyExistException("label already exist",208);
	}

	@Override
	public boolean removeNoteFromLabel(String token, long noteId, long labelId) {
		userAuthentication(token);
		Note fetchedNote = verifyNote(noteId);
		Optional<Label> fetchedLabel = labelRepository.findById(labelId);
		if (fetchedLabel.isPresent()) {
			fetchedNote.getLabelsList().remove(fetchedLabel.get());
			noteRepository.saveOrUpdate(fetchedNote);
			return true;
		}
		throw new LabelAlreadyExistException("label already exist",208);
	}

	@Override
	public boolean updateLabelName(String token, LabelDto labelDto, long labelId) {
		userAuthentication(token);
		Optional<Label> fetchedLabel = labelRepository.findById(labelId);
		if (fetchedLabel.isPresent()) {
			labelRepository.updateLabelName(labelDto.getLabelName(), fetchedLabel.get().getLabelId());
			return true;
		}

		throw new LabelNotFoundException("Label Not present in the database to update the labelName",404);
	}

	@Override
	public boolean idDeletedLabel(String token, long labelId) {
		userAuthentication(token);
		Optional<Label> fetchedLabel = labelRepository.findById(labelId);
		if (fetchedLabel.isPresent()) {
			labelRepository.delete(fetchedLabel.get());
			return true;
		}
		throw new LabelException("Label not found to delete ",404);
	}
	
	
	@Override
	public boolean deleteLabel(String token, long labelId) {

		userAuthentication(token);
		System.out.println("inside is delete label method");
		Optional<Label> fetchedLabel = labelRepository.findById(labelId);
		System.out.println("label found");
		if (fetchedLabel.isPresent()) {
			labelRepository.delete(fetchedLabel.get());
			System.out.println("label deleted");
			return true;
		}
		throw new LabelException("Label not found to delete ",404);
	}
	
	
//	@Override
//	public boolean isLabelEdited(String token, String labelName, long labelId) {
//		authenticatedUser(token);
//		Optional<Label> fetchedLabel = labelRepository.findById(labelId);
//		if (fetchedLabel.isPresent()) {
//			if (isValidNameForEdit(fetchedLabel, labelName)) {
//				labelRepository.updateLabelName(labelName, fetchedLabel.get().getLabelId());
//				return true;
//			}
//			return false;
//		}
//		throw new LabelException(Util.LABEL_NOT_FOUND_EXCEPTION_MESSAGE, 404);
//	}
//
//	private boolean isValidNameForEdit(Optional<Label> fetchedLabel, String labelName) {
//
//		if (labelRepository.checkLabelWithDb(labelName).isEmpty()) {
//			return !fetchedLabel.get().getLabelName().equals(labelName);
//		}
//		throw new LabelException(Util.LABEL_ALREADY_EXIST_EXCEPTION_MESSAGE, Util.ALREADY_EXIST_EXCEPTION_STATUS);
//	}
	
	

	@Override
	public List<Label> listOfLabels(String token) {
		userAuthentication(token);
		return labelRepository.getAllLabels();
	}

	@Override
	public List<Note> listOfNotesOfLabel(String token, long labelId) {
		userAuthentication(token);
		Optional<Label> fetchedLabel = labelRepository.findById(labelId);
		if (fetchedLabel.isPresent()) {
			return fetchedLabel.get().getNoteList();
		}
		throw new LabelNotFoundException("Label Not found",404);
	}

}
