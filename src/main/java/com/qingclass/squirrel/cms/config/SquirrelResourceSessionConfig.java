package com.qingclass.squirrel.cms.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = "com.qingclass.squirrel.cms.mapper.cms", sqlSessionFactoryRef = "sqlSessionBeanSquirrelResource")
public class SquirrelResourceSessionConfig {

    @Autowired
    @Qualifier("dataSourceSquirrelResource")
    DataSource resourceDataSource;

    @Bean("sqlSessionBeanSquirrelResource")
    @Primary
    public SqlSessionFactory sqlSessionSquirrelResourceFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(resourceDataSource); // 使用titan数据源, 连接titan库

        return factoryBean.getObject();

    }
}
