package com.qingclass.squirrel.cms.config;

import com.qingclass.squirrel.cms.filter.SessionCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SessionCheckFilterConfig {
	@Bean
	public FilterRegistrationBean sessionCheckFilter(){
		// 登陆拦截
	    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	    registrationBean.setFilter(new SessionCheckFilter());
	    registrationBean.addUrlPatterns("/lesson/*");
		registrationBean.addUrlPatterns("/level/create");
		registrationBean.addUrlPatterns("/level/edit");
		registrationBean.addUrlPatterns("/level/info");
		registrationBean.addUrlPatterns("/picturebook/*");
		registrationBean.addUrlPatterns("/question/*");
		registrationBean.addUrlPatterns("/subject/*");
		registrationBean.addUrlPatterns("/unit/*");
		registrationBean.addUrlPatterns("/payment/*");
		registrationBean.addUrlPatterns("/word/list");
		registrationBean.addUrlPatterns("/word/create");
		registrationBean.addUrlPatterns("/word/edit");
		registrationBean.addUrlPatterns("/word/delete");
		registrationBean.addUrlPatterns("/word/info");
		registrationBean.addUrlPatterns("/word/findWord");
		registrationBean.addUrlPatterns("/admin/sign-up");
		registrationBean.addUrlPatterns("/user/*");
		registrationBean.addUrlPatterns("/wx-purchase/*");
		registrationBean.addUrlPatterns("/wx-push-message/*");
		registrationBean.addUrlPatterns("/wx-share/*");
		registrationBean.addUrlPatterns("/channel/*");
		registrationBean.addUrlPatterns("/data/*");
		registrationBean.addUrlPatterns("/invitation/*");
		registrationBean.addUrlPatterns("/voucher/*");

	    registrationBean.setOrder(100);
	    return registrationBean;
	}
}
