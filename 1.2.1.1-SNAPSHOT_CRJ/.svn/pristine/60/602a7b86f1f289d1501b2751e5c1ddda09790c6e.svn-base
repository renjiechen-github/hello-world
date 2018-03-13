package com.ycdc.nbms.report.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ycdc.core.base.BaseController;
import com.ycdc.nbms.report.serv.QARepServ;

/**
 * 品控指标统计
 * 
 * @author 孙诚明
 * @since 2016年9月29日
 */
@Controller
public class QARepAction extends BaseController
{

	@Resource
	private QARepServ serv;
	
	/**
	 * 按照时间查询品控指标（首页展示）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "report/qAView.do")
	public void qaView(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeJsonData(serv.qaView(request,response), response);
	}
	
	/**
	 * 按照时间查询品控指标（报表展示）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "report/qaViewTotal.do")
	public void qaViewTotal(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeJsonData(serv.qaViewTotal(request,response), response);
	}	
}
