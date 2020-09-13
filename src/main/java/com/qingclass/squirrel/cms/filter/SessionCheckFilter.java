package com.qingclass.squirrel.cms.filter;

import com.qingclass.squirrel.cms.entity.cms.CmsAdmin;
import com.qingclass.squirrel.cms.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class SessionCheckFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;


		CmsAdmin cmsAdminInfo = null;

		try{
			cmsAdminInfo = (CmsAdmin) req.getSession().getAttribute(CmsAdmin.SESSION_SQUIRREL_ADMIN_KEY);
		}catch (Exception e ){
			logger.error("sign in error, Deserialize failed");
			res.setContentType("application/json; charset=utf-8");
			String deniedJson = Tools.mapToJson(Tools.f("登录失败，请重新登录。"));
			PrintWriter writer = response.getWriter();
			writer.println(deniedJson);
			writer.close();
			return;
		}


		if (null == cmsAdminInfo) {
			res.setContentType("application/json; charset=utf-8");
			String deniedJson = Tools.mapToJson(Tools.d());
			PrintWriter writer = response.getWriter();
			writer.println(deniedJson);
			writer.close();
			return;
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}
