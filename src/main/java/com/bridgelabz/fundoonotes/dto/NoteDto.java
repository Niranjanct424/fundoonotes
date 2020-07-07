package com.bridgelabz.fundoonotes.dto;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * 
 * @author Niranjan c.t
 * @version 1.0
 * @Date : 2-03-2019
 */
@Data
@Component
public class NoteDto {

	private String title;
	
	private String description;
	
	private LocalDateTime reminderDate;



}
