package com.ycdc.task.serv;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 定时查询所有有效合租合约信息，入库订单表
 * 
 * @author 孙诚明
 * @version 1.0
 * @date 2016年12月7日上午9:52:27
 */
public interface OrderTaskServ
{
	/**
	 * 手工触发例行保洁订单入库（老版本服务模块）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	Object artificial(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 手工触发例行保洁订单入库（新版本工作流服务模块）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	Object newArtificial(HttpServletRequest request, HttpServletResponse response);

	/**
	 * 手工触发单个房间例行保洁订单入库（新版本工作流服务模块）
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	Object singleNewArtificial(HttpServletRequest request, HttpServletResponse response);
}
