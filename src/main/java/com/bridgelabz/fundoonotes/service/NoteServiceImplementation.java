package com.bridgelabz.fundoonotes.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.NoteDto;
import com.bridgelabz.fundoonotes.dto.RemainderDto;
import com.bridgelabz.fundoonotes.exception.AuthorizationException;
import com.bridgelabz.fundoonotes.exception.NoteException;
import com.bridgelabz.fundoonotes.exception.RemainderException;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.model.Label;
import com.bridgelabz.fundoonotes.model.Note;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utility.JWTToken;

@Service
public class NoteServiceImplementation implements INoteService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private NoteRepository noteRepository;
	@Autowired
	private JWTToken jwtToken;
	@Autowired
	ElasticSearchImpl elasticSearchImpl;

	private User authenticatedUser(String token) {
		User fetchedUser = userRepository.getUser(jwtToken.decodeToken(token));
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
		throw new UserNotFoundException("Note Not Found");
	}

	@Override
	public Note createNote(NoteDto noteDto, String token) {

		User fetchedUser = authenticatedUser(token);

		if (fetchedUser != null) {
			Note newNote = new Note();
			BeanUtils.copyProperties(noteDto, newNote);
			newNote.setCreatedDate(LocalDateTime.now());
			newNote.setColor("white");
//			newNote.setArchived(false);
//			newNote.setUpdatedDate(LocalDateTime.now());
			fetchedUser.getNotes().add(newNote);
			noteRepository.saveOrUpdate(newNote);
//			try {
//				elasticSearchImpl.createNote(newNote);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
			return newNote;
		}
		throw new AuthorizationException("Note created faild", 401);
	}

	@Override
	public boolean updateNote(NoteDto noteDto, long noteId, String token) {
		// using token we will get the user
		authenticatedUser(token);
		System.out.println("user verified ");
		// here we get the existing note
		Note fetchedNote = verifiedNote(noteId);
		System.out.println("user note fetched ");
		BeanUtils.copyProperties(noteDto, fetchedNote);
		System.out.println("user info copied ");
		fetchedNote.setUpdatedDate(LocalDateTime.now());
		System.out.println("user detials updated ");
		noteRepository.saveOrUpdate(fetchedNote);
		System.out.println("user Note updatation successful ");
//		try {
//			elasticSearchImpl.updateNote(fetchedNote);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new NoteException("Faild to create note", 502);
//		}
		return true;
	}

	/**
	 * using token we will get the user and note is verified using note id then note
	 * will deleted using noteId.
	 */
	@Override
	public boolean deleteNote(long noteId, String token) {
		User fetchedUser = authenticatedUser(token);

		if (fetchedUser != null) {
			// authenticatedUser(token);
			verifiedNote(noteId);
			noteRepository.isDeletedNote(noteId);
			return true;
		}
		else
		throw new NoteException("Faild to Update note", 502);
		// found authorized user
		
		
//				authenticatedUser(token);
//				// verified valid note
//				verifiedNote(noteId);
//				noteRepository.isDeletedNote(noteId);
////				elasticSearchRepository.deleteNote(fetchedVerifiedNote);
//				return true;
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
		else
		{
			fetchedNote.setArchived(false);
			fetchedNote.setUpdatedDate(LocalDateTime.now());
			noteRepository.saveOrUpdate(fetchedNote);
			return false;
		}
		// if archived already unArchive functionality
				
//				elasticSearchRepository.updateNote(fetchedNote);
				
//		throw new NoteException("Faild to archiveNote", 404);
	}
//	

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
//		if (!fetchedNote.isTrashed()) {
//			fetchedNote.setTrashed(true);
//			fetchedNote.setUpdatedDate(LocalDateTime.now());
//			noteRepository.saveOrUpdate(fetchedNote);
//			return true;
//		}
		if (!fetchedNote.isTrashed()) {
			fetchedNote.setTrashed(true);
			fetchedNote.setArchived(false);
			fetchedNote.setPinned(false);
			fetchedNote.setReminderDate(null);
			fetchedNote.setUpdatedDate(LocalDateTime.now());
			noteRepository.saveOrUpdate(fetchedNote);
//			elasticSearchRepository.updateNote(fetchedNote);
			return true;
		}
		else
		{
			fetchedNote.setTrashed(false);
			fetchedNote.setPinned(false);
			fetchedNote.setArchived(false);
			fetchedNote.setUpdatedDate(LocalDateTime.now());
			noteRepository.saveOrUpdate(fetchedNote);
			return false;
		}
		

