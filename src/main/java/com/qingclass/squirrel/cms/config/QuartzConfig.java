package com.qingclass.squirrel.cms.config;

import org.quartz.Scheduler;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class QuartzConfig {

//	@Autowired
//	private  Environment environment;
	
    @Bean(name="SchedulerFactory")
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setQuartzProperties(quartzProperties());
        return factory;
    }


    @Bean
    public Properties quartzProperties() throws IOException {
//    	String env = environment.getProperty("spring.profiles.active");
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
//        propertiesFactoryBean.setLocation(new ClassPathResource("/application-quartz-"+env+".properties"));
        propertiesFactoryBean.setLocation(new ClassPathResource("/application-quartz.properties"));
        //在quartz.properties中的属性被读取并注入后再初始化对象
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }



    /*
     * quartz初始化监听器
     */
  /*  @Bean
    public QuartzInitializerListener executorListener() {
        return new QuartzInitializerListener();
    }*/

    /*
     * 通过SchedulerFactoryBean获取Scheduler的实例
     */
    @Bean(name="Scheduler")
    public Scheduler scheduler() throws IOException {
        return schedulerFactoryBean().getScheduler();
    }


}
