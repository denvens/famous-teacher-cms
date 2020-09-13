package com.qingclass.squirrel.cms.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfig {

	@Bean
	public HttpClient initHttpClient() {
		return HttpClientBuilder.create().build();
	}

}
