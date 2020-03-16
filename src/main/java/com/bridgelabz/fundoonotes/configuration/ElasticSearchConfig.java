package com.bridgelabz.fundoonotes.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchConfig {
	/**
	 * RestHighLevelClient client that wraps an instance of the low level RestClient
	 *  and allows to build requests and read responses.
	 * 
	 */
	
	@Bean(destroyMethod = "close") 
	public RestHighLevelClient client()
	{
		RestHighLevelClient restclient=new RestHighLevelClient(RestClient.builder(new HttpHost("localhost",9200,"http")));
		return restclient;
	}

}
