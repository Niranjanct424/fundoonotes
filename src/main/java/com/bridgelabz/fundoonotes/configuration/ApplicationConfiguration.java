package com.bridgelabz.fundoonotes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class ApplicationConfiguration {
	/**
	 * creates the object of BCryptPasswordEncoder
	 * 
	 * it will gives the object of BCryptPasswordEncoder
	 */

	@Bean
	public BCryptPasswordEncoder getPasswordEncription() {
		return new BCryptPasswordEncoder();
	}

}
