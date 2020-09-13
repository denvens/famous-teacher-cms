package com.qingclass.squirrel.cms.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {
    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    public @Bean MongoClient mongoClient() {
		MongoClientURI uri = new MongoClientURI(mongoUri);
		return new MongoClient(uri);
	}

	public @Bean MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoClient(), "msyb");
	}
}