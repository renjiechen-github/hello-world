
package com.ycdc.appserver.bus.service.user;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

import com.ycdc.appserver.model.user.User;

public interface IUserService
{
	User checkLogin(User user, HttpServletRequest request);
	
	List<Map<String,String>> loadUserList(JSONObject json, HttpServletRequest request);
}
