package com.ycdc.nbms.datapermission.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ycdc.nbms.datapermission.model.DataPermission;
import com.ycdc.nbms.datapermission.model.DataPermissionRequest;

/**
 * 数据配置
 * 
 * @author sunchengming
 * @date 2017年9月20日 下午4:21:22
 * 
 */
public interface DataPermissionMapper
{

	/**
	 * 根据角色id查询数据权限信息
	 * 
	 * @param req
	 *          入参
	 * @return 数据权限信息
	 */
	List<DataPermission> getPermissionsInfo(DataPermissionRequest req);

	/**
	 * 根据role_id查询是否存在记录
	 * 
	 * @param role_id
	 *          角色id
	 * @return
	 */
	int getRecordTotalByRoleId(@Param(value = "role_id") int role_id);

	/**
	 * 批量插入权限信息
	 * 
	 * @param datas
	 *          权限信息
	 * @param role_id
	 *          角色id
	 * @param user_id
	 *          登录用户id
	 * @return
	 */
	int batchInsertPermissions(@Param(value = "datas") List<DataPermission> datas, @Param(value = "role_id") int role_id,
			@Param(value = "creater_id") int user_id);

	/**
	 * 批量更改权限信息
	 * 
	 * @param datas
	 *          权限信息
	 * @param role_id
	 *          角色id
	 * @param user_id
	 *          登录用户id
	 * @return
	 */
	int batchUpdatePermissions(@Param(value = "datas") List<DataPermission> datas, @Param(value = "role_id") int role_id,
			@Param(value = "creater_id") int user_id);

	/**
	 * 根据角色id查询菜单权限
	 * 
	 * @param req
	 * @return
	 */
	List<Map<String, Object>> getMenuInfo(DataPermissionRequest req);

	/**
	 * 根据角色，查询权限
	 * 
	 * @param roleIds
	 * @return
	 */
	List<Map<String, Object>> getPermissionsInfoByRoleIds(@Param(value = "roleIds") String roleIds,
			@Param(value = "typeId") int typeId);

	/**
	 * 根据角色信息，查询导出权限
	 * 
	 * @param roleIds
	 * @return
	 */
	int getExportInfoByRoleIds(@Param(value = "roleIds") String roleIds);

	/**
	 * 根据角色id和类型id查看是否有记录
	 * 
	 * @param role_id
	 * @param type_id
	 * @param string 
	 * @return
	 */
	int getTotalByRoleId(@Param(value = "role_id") int role_id, @Param(value = "type_id") int type_id, @Param(value = "sub_type_id") String sub_type_id);

	/**
	 * 插入没有记录的信息
	 * 
	 * @param role_id
	 * @param user_id
	 * @param data
	 * @return
	 */
	int insertPermissions(DataPermission data);

}
