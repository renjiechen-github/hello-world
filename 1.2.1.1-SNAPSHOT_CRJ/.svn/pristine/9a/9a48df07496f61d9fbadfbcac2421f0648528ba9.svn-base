package com.yc.rm.caas.appserver.base.login.service;

import java.util.Map;

import com.yc.rm.caas.appserver.model.user.User;

/**
 * @Description 用户登录
 * @author sunchengming
 * @date 2017年7月18日 下午2:16:30
 * 
 */
public interface ILoginServ
{
	/**
	 * 用户登录
	 * 
	 * @param user
	 * @return
	 */
	User searchLogin(User user);

	/**
	 * 加载菜单
	 * 
	 * @return
	 */
	Map<String, Object> selectUserMenuById(User user);

	/**
	 * 修改密码
	 * 
	 * @param user
	 * @return
	 */
	int modifyPassword(User user);
}
