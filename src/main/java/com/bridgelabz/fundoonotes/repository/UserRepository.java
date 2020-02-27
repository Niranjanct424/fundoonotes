package com.bridgelabz.fundoonotes.repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.model.User;

@Repository
public class UserRepository implements UserRepositoryInterface {

	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public User save(User newUser) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(newUser);
		return newUser;
	}

	@Override
	public User getUser(String emailId) {
		Session session = entityManager.unwrap(Session.class);
		Query emailFetchQuery = session.createQuery("FROM User where emailId=:emailId");
		emailFetchQuery.setParameter("emailId", emailId);
		return (User) emailFetchQuery.uniqueResult();
	}

	@Override
	@Transactional
	public User getUser(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery(" FROM User WHERE id=:id");
		query.setParameter("id", id);
		return (User) query.uniqueResult();
	}

	@Override
	@Transactional
	public boolean isVerifiedUser(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("UPDATE User set is_verified=:verified" + " WHERE id=:id");
		query.setParameter("verified", true);
		query.setParameter("id", id);
		query.executeUpdate();
		return true;
	}

	@Override
	@Transactional
	public boolean updatePassword(UpdatePassword updatePasswordinformation, long id) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("UPDATE User set password=:updatedPassword" + " WHERE id=:id");
		query.setParameter("updatedPassword", updatePasswordinformation.getConfirmPassword());
		query.setParameter("id", id);
		query.executeUpdate();
		return true;
	}



}
