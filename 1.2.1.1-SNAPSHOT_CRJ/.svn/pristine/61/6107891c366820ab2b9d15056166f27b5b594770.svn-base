package com.ycdc.nbms.datapermission.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ycdc.core.base.BaseController;
import com.ycdc.nbms.datapermission.model.DataPermissionRequest;
import com.ycdc.nbms.datapermission.model.DataPermissionResponse;
import com.ycdc.nbms.datapermission.service.DataPermissionServ;

/**
 * 数据权限配置
 * 
 * @author sunchengming
 * @date 2017年9月20日 下午2:40:47
 * 
 */
@Controller
@RequestMapping("/datapermission")
public class DataPermissionAction extends BaseController
{
	@Autowired
	private DataPermissionServ dataServ;

	/**
	 * 根据角色id查询数据权限信息
	 * 
	 * @param req
	 *          入参（角色id）
	 * @return 数据权限信息
	 */
	@RequestMapping("/getPermissionsInfo.do")
	@ResponseBody
	public DataPermissionResponse getPermissionsInfo(@RequestBody DataPermissionRequest req)
	{
		return dataServ.getPermissionsInfo(req);
	}

	/**
	 * 新增或者更改用户的数据权限
	 * 
	 * @param req
	 *          入参
	 * @return 成功或者失败
	 */
	@RequestMapping("/udpatePermissionInfo.do")
	@ResponseBody
	public int udpatePermissionInfo(HttpServletRequest request, @RequestBody DataPermissionRequest req)
	{
		return dataServ.udpatePermissionInfo(request, req);
	}
	
	/**
	 * 根据角色获取是否有导出按钮显示的权限
	 * @param request
	 * @param req
	 * @return
	 */
	@RequestMapping("/judgeExportButton.do")
	@ResponseBody
	public int judgeExportButton(HttpServletRequest request, @RequestBody DataPermissionRequest req)
	{
		return dataServ.judgeExportButton(request, req);
	}
	
	/**
	 * 新增或者更改用户的数据权限
	 * 
	 * @param roleIds 角色Id（11,22,33）
	 * @param roleIds 权限类型id（1:订单，2:合约）
	 * @return 
	 */
	@RequestMapping("/getPermissionsInfoByRoleIds.do")
	public void getPermissionsInfoByRoleIds(HttpServletRequest request, HttpServletResponse response)
	{
		String roleIds = request.getParameter("roleIds");
		int typeId = Integer.valueOf(request.getParameter("typeId"));
		writeJsonData(dataServ.getPermissionsInfoByRoleIds(roleIds,typeId), response);
	}
}
