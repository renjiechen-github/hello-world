package com.ycdc.nbms.report.serv;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 房源信息查询
 * 
 * @author 孙诚明
 * @version 1.0
 * @since 2016年12月15日
 * @category com.ycdc.nbms.report.serv
 */
public interface HouseInfoServ
{

	/**
	 * 按照条件查询房源信息
	 * 
	 * @param request
	 * @param response
	 */
	void queryHouseInfo(HttpServletRequest request, HttpServletResponse response);
	
}
