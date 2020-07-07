package com.bridgelabz.fundoonotes.repository;

import java.util.List;

/**
 * 
 * @author Niranjan c.t
 * @version 1.0
 * @Date : 29-02-2019
 */

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.model.User;

@Repository
public class UserRepository implements IUserRepository {

	@Autowired
	private EntityManager entityManager;

	@Override
	@Transactional
	public User save(User newUser) {
		Session session = entityManager.unwrap(Session.class);
		session.saveOrUpdate(newUser);
		return newUser;
	}

	/**
	 * getUser method produces the
	 */
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public User getUser(String emailId) {
		Session session = entityManager.unwrap(Session.class);
		Query emailFetchQuery = session.createQuery("FROM User where emailId=:emailId");
		emailFetchQuery.setParameter("emailId", emailId);
		return (User) emailFetchQuery.uniqueResult();
	}

	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public User getUser(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery(" FROM User WHERE id=:id");
		query.setParameter("id", id);
		return (User) query.uniqueResult();
	}

	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public boolean isVerifiedUserCheck(Long id) {
		Session session = entityManager.unwrap(Session.class);
		Query query = session.createQuery("UPDATE User set is_verified=:verified" + " WHERE id=:id");
		query.setParameter("verified", true);
		query.setParameter("id", id);
		query.executeUpdate();
		return true;
	}
	
	
	@SuppressWarnings("rawtypes")
	@Override
	@Transactional
	public boolean updatePassword(UpdatePassword updatePasswordinformation, long id) {
		Session session = entityManager.unwrap(Session.class);
		System.out.println("inside database method");
		Query query = session.createQuery("UPDATE User set password=:updatedPassword" + " WHERE id=:id");
		query.setParameter("updatedPassword", updatePasswordinformation.getConfirmPassword());
		query.setParameter("id", id);
		query.executeUpdate();
		return true;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<User> getUsers() {
		Session currentsession = entityManager.unwrap(Session.class);
		List<User> usersList = currentsession.createQuery("FROM User").getResultList();
		return  usersList;
	}

}
