package com.qingclass.squirrel.cms.controller.ueditor.upload;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import com.qingclass.squirrel.cms.controller.ueditor.define.State;

@Component
public class Uploader {
	private HttpServletRequest request = null;
	private Map<String, Object> conf = null;
	
	public Uploader(HttpServletRequest request, Map<String, Object> conf) {
		this.request = request;
		this.conf = conf;
	}

	public final State doExec() {
		String filedName = (String) this.conf.get("fieldName");
		State state = null;

		if ("true".equals(this.conf.get("isBase64"))) {
			state = Base64Uploader.save(this.request.getParameter(filedName),
					this.conf);
		} else {
			state = new BinaryUploader().save(this.request, this.conf);
		}

		return state;
	}
}
