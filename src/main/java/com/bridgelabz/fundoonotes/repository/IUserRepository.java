package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.model.User;

public interface IUserRepository {

	public User save(User newUser);

	
	public User getUser(String emailId);

	public User getUser(Long id);

	
	public boolean isVerifiedUserCheck(Long id);

	
	public boolean updatePassword(UpdatePassword updatePasswordinformation, long id);


	List<User> getUsers();


}
