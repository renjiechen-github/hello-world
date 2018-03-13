package com.ycdc.nbms.report.serv;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 收支金额统计
 * 
 * @author 孙诚明
 * @since 2016年9月29日
 */
public interface PrRepServ
{

	/**
	 * 按类型和分类查询（报表展示）
	 * 
	 * @param request
	 * @return
	 */
	Object prView(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 查询财务信息（首页展示）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	Object prViewCost(HttpServletRequest request, HttpServletResponse response);

}
