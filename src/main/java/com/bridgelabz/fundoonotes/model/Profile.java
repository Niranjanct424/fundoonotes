package com.bridgelabz.fundoonotes.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Profile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String profilePicName;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User userInfo;

	public Profile(String profilePicName, User userInfo) {
		super();
		this.profilePicName = profilePicName;
		this.userInfo = userInfo;
	}

}
