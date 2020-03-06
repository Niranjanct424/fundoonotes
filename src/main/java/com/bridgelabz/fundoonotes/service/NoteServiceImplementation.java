package com.bridgelabz.fundoonotes.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.RemainderDto;
import com.bridgelabz.fundoonotes.exception.AuthorizationException;
import com.bridgelabz.fundoonotes.exception.RemainderException;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utility.JWTToken;

public class NoteServiceImplementation implements AbstractNoteService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private NoteRepository noteRepository;
	@Autowired
	private JWTToken jwtToken;

	private User authenticatedUser(String token) {
		com.bridgelabz.fundoonotes.model.User fetchedUser = userRepository.getUser(jwtToken.decodeToken(token));
		if (fetchedUser != null) {
			return fetchedUser;
		}
		throw new AuthorizationException("Authorization faild", 401);
	}

	private Note verifiedNote(long noteId) {
		Note fetchedNote = noteRepository.getNote(noteId);
		if (fetchedNote != null) {
			return fetchedNote;
		}
		throw new UserException("Note Not Found", 404);
	}

	@Override
	public boolean createNote(NoteDto noteDto, String token) {

		User fetchedUser = authenticatedUser(token);

		if (fetchedUser != null) {
			Note newNote = new Note();
			BeanUtils.copyProperties(noteDto, newNote);
			newNote.setCreatedDate(LocalDateTime.now());
			newNote.setColor("white");
			fetchedUser.getNote().add(newNote);
			noteRepository.saveOrUpdate(newNote);
			return true;
		}
		throw new AuthorizationException("Authorization faild", 401);
	}

	@Override
	public boolean updateNote(NoteDto noteDto, long noteId, String token) {
		// using token we will get the user
		authenticatedUser(token);
		// here we get the existing note
		Note fetchedNote = verifiedNote(noteId);
		BeanUtils.copyProperties(noteDto, fetchedNote);
		fetchedNote.setUpdatedDate(LocalDateTime.now());
		noteRepository.saveOrUpdate(fetchedNote);
		return false;
	}

	/**
	 * using token we will get the user and note is verified using note id then note
	 * will deleted using noteId.
	 */
	@Override
	public boolean deleteNote(long noteId, String token) {
		authenticatedUser(token);
		verifiedNote(noteId);
		noteRepository.isDeletedNote(noteId);
		return true;
	}

	/**
	 * if we found found authorized user then we will verify the Note if the user
	 * note is not archived will archieveNote or its archived before only.
	 */
	@Override
	public boolean archieveNote(long noteId, String token) {
		authenticatedUser(token);
		Note fetchedNote = verifiedNote(noteId);
		// fetched note is not archived
		if (!fetchedNote.isArchived()) {
			fetchedNote.setArchived(true);
			fetchedNote.setUpdatedDate(LocalDateTime.now());
			noteRepository.saveOrUpdate(fetchedNote);
			return true;
		}
		return false;
	}

	/**
	 * We will make the the note pinned if not already pinned we will make it un
	 * pinned in the second time of service called.
	 */
	@Override
	public boolean isPinnedNote(long noteId, String token) {
		authenticatedUser(token);
		// verified valid note
		Note fetchedNote = verifiedNote(noteId);
		if (!fetchedNote.isPinned()) {
			fetchedNote.setPinned(true);
			fetchedNote.setUpdatedDate(LocalDateTime.now());
			noteRepository.saveOrUpdate(fetchedNote);
			return true;
		}
		// making note Unpinned if note already pinned
		fetchedNote.setPinned(false);
		fetchedNote.setUpdatedDate(LocalDateTime.now());
		noteRepository.saveOrUpdate(fetchedNote);
		return false;
	}

	/**
	 * if the note is verified as valid note then will check note is not trashed its
	 * set to trashed or if trashed already returns false
	 */
	@Override
	public boolean trashNote(long noteId, String token) {
		authenticatedUser(token);
		Note fetchedNote = verifiedNote(noteId);
		if (!fetchedNote.isTrashed()) {
			fetchedNote.setTrashed(true);
			fetchedNote.setUpdatedDate(LocalDateTime.now());
			noteRepository.saveOrUpdate(fetchedNote);
			return true;
		}
		return false;
	}

	@Override
	public List<Note> getallNotes(String token) {

		return noteRepository.getAllNotes(authenticatedUser(token).getUserId());
	}

	@Override
	public List<Note> getAllTrashedNotes(String token) {

		List<Note> trashedNotes = noteRepository.getAllTrashedNotes(authenticatedUser(token).getUserId());
		if (!trashedNotes.isEmpty()) {
			return trashedNotes;
		}
		return trashedNotes;
	}

	@Override
	public List<Note> getAllPinnedNotes(String token) {
		List<Note> pinnedNotes = noteRepository.getAllPinnedNotes(authenticatedUser(token).getUserId());
		if (!pinnedNotes.isEmpty()) {
			return pinnedNotes;
		}
		return null;
	}

	@Override
	public List<Note> getAllArchivedNotes(String token) {
		List<Note> archivedNotes = noteRepository.getAllArchivedNotes(authenticatedUser(token).getUserId());
		if (!archivedNotes.isEmpty()) {
			return archivedNotes;
		}
		return archivedNotes;
	}

	@Override
	public void changeColour(String token, long noteId, String noteColor) {
		authenticatedUser(token);
		Note fetchedNote = verifiedNote(noteId);
		fetchedNote.setColor(noteColor);
		fetchedNote.setUpdatedDate(LocalDateTime.now());
		noteRepository.saveOrUpdate(fetchedNote);
	}

	@Override
	public void setRemainderforNote(String token, long noteId, RemainderDto remainderDTO) {
		authenticatedUser(token);
		// validate note
		Note fetchedNote = verifiedNote(noteId);
		if (fetchedNote.getReminderDate() == null) {
			fetchedNote.setUpdatedDate(LocalDateTime.now());
			fetchedNote.setReminderDate(remainderDTO.getRemainder());
			noteRepository.saveOrUpdate(fetchedNote);
			return;
		}
		throw new RemainderException("Opps...Remainder already set!", 502);

	}

	@Override
	public void removeRemainderforNote(String token, long noteId) {
		authenticatedUser(token);
		// validate note
		Note fetchedNote = verifiedNote(noteId);
		if (fetchedNote.getReminderDate() != null) {
			fetchedNote.setReminderDate(null);
			fetchedNote.setUpdatedDate(LocalDateTime.now());
			noteRepository.saveOrUpdate(fetchedNote);
			return;
		}
		throw new RemainderException("Opps...Remainder already removed!", 502);
	}

	@Override
	public List<Note> searchByTitle(String token, String noteTitle) {
		authenticatedUser(token);
		List<Note> fetchedNote = noteRepository.searchBy(noteTitle);
		if (!fetchedNote.isEmpty()) {
			return fetchedNote;

		}
		throw new com.bridgelabz.fundoonotes.exception.NoteException("Note Not Found Exception", 502);

	}

}
