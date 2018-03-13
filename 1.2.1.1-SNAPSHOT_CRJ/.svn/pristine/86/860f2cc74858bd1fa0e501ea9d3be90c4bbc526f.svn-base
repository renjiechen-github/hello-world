package com.room1000.cascading.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 下拉框级联查询
 * 
 * @author sunchengming
 * @date 2017年4月14日
 * @version 1.0
 *
 */
public interface CascadingDao
{

	/**
	 * 根据区域的ID，查询所属小区
	 * 
	 * @param areaId
	 *          areaId
	 * @return List<Map<String, String>>
	 */
	List<Map<String, String>> groupList(@Param(value = "areaId") String areaId);

	/**
	 * 根据区域的ID，查询所属管家
	 * 
	 * @param areaId
	 *          areaId
	 * @param rankId
	 * @return List<Map<String, String>>
	 */
	List<Map<String, String>> butlerList(@Param(value = "areaId") String areaId, @Param(value = "rankId") String rankId, @Param(value = "groupId") String groupId);

	/**
	 * 根据小区的ID，查询所属管家
	 * 
	 * @param groupId
	 *          groupId
	 * @return Map<String, String>
	 */
	List<Map<String, String>> butlerInfo(@Param(value = "groupId") String groupId);

	/**
	 * 根据任务编码，查询被推荐人信息
	 * 
	 * @param code
	 *          code
	 * @return Map<String, String>
	 */
	Map<String, Object> recommendInfo(@Param(value = "code") String code);

	/**
	 * 根据houserank id，获取管家名称
	 * 
	 * @param id
	 *          id
	 * @return List<Map<String, String>>
	 */
	List<Map<String, String>> getbutlerInfo(String id);

	/**
	 * 获取第三方号码和回调地址
	 * 
	 * @return List<Map<String, String>>
	 */
	List<Map<String, String>> getCallBackInfo();

	/**
	 * 获取员工列表
	 * 
	 * @param roleId
	 * @param type
	 * @return
	 */
	List<Map<String, String>> getUserListByAuthority(@Param(value = "roleId") String roleId,
			@Param(value = "type") String type);

	/**
	 * 查询所有用户
	 * 
	 * @return
	 */
	List<Map<String, String>> getUserAll();

}
