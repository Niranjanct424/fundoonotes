package com.bridgelabz.fundoonotes.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author Niranjan c.t
 *
 */
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	private long userId;
	@NotNull
	private String name;
	@NotNull
	private String password;
	@NotNull
	private String mobileNumber;
	@Column(unique = true)
	@Email
	private String emailId;
	@NotNull
	private LocalDateTime createDateTime;
	@NotNull
	private LocalDateTime updateDateTime;
	@Column(columnDefinition = "boolean default false",nullable = false)
	private boolean isVerified ;

	public User(long id, String name, String password, String mobileNumber, String emailId) {
		super();
		this.userId = id;
		this.name = name;
		this.password = password;
		this.mobileNumber = mobileNumber;
		this.emailId = emailId;
	}

	public User() {
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public LocalDateTime getCreateDateTime() {
		return createDateTime;
	}

	public void setCreateDateTime(LocalDateTime createDateTime) {
		this.createDateTime = createDateTime;
	}

	public LocalDateTime getUpdateDateTime() {
		return updateDateTime;
	}

	public void setUpdateDateTime(LocalDateTime updateDateTime) {
		this.updateDateTime = updateDateTime;
	}

	public boolean isVerified() {
		return isVerified;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}

	@Override
	public String toString() {
		return "User [id=" + userId + ", name=" + name + ", password=" + password + ", mobileNumber=" + mobileNumber
				+ ", emailId=" + emailId + ", createDateTime=" + createDateTime + ", updateDateTime=" + updateDateTime
				+ ", isVerified=" + isVerified + "]";
	}

}
