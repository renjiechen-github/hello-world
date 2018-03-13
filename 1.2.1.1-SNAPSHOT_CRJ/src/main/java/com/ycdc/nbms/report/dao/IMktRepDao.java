package com.ycdc.nbms.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface IMktRepDao
{
	/**
	 * 更改合约区域信息
	 * 
	 * @param map
	 * @return
	 */
	int updateAreaId(Map<String, Object> map);

	/**
	 * 获取所有区域信息
	 * 
	 * @return
	 */
	List<Map<String, String>> getAllAreaName();

	/**
	 * 查询12个月的当月收房量
	 * 
	 * @param dates
	 * @return
	 */
	List<Map<String, String>> selectMp(@Param(value = "dates") String[] dates);

	/**
	 * 查询12个月的当月出租量
	 * 
	 * @param dates
	 * @return
	 */
	List<Map<String, String>> selectMr(@Param(value = "dates") String[] dates);

	/**
	 * 查询12个月的当前房源量
	 * 
	 * @param dates
	 * @return
	 */
	List<Map<String, String>> selectYp(@Param(value = "dates") String[] dates);

	/**
	 * 查询12个月的当前出租量
	 * 
	 * @param dates
	 * @return
	 */
	List<Map<String, String>> selectYr(@Param(value = "dates") String[] dates);

	/**
	 * 查询12个月的收房总价
	 * 
	 * @param dates
	 * @param time
	 * @return
	 */
	List<Map<String, String>> selectYcr(@Param(value = "dates") String[] dates, @Param(value = "time") String time);

	/**
	 * 查询12个月的出租总价
	 * 
	 * @param dates
	 * @return
	 */
	List<Map<String, String>> selectYra(@Param(value = "dates") String[] dates);

	/**
	 * 获取收房同比、环比
	 * 
	 * @param date
	 * @return
	 */
	List<Map<String, String>> getHousingRes(@Param(value = "date") String date);

	/**
	 * 获取出租同比、环比
	 * 
	 * @param date
	 * @return
	 */
	List<Map<String, String>> getRentRes(@Param(value = "date") String date);

	/**
	 * 获取收房总天间
	 * 
	 * @param dates
	 * @return
	 */
	List<Map<String, String>> selectYpTotal(@Param(value = "dates") String[] dates);

	/**
	 * 获取出租总天间
	 * 
	 * @param dates
	 * @return
	 */
	List<Map<String, String>> selectYrTotal(@Param(value = "dates") String[] dates);

	/**
	 * 根据网格ID，获取当前收房
	 * 
	 * @param user_id
	 * @param date
	 * @return
	 */
	Map<String, String> selectYpById(@Param(value = "user_id") String user_id, @Param(value = "teamIds") String teamIds, @Param(value = "date") String date);

	/**
	 * 根据网格ID，获取当前出租
	 * 
	 * @param user_id
	 * @param date
	 * @return
	 */
	Map<String, String> selectYrById(@Param(value = "user_id") String user_id, @Param(value = "teamIds") String teamIds, @Param(value = "date") String date);

	/**
	 * 根据网格ID，获取收房总价
	 * 
	 * @param user_id
	 * @param date
	 * @param day
	 * @return
	 */
	Map<String, String> selectYcrById(@Param(value = "userId") String userId, @Param(value = "teamIds") String teamIds, @Param(value = "date") String date, @Param(value = "day") String day);

	/**
	 * 根据网格ID，获取出租总价
	 * 
	 * @param user_id
	 * @param date
	 * @return
	 */
	Map<String, String> selectYraById(@Param(value = "user_id") String user_id, @Param(value = "teamIds") String teamIds, @Param(value = "date") String date);

	/**
	 * 根据网格ID，获取收房总天间
	 * 
	 * @param user_id
	 * @param date
	 * @return
	 */
	Map<String, String> selectYpTotalById(@Param(value = "user_id") String user_id, @Param(value = "date") String date);

	/**
	 * 根据网格ID，获取出租总天间
	 * 
	 * @param user_id
	 * @param date
	 * @return
	 */
	Map<String, String> selectYrTotalById(@Param(value = "user_id") String user_id, @Param(value = "date") String date);

	/**
	 * 当月已出租房间的收房总价
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
	Map<String, String> selectMcr(@Param(value = "date") String date, @Param(value = "time") String time);

	/**
	 * 
	 * @param date
	 * @param time
	 * @return
	 */
	Map<String, String> selectMsf(@Param(value = "date") String date, @Param(value = "time") String time);

	/**
	 * 当月出租总价
	 * 
	 * @param dates
	 * @return
	 */
	List<Map<String, String>> selectMra(@Param(value = "dates") String[] dates);

	/**
	 * 获取当前有效房源量
	 * 
	 * @return
	 */
	Map<String, String> selectYpValid();

	/**
	 * 获取当前有效出租量
	 * 
	 * @return
	 */
	Map<String, String> selectYrValid();

}
