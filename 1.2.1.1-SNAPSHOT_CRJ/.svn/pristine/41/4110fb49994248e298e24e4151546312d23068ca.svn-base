package com.ycdc.core.plugin.lbs.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 百度 Place圆形区域检索
 * 
 * @author 孙诚明
 * @date 2017年2月23日
 */
public interface PlaceSearchDAO
{
	/**
	 * 获取所有小区信息
	 * 
	 * @return
	 */
	List<Map<String, Object>> getGroupByAll();
	
	/**
	 * 根据小区ID，获取小区信息
	 * 
	 * @param id
	 *          小区ID
	 * @return
	 */
	Map<String, Object> getGroupById(@Param(value = "id") String id);

	/**
	 * 根据小区ID，更新小区坐标
	 * 
	 * @param map
	 * @return
	 */
	int updateLocationById(Map<String, Object> map);

	/**
	 * 根据小区ID，更新公交信息
	 * 
	 * @param id
	 *          小区ID
	 * @param traffic
	 *          公交信息JSON字符串
	 * @return
	 */
	int updateRrafficById(@Param(value = "id") String id, @Param(value = "traffic") String traffic);

}
