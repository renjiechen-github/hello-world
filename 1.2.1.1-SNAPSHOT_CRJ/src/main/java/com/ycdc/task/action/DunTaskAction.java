package com.ycdc.task.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ycdc.core.base.BaseController;
import com.ycdc.task.serv.DunTaskServ;

/**
 * 定时查询所有待付款详情，发送短信进行催租操作
 * 待付款信息查询：按照计划付款时间减去10天或者15天后，等于当前查询日期的所有信息
 * 
 * @author 孙诚明
 * @version 1.0
 * @date 2017年4月12日上午9:52:27
 */
@Controller
public class DunTaskAction extends BaseController
{

	@Resource
	private DunTaskServ serv;
	
	/**
	 * 手工触发催租操作
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "task/dunTask.do")
	public void dunTask(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeText(serv.artificial(request,response), response);
	}
}
