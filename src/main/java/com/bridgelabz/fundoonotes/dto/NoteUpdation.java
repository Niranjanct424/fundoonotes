package com.bridgelabz.fundoonotes.dto;

import java.time.LocalDateTime;

import lombok.Data;

/**
 *@Date :4/3/2020
 * @author Niranjan c.t
 */
@Data
public class NoteUpdation {

	private long id;
	
	private String title;
	
	private String description;
	
	private boolean isArchieved;
	
	private boolean isPinned;
	
	private boolean isTrashed;
	
	private LocalDateTime createdDateAndTime;
	
	private LocalDateTime upDateAndTime;
}
