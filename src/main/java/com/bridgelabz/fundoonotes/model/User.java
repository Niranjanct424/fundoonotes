package com.bridgelabz.fundoonotes.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

/**
 * @author Niranjan c.t
 */
@Data
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@NotNull
	@Column(name = "user_id")
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
	@Column(columnDefinition = "boolean default false", nullable = false)
	private boolean isVerified;

	@JsonIgnore
	@JoinColumn(name = "user_id")
	@OneToMany(cascade = CascadeType.ALL)
	private List<Note> notes;

	@JsonIgnore
	@JoinColumn(name = "user_id")
	@OneToMany(cascade = CascadeType.ALL)
	private List<Label> labels;

	
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


}
