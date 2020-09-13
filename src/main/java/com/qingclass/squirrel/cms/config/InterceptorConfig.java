package com.qingclass.squirrel.cms.config;

import com.qingclass.squirrel.cms.interceptor.LessonJsonInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {


    @Bean
    LessonJsonInterceptor lessonJsonInterceptor(){
        return new LessonJsonInterceptor();
    }


    /**
     *
     * 拦截器链
     * */
    public void addInterceptors(InterceptorRegistry registry) {
        //拦截条件
        registry.addInterceptor(lessonJsonInterceptor()).
                addPathPatterns("/lesson/create").
                addPathPatterns("/lesson/edit").
                addPathPatterns("/question/create").
                addPathPatterns("/question/delete").
                addPathPatterns("/question/edit");

        super.addInterceptors(registry);
    }
}
