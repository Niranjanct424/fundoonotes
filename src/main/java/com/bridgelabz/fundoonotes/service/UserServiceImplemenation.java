package com.bridgelabz.fundoonotes.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgeLabz.fundooNotes.exception.InvalidCredentialsException;
import com.bridgelabz.fundoonotes.constants.UserException;
import com.bridgelabz.fundoonotes.dto.LoginDto;
import com.bridgelabz.fundoonotes.dto.RegisterDto;
import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utility.EmailService;
import com.bridgelabz.fundoonotes.utility.JWTToken;
import com.bridgelabz.fundoonotes.utility.Util;

@Service
@Transactional
public class UserServiceImplemenation implements UserService {

	@Autowired
	private Environment environment;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private JWTToken jwtToken;

	@Autowired
	EmailService emilService;
	
	@Autowired
	UserException userException;

	@Override
	public boolean register(RegisterDto userDto) {
		User fetchedUser = userRepository.getUser(userDto.getEmailId());
		if (fetchedUser != null) {
			return false;
		}
		User newUser = new User();
		BeanUtils.copyProperties(userDto, newUser);
		newUser.setCreateDateTime(LocalDateTime.now());
		newUser.setUpdateDateTime(LocalDateTime.now());
		newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
		newUser.setVerified(false);
		userRepository.save(newUser);
		// user again fetched from database and mail sent for verification
		User fetchedUserForVerification = userRepository.getUser(newUser.getEmailId());
		String emailBodyContaintLink = Util.createLink("http://localhost:8080/user/verification",
				jwtToken.createJwtToken(fetchedUserForVerification.getUserId()));

		if (emilService.sendMail(userDto.getEmailId(), "Verification", emailBodyContaintLink))
			return true;
		else
			throw new UserException("Opps...Error sending verification mail!", 500);

	}

	@Override
	public boolean isVerifiedUserToken(String token) {
		long verifcatinIdfromDecodedJwt = jwtToken.decodeToken(token);
		if (verifcatinIdfromDecodedJwt > 0) {
			userRepository.isVerifiedUserCheck(verifcatinIdfromDecodedJwt);
			return true;
		}
		return false;
	}

	@Override
	public User login(LoginDto loginInformation) {
		User fetchedUser = userRepository.getUser(loginInformation.getEmailId());
		if (fetchedUser != null) {
			if (bCryptPasswordEncoder.matches(loginInformation.getPassword(), fetchedUser.getPassword())) {
				if (fetchedUser.isVerified()) {
					return fetchedUser;
				}
				String unVerifiedUsertosendmail = Util.createLink("http://localhost:8080/user/verification",
						jwtToken.createJwtToken(fetchedUser.getUserId()));
				emilService.sendMail(fetchedUser.getEmailId(), "Verification", unVerifiedUsertosendmail);			
			}
			throw new UserException("You enterd invalid Credentials", 400);
		}
		throw new UserException("UserNotFound", 404);
	}

	@Override
	public boolean isUserPresent(String emailId) {

		return false;
	}

	@Override
	public boolean updatePassword(UpdatePassword updatePassword, String token) {

		return false;
	}

}
