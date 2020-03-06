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
 *              PersistenceException is thrown. {@link Note}
 */
@Repository
public class NoteRepository implements AbstractNoteRepository {

	@Autowired
	private EntityManager entityManager;

	/**
	 * @Description:which takes note as input parameter saves note to database.
	 * @return note
	 */
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
		@SuppressWarnings("rawtypes")
		Query query = session.createQuery("FROM Note WHERE noteId=:id");
		query.setParameter("id", noteId);
		return (Note) query.uniqueResult();

	}

	@SuppressWarnings({ "rawtypes"})
	@Override
	@Transactional
	public boolean isDeletedNote(long noteId) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("DELETE FROM Note WHERE noteId=:id");
		query.setParameter("id", noteId);
		query.executeUpdate();
		return true;
	}

	/**
	 * @Description: sing HQL customized query from current session and fetching
	 *               operation is carried out which returns boolean value after
	 *               deleting the data from database.
	 */
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Note> getAllNotes(long userId) {
		return entityManager.unwrap(Session.class)
				.createQuery("FROM Note WHERE user_id=:id and is_trashed=false and is_archived=false")
				.setParameter("id", userId).getResultList();

	}

	/**
	 * @Description:using HQL customized query from current session and if the notes
	 *                    are not trashed and archived then simply fetch them.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Note> getAllTrashedNotes(long userId) {
		return entityManager.unwrap(Session.class).createQuery("From Note Where user_id=:id and is_trashed=true")
				.setParameter("id", userId).getResultList();
	}

	/**
	 * @Description:using HQL customized query from current session and if the notes
	 *                    are not trashed and archived then will fetch them.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Note> getAllPinnedNotes(long userId) {
		return entityManager.unwrap(Session.class).createQuery("From Note Where user_id=:id and is_pinned=true")
				.setParameter("id", userId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Note> getAllArchivedNotes(long userId) {
		return entityManager.unwrap(Session.class).createQuery("From Note Where user_id=:id and is_archived=true")
				.setParameter("id", userId).getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Note> searchBy(String noteTitle) {
		return entityManager.unwrap(Session.class).createQuery("From Note Where title=:title and is_trashed=false")
				.setParameter("id", noteTitle).getResultList();

	}

}
