package com.bridgelabz.fundoonotes.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * 
 * @author Niranjan c.t
 * @version 1.0
 * @Date : 2-03-2019
 * @description: Note is the entity class model and it has variables and setter
 *               getters and toString() implemented,Id is the primary key
 */
@Data
@Entity
public class Note {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "note_id")
	private long noteId;
	
	private String title;
	
	private String description;
	
	private boolean isArchived;
	
	private boolean isPinned;
	
	private boolean isTrashed;
	
	private String color;
	
	private LocalDateTime createdDate;
	
	private LocalDateTime updatedDate;
	
	private LocalDateTime reminderDate;

	/**
	 * @Description : Getters and setters Implementation
	 * @return
	 */


}