//		throw new NoteException("Note not found to make it trash", 404);
	}

	@Override
	public List<Note> getallNotes(String token) {
		
		List<Note> fetchedNotes = noteRepository.getAllNotes(authenticatedUser(token).getUserId());
		Collections.sort(fetchedNotes, (note1, note2) -> note2.getCreatedDate().compareTo(note1.getCreatedDate()));
		return fetchedNotes;
	}
	
	@Override
	public List<Note> fetchAllNotes(String token) {
		long userId = jwtToken.decodeToken(token);
		User user = userRepository.getUser(userId);
		if(user!=null)
		{
			List<Note> notes = noteRepository.getNotes(userId);
			notes.sort((Note note1 , Note note2)->note1.getTitle().compareTo(note2.getTitle()));            
			return notes;

		}
		throw  new UserNotFoundException("user Not Found");


	}


	@Override
	public List<Note> getAllTrashedNotes(String token) {

		List<Note> trashedNotes = noteRepository.getAllTrashedNotes(authenticatedUser(token).getUserId());
		
		Collections.sort(trashedNotes,
				(note1, note2) -> note2.getUpdatedDate().compareTo(note1.getUpdatedDate()));
		if (!trashedNotes.isEmpty()) {
//			return trashedNotes.stream().filter(note->note.isTrashed()==true).collect(Collectors.toList());
			return trashedNotes;
			
		}
		return trashedNotes;
//		throw new NoteException("TrashedNotes not found", 404);
	}
	
	
//	public List<NoteEntity> fetchTrashedNote(String token) {
//		long userId = jwtUtil.parseToken(token);
//		User user = userService.findById(userId);
//		if(user!=null)
//		{
//			List<NoteEntity> notes = noteRepository.getTrashedNotes(userId);
//			return notes.stream().filter(note->note.isTrashed()==true).collect(Collectors.toList());
////			return notes;
//
//			//return noteRepository.getTrashedNotes(userId);
//		}
//		throw  new UserNotFoundException("user Not Found",HttpStatus.NOT_FOUND);
//	}
	

	@Override
	public List<Note> getAllPinnedNotes(String token) {
		List<Note> pinnedNotes = noteRepository.getAllPinnedNotes(authenticatedUser(token).getUserId());
		Collections.sort(pinnedNotes,
				(note1, note2) -> note2.getUpdatedDate().compareTo(note1.getUpdatedDate()));
		if (!pinnedNotes.isEmpty()) {
			return pinnedNotes;
		}
//		throw new NoteException("PinnedNotes not found", 404);
		return pinnedNotes;
	}

	@Override
	public List<Note> getAllArchivedNotes(String token) {
		List<Note> fetchedArchivedNotes = noteRepository.getAllArchivedNotes(authenticatedUser(token).getUserId());
		Collections.sort(fetchedArchivedNotes,
				(note1, note2) -> note2.getUpdatedDate().compareTo(note1.getUpdatedDate()));
		if (!fetchedArchivedNotes.isEmpty()) {
			return fetchedArchivedNotes;
		}
		return fetchedArchivedNotes;
	}

	@Override
	public void changeColour(String token, long noteId, String noteColour) {
		authenticatedUser(token);
		
		Note fetchedNote = verifiedNote(noteId);
		
		fetchedNote.setColor(noteColour);
		
		noteRepository.saveOrUpdate(fetchedNote);
			
//			fetchedNote.setUpdatedDate(LocalDateTime.now());
//			fetchedNote.setColor(noteColour);
//			noteRepository.saveOrUpdate(fetchedNote);
		

	}

