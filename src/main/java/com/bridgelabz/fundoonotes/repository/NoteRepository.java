package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.Note;

/**
 * 
 * @author Niranjan
 * @Description :unwrap() Return an object of the specified type to allow access
 *              to the provider-specific API. If the provider's query
 *              implementation does not support the specified class, the
 *              PersistenceException is thrown.
 *{@link Note}
 */
@Repository
public class NoteRepository implements NoteRepositoryInterface {

	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public Note saveOrUpdate(Note newNote) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(newNote);
		return newNote;
	}

	@Override
	@Transactional
	public Note getNote(long noteId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("FROM Note WHERE noteId=:id");
		query.setParameter("id", noteId);
		return (Note) query.uniqueResult();

	}

	@Override
	public boolean isDeletedNote(long noteId) {

		return false;
	}

	@Override
	public List<Note> getAllNotes(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Note> getAllTrashedNotes(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Note> getAllPinnedNotes(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Note> getAllArchivedNotes(long userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Note> searchBy(String noteTitle) {
		// TODO Auto-generated method stub
		return null;
	}

}
