package com.ycdc.nbms.report.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ycdc.core.base.BaseController;
import com.ycdc.nbms.report.serv.ProRepServ;

/**
 * 工程数据指标
 * 
 * @author 孙诚明
 * @since 2016年11月30日
 */
@Controller
public class ProRepAction extends BaseController
{

	@Resource
	private ProRepServ serv;
	
	/**
	 * 按照时间查询工程数据（首页展示）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "report/proView.do")
	public void proView(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeJsonData(serv.proView(request,response), response);
	}
	
	
	/**
	 * 按照时间查询工程数据（报表展示）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "report/proTotal.do")
	public void proTotal(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeJsonData(serv.proTotal(request,response), response);
	}	
}
