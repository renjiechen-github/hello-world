package com.room1000.cascading.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 下拉框级联查询
 * 
 * @author sunchengming
 * @date 2017年4月14日
 * @version 1.0
 *
 */
public interface CascadingService
{

	/**
	 * 根据区域的ID，查询所属小区
	 * 
	 * @param request
	 *          request
	 * @param response
	 *          response
	 * @return Object
	 */
	Object groupList(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 根据区域的ID，查询所属管家
	 * 
	 * @param request
	 *          request
	 * @param response
	 *          response
	 * @return Object
	 */
	Object butlerList(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 根据任务编码，查询被推荐人信息
	 * 
	 * @param request
	 *          request
	 * @param response
	 *          response
	 * @return Object
	 */
	Object recommendInfo(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 获取第三方号码和回调地址
	 * 
	 * @param request
	 *          request
	 * @param response
	 *          response
	 * @return Object
	 */
	Object getCallBackInfo(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 获取指派人
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	Object getUserListByAuthority(HttpServletRequest request, HttpServletResponse response);

}
