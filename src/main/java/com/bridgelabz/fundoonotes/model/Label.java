package com.bridgelabz.fundoonotes.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;



@Entity
@Data
@Table(name = "label_details")
public class Label {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "label_id")
	private long labelId;
	private String labelName;
	private LocalDateTime createdDate;
	
//	@JsonIgnore
//	@ManyToMany(mappedBy = "labelsList", fetch = FetchType.LAZY)
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "note_label" ,joinColumns = { @JoinColumn(name = "label_id")} , inverseJoinColumns = {@JoinColumn(name = "note_id")})
	@JsonBackReference
	private List<Note> noteList;


//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable(name = "note_label" ,joinColumns = { @JoinColumn(name = "label_id")} , inverseJoinColumns = {@JoinColumn(name = "note_id")})
//	@JsonBackReference
	
	
}