//	@Override
//	public void setRemainderforNote1(String token, long noteId, RemainderDto remainderDTO) {
//		authenticatedUser(token);
//		// validate note
////		Note fetchedNote = verifiedNote(noteId);
////		if (fetchedNote.getReminderDate() == null) {
////			fetchedNote.setUpdatedDate(LocalDateTime.now());
////			fetchedNote.setReminderDate(remainderDTO.getRemainder());
////			noteRepository.saveOrUpdate(fetchedNote);
////			return;
////		}
////		throw new RemainderException("Opps...Remainder already set!", 502);
//		Note fetchedNote = verifiedNote(noteId);
//		if (fetchedNote.getReminderDate() == null ||!fetchedNote.getReminderDate().equals(remainderDTO)) {
//			fetchedNote.setUpdatedDate(LocalDateTime.now());
//			fetchedNote.setReminderDate(remainderDTO.getRemainder());
//			noteRepository.saveOrUpdate(fetchedNote);
////			elasticSearchRepository.updateNote(fetchedNote);
//			return;
//		}
//
//	}
	
	
//	@Override
//	public void setRemainderforNotenn(String token, long noteId,LocalDateTime reminderDate) {
//		long userId = jwtToken.decodeToken(token);
//		Note fetchedNote = verifiedNote(noteId);
//		fetchedNote.setReminderDate(reminderDate);
//		noteRepository.saveOrUpdate(fetchedNote);
//		}
	
	
	
	
	

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
		System.out.println("inside searchByTittle method ");
//		authenticatedUser(token);
//		System.out.println("We got the autheicated user");
//		List<Note> fetchedNote = noteRepository.searchBy(noteTitle);
////		System.out.println("fetchedNote we get is  " + fetchedNote);
//		if (!fetchedNote.isEmpty())
//		{
////			System.out.println("returning fetched note");
//			return fetchedNote;
//		}
		authenticatedUser(token);
		List<Note> fetchedNotes = noteRepository.searchBy(noteTitle);
//		List<Note> fetchedElasticNotes = elasticSearchRepository.searchByTitle(noteTitle);
		// notes are not empty
		if (!fetchedNotes.isEmpty()) {
			return fetchedNotes;
		}
		throw new com.bridgelabz.fundoonotes.exception.NoteException("Note Not Found Exception", 502);

//		try {
//			List<Note> fetchedNote = elasticSearchImpl.searchByTitle(noteTitle);
//			return fetchedNote;
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		throw new com.bridgelabz.fundoonotes.exception.NoteException("Note Not Found Exception", 502);

//		}
	}
	@Override
	public List<Label> getLabelsOfNote(String token, long noteId) {
		// authenticate user
		authenticatedUser(token);
		return verifiedNote(noteId).getLabelsList();
	}
	
	@Override
	public boolean restoreNote(long noteId, String token) {
		// found authorized user
		authenticatedUser(token);
		// verified valid no
		Note fetchedNote = verifiedNote(noteId);
		if (fetchedNote.isTrashed()) {
			fetchedNote.setTrashed(false);
			fetchedNote.setUpdatedDate(LocalDateTime.now());
			noteRepository.saveOrUpdate(fetchedNote);
//			elasticSearchRepository.updateNote(fetchedNote);
			return true;
		}
		return false;
	}
	
	@Override
	public List<Note> getAllRemaindersNotes(String token) {
		List<Note> fetchedremainderNotes = noteRepository.getAllRemainderNotes(authenticatedUser(token).getUserId());
		Collections.sort(fetchedremainderNotes,
				(note1, note2) -> note2.getReminderDate().compareTo(note1.getReminderDate()));
		// note found of authenticated user
		if (!fetchedremainderNotes.isEmpty()) {
			return fetchedremainderNotes;
		}
		// empty list
		return fetchedremainderNotes;
	}

//	@Override
//	public void setRemainderforNote(String token, long noteId, RemainderDto remainderDTO) {
//		// TODO Auto-generated method stub
//		
//		
//	}

	@Override
	public void setRemainderforNote1(String token, long noteId, RemainderDto remainderDto) {
		authenticatedUser(token);
		// validate note
		Note fetchedNote = verifiedNote(noteId);
		if (fetchedNote.getReminderDate() == null) {
			fetchedNote.setUpdatedDate(LocalDateTime.now());
			System.out.println("remainderDto.getReminderDate() = "+remainderDto.getReminderDate());
			fetchedNote.setReminderDate(remainderDto.getReminderDate());
			noteRepository.saveOrUpdate(fetchedNote);
			System.out.println("Reminder note soved in database");
			return;
		}
		throw new RemainderException("Opps...Remainder already set! for this note", 502);
		
	}

	


}
