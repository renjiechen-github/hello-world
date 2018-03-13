package com.ycdc.nbms.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 品控数据指标
 * 
 * @author 孙诚明
 * @since 2016年12月2日
 */
public interface QARepDao
{

	/**
	 * 每月看房预约量
	 * 
	 * @param date
	 * @return
	 */
	int getMonthReserve(@Param(value = "date") String date);

	/**
	 * 每月报修量
	 * 
	 * @param date
	 * @return
	 */
	int getMonthMaintenance(@Param(value = "date") String date);

	/**
	 * 每月投诉量
	 * 
	 * @param date
	 * @return
	 */
	int getMonthComplaint(@Param(value = "date") String date);

	/**
	 * 每月闭环量
	 * 
	 * @param date
	 * @return
	 */
	int getMonthCloseCycle(@Param(value = "date") String date);
	
	/**
	 * 订单总数（过滤例行保洁）
	 * 
	 * @param date
	 * @return
	 */
	int getOrderTotal(@Param(value = "date") String date);

	/**
	 * 服务处理总天数（过滤例行保洁）
	 * 
	 * @param date
	 * @return
	 */
	int getMonthPeriod(@Param(value = "date") String date);

	/**
	 * 未收款合同数
	 * 
	 * @return
	 */
	int getNoGatherAgreement();

	/**
	 * 看房订单（预约订单）
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getReserves(Map<String, Object> params);

	/**
	 * 维修订单
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getMaintenances(Map<String, Object> params);

	/**
	 * 保洁订单
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getCleanings(Map<String, Object> params);

	/**
	 * 例行保洁
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getRoutineCleanings(Map<String, Object> params);

	/**
	 * 投诉订单
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getComplaints(Map<String, Object> params);

	/**
	 * 其他订单
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getOthers(Map<String, Object> params);

	/**
	 * 入住问题
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getCheckIns(Map<String, Object> params);

	/**
	 * 退租订单
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getRefunds(Map<String, Object> params);

	/**
	 * 处理周期
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getPeriods(Map<String, Object> params);

	/**
	 * 按分类查询订单
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getCategoryData(Map<String, Object> params);

	/**
	 * 分类查询出来周期
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getCategoryPeriod(Map<String, Object> params);

}
