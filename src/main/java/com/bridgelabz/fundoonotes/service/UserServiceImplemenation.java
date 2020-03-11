package com.bridgelabz.fundoonotes.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.LoginDto;
import com.bridgelabz.fundoonotes.dto.RegisterDto;
import com.bridgelabz.fundoonotes.dto.UpdatePassword;
import com.bridgelabz.fundoonotes.exception.EmailSentFailedException;
import com.bridgelabz.fundoonotes.exception.InvalidCredentialException;
import com.bridgelabz.fundoonotes.exception.PasswordMissMatchException;
import com.bridgelabz.fundoonotes.exception.UserException;
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.utility.EmailService;
import com.bridgelabz.fundoonotes.utility.JWTToken;
import com.bridgelabz.fundoonotes.utility.Util;

@Service
@Transactional
public class UserServiceImplemenation implements UserService {

	@SuppressWarnings("unused")
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
		String emailBodyContaintLink = Util.createLink("http://localhost:8080/User/Verification",
				jwtToken.createJwtToken(fetchedUserForVerification.getUserId()));

		if (emilService.sendMail(userDto.getEmailId(), "Verification", emailBodyContaintLink))
			return true;
		else
			throw new EmailSentFailedException("Opps...Error sending verification mail!");

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
		// user exist if user != null
		if (fetchedUser != null) {
			if (bCryptPasswordEncoder.matches(loginInformation.getPassword(), fetchedUser.getPassword())) {
				// checking the user is verified or what?
				if (fetchedUser.isVerified()) {
					return fetchedUser;
				}
				/**
				 * for not verified user i am sending the verification link
				 */
				String unVerifiedUsertosendmail = Util.createLink("http://localhost:8080/User/Verification",
						jwtToken.createJwtToken(fetchedUser.getUserId()));
				emilService.sendMail(fetchedUser.getEmailId(), "Verification", unVerifiedUsertosendmail);
			}
			throw new InvalidCredentialException("You enterd invalid Credentials");
		}
		/**
		 * user not found
		 */
		throw new UserNotFoundException("UserNotFound");
	}

	@Override
	public boolean isUserExist(String emailId) {
		User userdata = userRepository.getUser(emailId);
		if (userdata != null) {
			// System.out.println("forgot : user found");
			// checking the user is a verified user!
			if (userdata.isVerified()) {
				// System.out.println("forgot : user verified");
				/**
				 * reset password mail will send to user
				 */
				String emailLink = "http://localhost:8080/User/UpdatePassword"
						+ jwtToken.createJwtToken(userdata.getUserId());
				emilService.sendMail(userdata.getEmailId(), "Update Password Link", emailLink);
				return true;
			}
			/**
			 * for not verify user i am sending the verification link
			 */
			String unVerifiedUsertosendmail = Util.createLink("http://localhost:8080/User/Verification",
					jwtToken.createJwtToken(userdata.getUserId()));
			emilService.sendMail(userdata.getEmailId(), "Verification", unVerifiedUsertosendmail);
		}
		/**
		 * user not found
		 */
		throw new UserNotFoundException("UserNotFound");
	}

	@Override
	public boolean updatePassword(UpdatePassword updatingPasswordinfo, String token) {
		if (updatingPasswordinfo.getPassword().equals(updatingPasswordinfo.getConfirmPassword())) {
			updatingPasswordinfo
					.setConfirmPassword(bCryptPasswordEncoder.encode(updatingPasswordinfo.getConfirmPassword()));
			userRepository.updatePassword(updatingPasswordinfo, jwtToken.decodeToken(token));
			/**
			 * sending notification mail after updating password to know user password
			 * updated.
			 */
			emilService.sendMail(updatingPasswordinfo.getEmailId(), "Your Email Password Updated",
					mailContaintAfterUpdatingPassword(updatingPasswordinfo));

			return true;
		}
		throw new PasswordMissMatchException(" PassWord mismatch .........");

	}

	private String mailContaintAfterUpdatingPassword(UpdatePassword updatePasswordInformation) {
		String passwordUpdateBodyContent = "Login Credentials \n" + "UserId : " + updatePasswordInformation.getEmailId()
				+ "\nPassword : " + updatePasswordInformation.getPassword();
		String loginString = "\nClick on the link to login\n";
		String loginLink = "http://localhost:8080" + "/User/Login";
		return passwordUpdateBodyContent + loginString + loginLink;
	}

}
