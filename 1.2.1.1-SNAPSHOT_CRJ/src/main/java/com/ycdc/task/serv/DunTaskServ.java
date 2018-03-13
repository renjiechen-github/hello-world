package com.ycdc.task.serv;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定时查询所有待付款详情，发送短信进行催租操作
 * 
 * @author 孙诚明
 * @version 1.0
 * @date 2017年4月12日上午9:52:27
 */
public interface DunTaskServ
{
	/**
	 * 手工触发催租操作
	 * 
	 * @param request
	 * @param response
	 */
	Object artificial(HttpServletRequest request, HttpServletResponse response);
}
