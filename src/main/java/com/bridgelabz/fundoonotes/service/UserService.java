package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.LoginDto;
import com.bridgelabz.fundoonotes.dto.RegisterDto;
import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.model.User;

/**
 * 
 * @author Niranjan c.t
 * @version 1.0
 * @Date : 29-02-2019
 */
public interface UserService {

	public boolean register(RegisterDto userDto);

	public boolean isVerifiedUserToken(String token);

	public User login(LoginDto loginInformation);

	public boolean isUserExist(String emailId);

	public boolean updatePassword(UpdatePassword updatePassword, String token);

}
