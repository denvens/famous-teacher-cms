package com.qingclass.squirrel.cms.controller.ueditor.upload;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.springframework.context.ApplicationContext;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.qingclass.squirrel.cms.constant.Global;
import com.qingclass.squirrel.cms.controller.ueditor.PathFormat;
import com.qingclass.squirrel.cms.controller.ueditor.define.AppInfo;
import com.qingclass.squirrel.cms.controller.ueditor.define.BaseState;
import com.qingclass.squirrel.cms.controller.ueditor.define.FileType;
import com.qingclass.squirrel.cms.controller.ueditor.define.State;
import com.qingclass.squirrel.cms.utils.ApplicationContextHelper;
import com.qingclass.squirrel.cms.utils.DateFormatHelper;
import com.qingclass.squirrel.cms.utils.OssUtil;

public class BinaryUploader {
	private static Logger log = Logger.getLogger("BinaryUploader");

	private ApplicationContext applicationContext = null;
	private static BinaryUploader binaryUploader = null;

	public State save(HttpServletRequest request, Map<String, Object> conf) {
		Global configuration = ApplicationContextHelper.getApplicationContext().getBean(Global.class);
		boolean isAjaxUpload = request.getHeader("X_Requested_With") != null;

		if (!ServletFileUpload.isMultipartContent(request)) {
			return new BaseState(false, AppInfo.NOT_MULTIPART_CONTENT);
		}

		ServletFileUpload upload = new ServletFileUpload(new DiskFileItemFactory());

		if (isAjaxUpload) {
			upload.setHeaderEncoding("UTF-8");
		}
		try {
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			State storageState = new BaseState(true);
			// 获取multiRequest 中所有的文件名
			Iterator<String> iter = multiRequest.getFileNames();
			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if (file != null) {
					String savePath = (String) conf.get("savePath");
					String originFileName = file.getOriginalFilename();
					String suffix = FileType.getSuffixByFilename(originFileName);
					// 将名字重复名，以免重复导致图片被覆盖并且防由于特殊字符导致无法上传
					originFileName = DateFormatHelper.getNowTimeStr("yyyyMMddHHmmssSSS") + new Random().nextInt(100);// originFileName.substring(0,originFileName.length()
					savePath = savePath + suffix;
					long maxSize = ((Long) conf.get("maxSize")).longValue();
					if (file.getSize() > maxSize) {
						return new BaseState(false, AppInfo.MAX_SIZE);
					}
					if (!validType(suffix, (String[]) conf.get("allowFiles"))) {
						return new BaseState(false, AppInfo.NOT_ALLOW_FILE_TYPE);
					}
					savePath = PathFormat.parse(savePath, originFileName);
					this.upload(savePath, file);
					storageState.putInfo("title", originFileName + suffix);

					if (storageState.isSuccess()) {
						storageState.putInfo("url", configuration.getDomain() + savePath);// PathFormat.format(savePath)
						storageState.putInfo("type", suffix);
						storageState.putInfo("state", AppInfo.getStateInfo(AppInfo.SUCCESS));
						storageState.putInfo("original", originFileName + suffix);
					}
				} else {
					return new BaseState(false, AppInfo.NOTFOUND_UPLOAD_DATA);
				}
			}
			return storageState;
		} catch (Exception e) {
			System.out.println("e>>>" + e);
			log.error("Exception", e);
		}
		return new BaseState(false, AppInfo.IO_ERROR);
	}

	private String upload(String savePath, MultipartFile file) {

		OssUtil ossUtil = ApplicationContextHelper.getApplicationContext().getBean(OssUtil.class);

		return ossUtil.upLoad(savePath, file);

	}

	private static boolean validType(String type, String[] allowTypes) {
		List<String> list = Arrays.asList(allowTypes);

		return list.contains(type);
	}

	public static int getCharacterPosition(String string) {

		Matcher slashMatcher = Pattern.compile("/").matcher(string);
		int mIdx = 0;
		while (slashMatcher.find()) {
			mIdx++;
			// 当"/"符号第二次出现的位置
			if (mIdx == 3) {
				break;
			}
		}
		return slashMatcher.start();
	}

}
