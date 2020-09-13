package com.qingclass.squirrel.cms.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.qingclass.squirrel.cms.mapper.statistic", sqlSessionFactoryRef = "sqlSessionBeanStatistic")
public class SquirrelStatisticDataSource {
	public static final String DATASOURCE_STATISTIC = "dataSourceStatistic";
	public static final String SQL_SESSION_BEAN_STATISTIC = "sqlSessionBeanStatistic";

	@Bean(DATASOURCE_STATISTIC)
	@ConfigurationProperties("spring.datasource.squirrel.statistic")
	public DataSource statisticDataSource() {
		return DataSourceBuilder.create().build();
	}

	@Bean(SQL_SESSION_BEAN_STATISTIC)
	public SqlSessionFactoryBean statisticSqlSessionFactory(@Qualifier(DATASOURCE_STATISTIC) DataSource dataSource)
			throws Exception {
		final SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		return sqlSessionFactoryBean;
	}

	@Bean(name="statisticTransactionManager")
	public PlatformTransactionManager statisticTransactionManager(@Qualifier(DATASOURCE_STATISTIC) DataSource dataSource){
		return new DataSourceTransactionManager(dataSource);
	}

}
