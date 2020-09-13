package com.qingclass.squirrel.cms.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.qingclass.squirrel.cms.mapper.user", sqlSessionFactoryRef = "sqlSessionBeanSquirrel")
public class SquirrelSessionConfig {

    @Autowired
    @Qualifier("dataSourceSquirrel")
    private DataSource ds2;

    @Bean("sqlSessionBeanSquirrel")
    public SqlSessionFactory sqlSessionSquirrelFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(ds2);


        return factoryBean.getObject();

    }
}