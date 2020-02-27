package com.bridgelabz.fundoonotes.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
		String emailBodyContaintLink = Util.createLink(
				Util.IP_ADDRESS + environment.getProperty("server.port") + Util.REGESTATION_VERIFICATION_LINK,
				jwtToken.createJwtToken(fetchedUserForVerification.getUserId()));
		if (emilService.sendMail(userDto.getEmailId(), "Verification", emailBodyContaintLink))
			return true;
		throw new UserException("Opps...Error sending verification mail!", 500);

	}

	@Override
	public boolean isVerifiedUserToken(String token) {

		return false;
	}

	@Override
	public User login(LoginDto loginInformation) {

		return null;
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
