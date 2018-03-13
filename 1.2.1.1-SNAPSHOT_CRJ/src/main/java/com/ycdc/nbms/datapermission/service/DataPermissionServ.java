package com.ycdc.nbms.datapermission.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ycdc.nbms.datapermission.model.DataPermissionRequest;
import com.ycdc.nbms.datapermission.model.DataPermissionResponse;

/**
 * 数据权限配置
 * 
 * @author sunchengming
 * @date 2017年9月20日 下午2:44:30
 * 
 */
public interface DataPermissionServ
{
	/**
	 * 新增或者更改用户的数据权限
	 * 
	 * @param req
	 *          入参
	 * @return 成功或者失败
	 */
	int udpatePermissionInfo(HttpServletRequest request, DataPermissionRequest req);

	/**
	 * 根据角色获取是否有导出按钮显示的权限
	 * 
	 * @param request
	 * @param req
	 * @return
	 */
	int judgeExportButton(HttpServletRequest request, DataPermissionRequest req);

	/**
	 * 根据角色id查询数据权限信息
	 * 
	 * @param req
	 *          入参（角色id）
	 * @return 数据权限信息
	 */
	DataPermissionResponse getPermissionsInfo(DataPermissionRequest req);

	/**
	 * 根据角色，查询权限
	 * 
	 * @param roleIds
	 * @param typeId
	 * @return
	 */
	List<Map<String, Object>> getPermissionsInfoByRoleIds(String roleIds, int typeId);
}
