package com.ycdc.nbms.report.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ycdc.core.base.BaseController;
import com.ycdc.nbms.report.serv.PrRepServ;

/**
 * 收支金额统计
 * 
 * @author 孙诚明
 * @since 2016年9月29日
 */
@Controller
public class PrRepAction extends BaseController
{

	@Resource
	private PrRepServ serv;

	/**
	 * 查询财务信息（首页展示）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "report/prViewCost.do")
	public void prView(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeJsonData(serv.prViewCost(request, response), response);
	}

	/**
	 * 按类型和分类查询（报表展示）
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "report/prView.do")
	public void mktView(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeJsonData(serv.prView(request, response), response);
	}
}
