package com.ycdc.nbms.report.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import pccom.web.action.BaseController;

import com.ycdc.nbms.report.serv.HouseInfoServ;


/**
 * 房源信息查询
 * 
 * @author 孙诚明
 * @version 1.0
 * @since 2016年10月18日
 * @category com.ycdc.nbms.report.action
 */
@Controller
public class HouseInfoAction extends BaseController
{
	@Resource
	private HouseInfoServ serv;

	/**
	 * 按照条件查询房源信息
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "report/queryHouseInfo.do")
	public void queryHouseInfo(HttpServletResponse response)
	{
		serv.queryHouseInfo(request, response);
	}
}
