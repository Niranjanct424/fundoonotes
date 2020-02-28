package com.bridgelabz.fundoonotes.repository;

import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.model.User;

public interface UserRepositoryInterface {

	public User save(User newUser);

	
	public User getUser(String emailId);

	public User getUser(Long id);

	
	public boolean isVerifiedUserCheck(Long id);

	
	public boolean updatePassword(UpdatePassword updatePasswordinformation, long id);


}
