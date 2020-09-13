package com.qingclass.squirrel.cms.controller;
import com.qingclass.squirrel.cms.entity.cms.CmsAdmin;
import com.qingclass.squirrel.cms.mapper.cms.CmsAdminMapper;
import com.qingclass.squirrel.cms.utils.MD5Util;
import com.qingclass.squirrel.cms.utils.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class CmsAdminController {

	@Autowired
    private CmsAdminMapper cmsAdminMapper;

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@ResponseBody
    @RequestMapping(value="/sign-up", method = RequestMethod.POST)
	public Map<String, Object> signUp(
			@RequestParam(value = "loginName", required = false)String loginName,
			@RequestParam(value = "userName", required = false)String userName, 
			@RequestParam(value = "roleName", required = false)String roleName){

		//TODO 参数非法校验

		//TODO admin表排重

		String plain = Tools.randomString(6);	//随机6位明文

		String crypt = MD5Util.crypt(loginName+plain);

		CmsAdmin cmsAdmin = new CmsAdmin(loginName, crypt, userName, plain, roleName);

		try{
			cmsAdminMapper.insert(cmsAdmin);
		}catch (Exception e){
			logger.error("sign-up was failed! ExceptionInfo : "+e);
			return Tools.f("sign-up was failed!");
		}


		logger.info("sign-up was successful，loginName="+loginName);
		return Tools.s();
	}

	@ResponseBody
	@RequestMapping(value="/sign-in", method = RequestMethod.POST)
	public Map<String, Object> signIn(
			HttpServletRequest request, 
			@RequestParam(value = "loginName", required = false)String loginName, 
			@RequestParam(value = "password", required = false)String password){

		//TODO 参数非法校验

		CmsAdmin cmsAdmin = cmsAdminMapper.SelectAdminByLoginName(loginName);

		if(cmsAdmin == null){
			return Tools.f("user doesn't exist!");
		}

		String crypt = MD5Util.crypt(password + cmsAdmin.getPlain());
		if(crypt.equals(cmsAdmin.getPassword())){
			logger.info("sign-in success,loginName="+loginName);

			request.getSession().setAttribute(CmsAdmin.SESSION_SQUIRREL_ADMIN_KEY,cmsAdmin);
			HashMap<String, Object> stringObjectHashMap = new HashMap<>();
			stringObjectHashMap.put("roleName",cmsAdmin.getRoleName());
			stringObjectHashMap.put("id",cmsAdmin.getId());
			return Tools.s(stringObjectHashMap);
		}else {
			logger.info("sign-in failed,password is wrong");
			return Tools.f("password is wrong");
		}
	}

	@ResponseBody
	@RequestMapping(value="/reset-pwd", method = RequestMethod.POST)
	public Map<String, Object> resetPwd(@RequestParam(value = "id", required = false)Integer id){

		CmsAdmin cmsAdmin = cmsAdminMapper.SelectAdminById(id);

		if(cmsAdmin == null){
			return Tools.f("user doesn't exist!");
		}

		String loginName = cmsAdmin.getLoginName();
		String plain = cmsAdmin.getPlain();
		String pwd = MD5Util.crypt(loginName+plain);

		cmsAdmin.setPassword(pwd);
		cmsAdminMapper.update(cmsAdmin);
		return Tools.s();
	}

	@ResponseBody
	@RequestMapping(value="/edit", method = RequestMethod.POST)
	public Map<String, Object> edit(@RequestParam(value = "id", required = false)Integer id,@RequestParam(value = "userName", required = false)String userName,
									@RequestParam(value = "loginName", required = false)String loginName,@RequestParam(value = "roleName", required = false)String roleName){

		CmsAdmin cmsAdmin = new CmsAdmin();
		cmsAdmin.setId(id);
		cmsAdmin.setLoginName(loginName);
		cmsAdmin.setUserName(userName);
		cmsAdmin.setRoleName(roleName);
		cmsAdminMapper.updateByPrimaryKey(cmsAdmin);
		return Tools.s();
	}

	@ResponseBody
	@RequestMapping(value="/set-pwd", method = RequestMethod.POST)
	public Map<String, Object> setPwd(@RequestParam(value = "id", required = false)Integer id,@RequestParam(value = "password",required = false)String password){

		CmsAdmin cmsAdmin = cmsAdminMapper.SelectAdminById(id);

		if(cmsAdmin == null){
			return Tools.f("user doesn't exist!");
		}


		String plain = cmsAdmin.getPlain();
		String pwd = MD5Util.crypt(password+plain);

		cmsAdmin.setPassword(pwd);
		cmsAdminMapper.update(cmsAdmin);
		return Tools.s();
	}

	@ResponseBody
	@RequestMapping(value="/del", method = RequestMethod.POST)
	public Map<String, Object> del(@RequestParam(value = "id", required = false)Integer id){

		cmsAdminMapper.delete(id);

		return Tools.s();
	}

	@ResponseBody
	@RequestMapping(value="/list", method = RequestMethod.POST)
	public Map<String, Object> list(){

		List<CmsAdmin> cmsAdmins = cmsAdminMapper.SelectAll();

		return Tools.s(cmsAdmins);
	}

	@ResponseBody
	@RequestMapping(value="/info", method = RequestMethod.POST)
	public Map<String, Object> list(@RequestParam(value = "id", required = false)Integer id){

		CmsAdmin cmsAdmin = cmsAdminMapper.SelectAdminById(id);

		return Tools.s(cmsAdmin);
	}


	@ResponseBody
	@RequestMapping(value="/is-sign-in", method = RequestMethod.POST)
	public Map<String, Object> isSignIn(ServletRequest request, ServletResponse response){

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		CmsAdmin cmsAdminInfo = null;
		cmsAdminInfo = (CmsAdmin) req.getSession().getAttribute(CmsAdmin.SESSION_SQUIRREL_ADMIN_KEY);
		if(cmsAdminInfo == null){
			return Tools.f();
		}
		Integer id = cmsAdminInfo.getId();
		String password = cmsAdminInfo.getPassword();

		CmsAdmin cmsAdmin = cmsAdminMapper.SelectAdminByIdAndPwd(id, password);

		if(cmsAdmin == null){
			return Tools.f();
		}

		return Tools.s();
	}

}
