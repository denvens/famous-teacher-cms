package com.qingclass.squirrel.cms.app;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.qingclass.squirrel.cms.utils.SpringUtil;

@SpringBootApplication(exclude = {
		DataSourceAutoConfiguration.class
})
@ComponentScan("com.qingclass.squirrel.cms")
@EnableTransactionManagement
@Import({SpringUtil.class})
public class SquirrelCmsApplication {
	
	public static void main(String[] args) throws IOException {
		SpringApplication.run(SquirrelCmsApplication.class, args);
	}
}