package com.room1000.cascading.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.room1000.cascading.service.CascadingService;
import com.ycdc.core.base.BaseController;

/**
 * 下拉框级联查询
 * 
 * @author sunchengming
 * @date 2017年4月14日
 * @version 1.0
 *
 */
@Controller
@RequestMapping("/cascading")
public class CascadingController extends BaseController
{

	/**
	 * serv
	 */
	@Resource
	private CascadingService serv;

	/**
	 * 根据权限和订单类型查询指派人列表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getUserListByAuthority.do")
	public void getUserListByAuthority(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeJsonData(serv.getUserListByAuthority(request, response), response);
	}

	/**
	 * 根据区域的ID，查询所属小区
	 * 
	 * @param request
	 *          request
	 * @param response
	 *          response
	 */
	@RequestMapping(value = "/groupList.do")
	public void groupList(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeJsonData(serv.groupList(request, response), response);
	}

	/**
	 * 根据区域的ID，查询所属管家
	 * 
	 * @param request
	 *          request
	 * @param response
	 *          response
	 */
	@RequestMapping(value = "/butlerList.do")
	public void butlerList(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeJsonData(serv.butlerList(request, response), response);
	}

	/**
	 * 根据任务编码，查询被推荐人信息
	 * 
	 * @param request
	 *          request
	 * @param response
	 *          response
	 */
	@RequestMapping(value = "/recommendInfo.do")
	public void recommendInfo(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeJsonData(serv.recommendInfo(request, response), response);
	}

	/**
	 * 获取第三方号码和回调地址
	 * 
	 * @param request
	 *          request
	 * @param response
	 *          response
	 */
	@RequestMapping(value = "/getCallBackInfo.do")
	public void getCallBackInfo(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeJsonData(serv.getCallBackInfo(request, response), response);
	}
}
