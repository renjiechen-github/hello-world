package com.ycdc.nbms.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 房源信息查询
 * 
 * @author 孙诚明
 * @version 1.0
 * @since 2016年12月15日
 * @category com.ycdc.nbms.report.dao
 */
public interface HouseInfoDao
{
	/**
	 * 按照条件查询房源信息
	 * 
	 * @param object
	 * @return
	 */
	List<Map<String, String>> queryHouseInfo(Object object);

	/**
	 * 通过ID查询区域名称
	 * 
	 * @param areaId
	 * @return
	 */
	String getAreaById(@Param(value="areaId")String areaId);
}
