package com.ycdc.task.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ycdc.core.base.BaseController;
import com.ycdc.task.serv.OrderTaskServ;

/**
 * 定时查询所有有效合租合约信息，入库订单表
 * 
 * @author 孙诚明
 * @version 1.0
 * @date 2016年12月7日上午9:52:27
 */
@Controller
public class OrderTaskAction extends BaseController
{

	@Resource
	private OrderTaskServ serv;
	
	/**
	 * 手工触发例行保洁订单入库（老版本服务模块）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "task/orderTask.do")
	public void qaView(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeText(serv.artificial(request,response), response);
	}
	

	/**
	 * 手工触发例行保洁订单入库（新版本工作流服务模块）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "task/newOrderTask.do")
	public void newQaView(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeText(serv.newArtificial(request,response), response);
	}
	
	/**
	 * 手工触发单个房间例行保洁订单入库（新版本工作流服务模块）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "task/singleNewOrderTask.do")
	public void singleNewQaView(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeText(serv.singleNewArtificial(request,response), response);
	}
}
