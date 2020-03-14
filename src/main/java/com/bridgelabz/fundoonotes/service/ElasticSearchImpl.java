package com.bridgelabz.fundoonotes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.fundoonotes.configuration.ElasticSearchConfig;
import com.bridgelabz.fundoonotes.model.Note;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ElasticSearchImpl implements IElasticSearch {

	@Autowired
	ElasticSearchConfig elasticSearchConfig;
	
	@Autowired
	ObjectMapper objectMapper;

	@Override
	public void createNote(Note noteInfo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateNote(Note noteInfo) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteNote(Note noteInfo) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Note> searchByTitle(String title) {
		// TODO Auto-generated method stub
		return null;
	}

}
