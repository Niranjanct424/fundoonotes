package com.bridgelabz.fundoonotes.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Niranjan c.t
 * @version 1.0
 * @Date : 2-03-2019
 * @description: Note is the entity class model and it has variables and setter
 *               getters,userId is the primary key
 */
@Entity
@Table(name = "note_details")
public class Note {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long userId;
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
	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getTittle() {
		return title;
	}

	public void setTittle(String tittle) {
		this.title = tittle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isArchived() {
		return isArchived;
	}

	public void setArchived(boolean isArchived) {
		this.isArchived = isArchived;
	}

	public boolean isPinned() {
		return isPinned;
	}

	public void setPinned(boolean isPinned) {
		this.isPinned = isPinned;
	}

	public boolean isTrashed() {
		return isTrashed;
	}

	public void setTrashed(boolean isTrashed) {
		this.isTrashed = isTrashed;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDateTime updatedDate) {
		this.updatedDate = updatedDate;
	}

	public LocalDateTime getReminderDate() {
		return reminderDate;
	}

	public void setReminderDate(LocalDateTime reminderDate) {
		this.reminderDate = reminderDate;
	}

	@Override
	public String toString() {
		return "Note [userId=" + userId + ", tittle=" + title + ", description=" + description + ", isArchived="
				+ isArchived + ", isPinned=" + isPinned + ", isTrashed=" + isTrashed + ", color=" + color
				+ ", createdDate=" + createdDate + ", updatedDate=" + updatedDate + ", reminderDate=" + reminderDate
				+ "]";
	}

}
