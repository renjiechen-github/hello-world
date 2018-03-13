
package com.ycdc.appserver.bus.controller.user;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.yc.rm.caas.appserver.bus.service.team.ITeamServ;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycdc.appserver.base.interceptor.LoggedInterceptor;
import com.ycdc.appserver.bus.controller.BaseController;
import com.ycdc.appserver.bus.service.user.IUserService;
import com.ycdc.appserver.model.user.User;

@Controller
@RequestMapping("appserver")
public class UserLoginController extends BaseController
{

	@Autowired
	private IUserService userLoginService;
	@Autowired
	private ITeamServ _teamServImpl;

	/**
	 * 登录
	 * 
	 * @param person
	 * @return
	 */
	@RequestMapping(value = "/person/login", method = RequestMethod.POST)
	public @ResponseBody
	User login(@RequestBody User user, HttpServletRequest request)
	{
		log.debug("into login");
		User u = userLoginService.checkLogin(user, request);
		if (u.getId() != null) {
			u.setTeamIds(_teamServImpl.getTeamIdsByCharge(new Integer(u.getId())));
			if (u.getTeamIds() == null || u.getTeamIds().size() <= 0) {
				u.setTeamIds(null);
			}
		}
		log.debug("user:" + com.alibaba.fastjson.JSONObject.toJSONString(u));
		return u;
	}

	/**
	 * 获取出租用户类别信息
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping(value = "/person/loadUserList.do", method = RequestMethod.POST)
	public @ResponseBody
	Object loadUserList(@RequestBody JSONObject json, HttpServletRequest request)
	{
		return userLoginService.loadUserList(json, request);
	}
}
