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
import com.bridgelabz.fundoonotes.exception.UserNotFoundException;
import com.bridgelabz.fundoonotes.model.User;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.MailObject;
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
	
	@Autowired
	MailObject mailObject;
	
//	@Autowired
//	RabbitMQSender rabbitMQSender;
	

	@Override
	public boolean register(RegisterDto userDto) {
		User fetchedUser = userRepository.getUser(userDto.getEmailId());
		if (fetchedUser == null) {
			
			User newUser = new User();
			BeanUtils.copyProperties(userDto, newUser);
			newUser.setCreateDateTime(LocalDateTime.now());
			newUser.setUpdateDateTime(LocalDateTime.now());
			newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
			newUser.setVerified(false);
			userRepository.save(newUser);
			// user again fetched from database and mail sent for verification
			User fetchedUserForVerification = userRepository.getUser(newUser.getEmailId());
			String emailBodyContaintLink = Util.createLink("http://localhost:4200/Verificationa",
					jwtToken.createJwtToken(fetchedUserForVerification.getUserId()));

			if (emilService.sendMail(userDto.getEmailId(), "Verification", emailBodyContaintLink))
				return true;
//			mailObject.setEmail(userDto.getEmailId());
//			mailObject.setSubject("RabitMQ mail verification");
//			mailObject.setMessage(emailBodyContaintLink);
//			rabbitMQSender.send(mailObject);
			return true;
			
		}
		throw new EmailSentFailedException("User already exist with this EmailId try with another");


	}

	@Override
	public boolean isVerifiedUserToken(String token) {
		System.out.println("inside the isVerifed method");
		long verifcatinIdfromDecodedJwt = jwtToken.decodeToken(token);
		System.out.println("got decoded long value");
		if (verifcatinIdfromDecodedJwt > 0) {
			userRepository.isVerifiedUserCheck(verifcatinIdfromDecodedJwt);
			return true;
		}
		else
		{
		throw new EmailSentFailedException(" verification faild");
		}
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
			throw new InvalidCredentialException("You enterd invalid Credentials Please checked deatails..");
		}
		/**
		 * user not found
		 */
		throw new UserNotFoundException("UserNotFound");
	}

	@Override
	public boolean isUserExist(String emailId) {
		System.out.println("inside isUserExist");
		User userdata = userRepository.getUser(emailId);
		System.out.println("user found forgot password mail will go");
		if (userdata != null) {
			// System.out.println("forgot : user found");
			// checking the user is a verified user!
			if (userdata.isVerified()) {
				// System.out.println("forgot : user verified");
				/**
				 * reset password mail will send to user
				 */
				String emailLink = "http://localhost:4200/resetPassword/"
						+ jwtToken.createJwtToken(userdata.getUserId());
				emilService.sendMail(userdata.getEmailId(), "Update Password Link", emailLink);
				return true;
			}
			/**
			 * for not verify user i am sending the verification link
			 */
			String unVerifiedUsertosendmail = Util.createLink("http://localhost:4200/resetPassword/",
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
		System.out.println("inside update password");
		User userdata = userRepository.getUser(jwtToken.decodeToken(token));
		if (updatingPasswordinfo.getPassword().equals(updatingPasswordinfo.getConfirmPassword())) {
			System.out.println("passwords are equal");
			updatingPasswordinfo
					.setConfirmPassword(bCryptPasswordEncoder.encode(updatingPasswordinfo.getConfirmPassword()));
			System.out.println("UpdatePassword encoded set values added");
			userRepository.updatePassword(updatingPasswordinfo, jwtToken.decodeToken(token));
			/**
			 * sending notification mail after updating password to know user password
			 * updated.
			 */
			emilService.sendMail(userdata.getEmailId(), "Your Email Password Updated",
					" go check for login");

			return true;
		}
		else
		{
		throw new PasswordMissMatchException(" PassWord mismatch .........");
		}

	}

//	private String mailContaintAfterUpdatingPassword(UpdatePassword updatePasswordInformation) {
//		String passwordUpdateBodyContent = "Login Credentials \n" + "UserId : " + updatePasswordInformation.getEmailId()
//				+ "\nPassword : " + updatePasswordInformation.getPassword();
//		String loginString = "\nClick on the link to login\n";
//		String loginLink = "http://localhost:8080" + "/User/Login";
//		return passwordUpdateBodyContent + loginString + loginLink;
//	}

}
