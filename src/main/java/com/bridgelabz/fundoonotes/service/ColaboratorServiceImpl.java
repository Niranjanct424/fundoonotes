package com.bridgelabz.fundoonotes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.exception.AuthorizationException;
import com.bridgelabz.fundoonotes.exception.ColaboratorException;
import com.bridgelabz.fundoonotes.exception.EmailSentFailedException;
import com.bridgelabz.fundoonotes.exception.NoteNotFoundException;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utility.EmailService;
import com.bridgelabz.fundoonotes.utility.JWTToken;
import com.bridgelabz.fundoonotes.utility.Util;

@Service
public class ColaboratorServiceImpl implements IColaboratorService {

	@Autowired
	JWTToken jwtToken;

	@Autowired
	UserRepository userRepository;

	@Autowired
	NoteRepository noteRepository;
	@Autowired
	EmailService emilService;

	public User authenticateUser(String token) {
		User fetcheduser = userRepository.getUser(jwtToken.decodeToken(token));
		if (fetcheduser != null) {
			return fetcheduser;
		}
		throw new AuthorizationException("Faild to authenticate user", 401);

	}

	private Note verifyNote(long noteId) {
		Note fetchedNote = noteRepository.getNote(noteId);
		if (fetchedNote != null) {
			if (!fetchedNote.isTrashed()) {
				return fetchedNote;
			}
			throw new NoteNotFoundException("Note not found i think it's trashed", 404);
		}
		throw new NoteNotFoundException("Note Not found ", 404);
	}

	private User validColaborator(String emailId) {
		User fetchedColaborator = userRepository.getUser(emailId);
		if (fetchedColaborator != null && fetchedColaborator.isVerified()) {
			return fetchedColaborator;
		}
		throw new ColaboratorException("Opps...Colaborator is not valid user!", 404);
	}

	@Override
	public boolean addColaborator(String token, long noteId, String emailId) {
		if (authenticateUser(token).getEmailId().equals(emailId)) {
			throw new ColaboratorException("Opps...Can't add own account as colaborator", 400);
		}
		Note fetchedValidNote = verifyNote(noteId);
		User fetchedValidColaborator = validColaborator(emailId);
		fetchedValidNote.getColaboratedUsers().add(fetchedValidColaborator);
		fetchedValidColaborator.getColaboratedNotes().add(fetchedValidNote);
		userRepository.save(fetchedValidColaborator);
		
		String emailBodyContaintLink = Util.createLink("http://localhost:8080/User/Verification",
				jwtToken.createJwtToken(fetchedValidColaborator.getUserId()));

		if (emilService.sendMail(fetchedValidColaborator.getEmailId(), "Note colaboration", emailBodyContaintLink))
			return true;
			
		else
			throw new EmailSentFailedException("Opps...Error sending  mail! to colaborator");
		
	}

	@Override
	public List<User> getColaboratorsOfNote(String token, long noteId) {
		authenticateUser(token);
		return verifyNote(noteId).getColaboratedUsers();
	}

	
	@Override
	public boolean removeColaborator(String token, Long noteId, String emailId) {
		authenticateUser(token);
		Note fetchedValidNote = verifyNote(noteId);
		User fetchedValidColaborator = validColaborator(emailId);
		fetchedValidNote.getColaboratedUsers().remove(fetchedValidColaborator);
		fetchedValidColaborator.getColaboratedNotes().remove(fetchedValidNote);
		userRepository.save(fetchedValidColaborator);
		return true;
	}

}
