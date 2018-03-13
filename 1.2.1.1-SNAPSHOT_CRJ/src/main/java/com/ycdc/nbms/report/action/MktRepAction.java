package com.ycdc.nbms.report.action;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ycdc.core.base.BaseController;
import com.ycdc.nbms.report.serv.IMktRepServ;

@Controller
public class MktRepAction extends BaseController
{

	@Resource
	private IMktRepServ mktRepServ;

	/**
	 * 获取区域
	 * 
	 * @author 雷杨
	 * @param response
	 */
	@RequestMapping(value = "report/mktView.do")
	public void mktView(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeJsonData(mktRepServ.mktView(request), response);
	}

	/**
	 * 手工刷新报表
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "report/testMktView.do")
	public void testMktView(HttpServletRequest request, HttpServletResponse response)
	{
		this.writeJsonData(mktRepServ.createReport(), response);
	}

}
