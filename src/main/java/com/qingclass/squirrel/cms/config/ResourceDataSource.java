package com.qingclass.squirrel.cms.config;

import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ResourceDataSource {
 public static final String DATASOURCE_ADMIN = "dataSourceSquirrelResource";

 public static final String DATASOURCE_ADMIN_2 = "dataSourceSquirrel";


 @Bean(DATASOURCE_ADMIN)
 @ConfigurationProperties(prefix = "spring.datasource.squirrel_resource")
 @Primary
 public DataSource resourceDataSourceSquirrelResource() {
  return DataSourceBuilder.create().build();
 }

 @Bean(DATASOURCE_ADMIN_2)
 @ConfigurationProperties(prefix = "spring.datasource.squirrel")
 public DataSource resourceDataSourceSquirrel() {
  return DataSourceBuilder.create().build();
 }



}