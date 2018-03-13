package com.ycdc.core.plugin.lbs.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ycdc.core.base.BaseController;
import com.ycdc.core.plugin.lbs.PlaceSearch;

/**
 * 百度 Place圆形区域检索
 * 
 * @author 孙诚明
 * @date 2017年2月23日
 */
@Controller
public class PlaceSearchAction extends BaseController
{
	@Resource
	private PlaceSearch serv;

	/**
	 * 全量更新小区坐标
	 * 
	 * @param response
	 */
	@RequestMapping("/lbs/updateLocationByAll.do")
	public void updateLocationByAll(HttpServletResponse response)
	{
		serv.updateLocationByAll();
	}
	
	/**
	 * 根据小区ID，更新小区坐标
	 * @param response
	 */
	@RequestMapping("/lbs/updateLocationById.do")
	public void updateLocationById(HttpServletResponse response)
	{
		String id = request.getParameter("id");
		String str = serv.updateLocationById(id);
		logger.info("小区坐标 = " + str);
	}

	/**
	 * 全量更新小区周边交通信息
	 * 
	 * @param response
	 */
	@RequestMapping("/lbs/getRrafficInfoByAll.do")
	public void getRrafficInfoByAll(HttpServletResponse response)
	{
		// 根据小区ID，获取小区周边交通信息
		serv.getRrafficInfoByAll();
	}
	
	/**
	 * 根据小区ID，获取交通信息，并入库更新
	 * 
	 * @param response
	 */
	@RequestMapping("/lbs/getRrafficInfoById.do")
	public void getRrafficInfoById(HttpServletResponse response)
	{
		String id = request.getParameter("id");
		// 根据小区ID，获取小区周边交通信息
		String str = serv.getRrafficInfoById(id);
		logger.info("小区周边交通信息 = " + str);
	}
}
