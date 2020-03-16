package com.bridgelabz.fundoonotes.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.configuration.ElasticSearchConfig;
import com.bridgelabz.fundoonotes.model.Note;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ElasticSearchImpl implements IElasticSearch {

	@Autowired
	ElasticSearchConfig elasticSearchConfig;

	@Autowired
	ObjectMapper objectMapper;

	private String INDEX = "springboot";

	private String TYPE = "note_details";

	@Override
	public String createNote(Note noteInfo) {

		Map<String, Object> notemapper = objectMapper.convertValue(noteInfo, Map.class);
		IndexRequest indexrequest = new IndexRequest(INDEX, TYPE, String.valueOf(noteInfo.getNoteId()))
				.source(notemapper);
		IndexResponse indexResponse = null;
		try {
			indexResponse = elasticSearchConfig.client().index(indexrequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(indexrequest);
		System.out.println(indexResponse);
		System.out.println(indexResponse.getResult().name());
		return indexResponse.getResult().name();

	}

	@Override
	public String updateNote(Note noteInfo) {
		Map<String, Object> notemapper = objectMapper.convertValue(noteInfo, Map.class);
		System.out.println(noteInfo.getNoteId());
		UpdateRequest updateRequest = new UpdateRequest(INDEX, TYPE, String.valueOf(noteInfo.getNoteId()))
				.doc(notemapper);
		UpdateResponse updateResponse = null;
		try {
			updateResponse = elasticSearchConfig.client().update(updateRequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return updateResponse.getResult().name();

	}

	@Override
	public String deleteNote(Note noteInfo) {
		Map<String, Object> notemapper = objectMapper.convertValue(noteInfo, Map.class);
		DeleteRequest deleterequest = new DeleteRequest(INDEX, TYPE, String.valueOf(noteInfo.getNoteId()));
		DeleteResponse deleteResponse = null;
		try {
			deleteResponse = elasticSearchConfig.client().delete(deleterequest, RequestOptions.DEFAULT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return deleteResponse.getResult().name();

	}

	@Override
	public List<Note> searchByTitle(String title) {

		System.out.println(title);
		SearchRequest searchrequest = new SearchRequest("springboot");
		SearchSourceBuilder searchsource = new SearchSourceBuilder();
		System.out.println(searchrequest);

		searchsource.query(QueryBuilders.matchQuery("title", title));
		searchrequest.source(searchsource);
		SearchResponse searchresponse = null;
		try {
			searchresponse = elasticSearchConfig.client().search(searchrequest, RequestOptions.DEFAULT);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(getResult(searchresponse).toString());
		return getResult(searchresponse);
	}

	private List<Note> getResult(SearchResponse searchresponse) {
		SearchHit[] searchhits = searchresponse.getHits().getHits();
		List<Note> notes = new ArrayList<>();
		if (searchhits.length > 0) {
			Arrays.stream(searchhits)
					.forEach(hit -> notes.add(objectMapper.convertValue(hit.getSourceAsMap(), Note.class)));
		}
		return notes;
	}

}
