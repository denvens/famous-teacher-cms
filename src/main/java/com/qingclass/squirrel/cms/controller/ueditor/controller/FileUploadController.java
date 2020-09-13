package com.qingclass.squirrel.cms.controller.ueditor.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qingclass.squirrel.cms.controller.ueditor.ActionEnter;

@Controller
@RequestMapping("/ueditor")
public class FileUploadController  {
	
	
	
	@RequestMapping(value = { "/toUpload" })
	@ResponseBody
	public String toUploads(HttpServletRequest request ) {
 		String rootPath = request.getSession().getServletContext().getRealPath("/");
		return new ActionEnter( request, rootPath ).exec();
	}
	
	
	
}