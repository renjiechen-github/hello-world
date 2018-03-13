package com.ycdc.nbms.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ycdc.util.page.Page;

/**
 * 收支金额统计
 * 
 * @author 孙诚明
 * @since 2016年9月29日
 */
public interface PrRepDao
{
	/**
	 * 按照时间查询计划支出的总金额
	 * 
	 * @param start_date
	 *          开始时间
	 * @param end_date
	 *          结束时间
	 * @param tableName
	 * @return
	 */
	List<Map<String, Object>> queryPayPlat(@Param(value = "start_date") String start_date,
			@Param(value = "end_date") String end_date, @Param(value = "table_name") String table_name);

	/**
	 * 按照时间查询计划支出的总金额
	 * 
	 * @param start_date
	 *          开始时间
	 * @param end_date
	 *          结束时间
	 * @return
	 */
	List<Map<String, Object>> queryPayPlatTest(@Param(value = "start_date") String start_date,
			@Param(value = "end_date") String end_date,
			@Param(value = "page") Page<Map<String, Object>> page);

	/**
	 * 按照实际时间查询支出总金额
	 * 
	 * @param start_date
	 *          开始时间
	 * @param end_date
	 *          结束时间
	 * @return
	 */
	List<Map<String, Object>> queryPayReal(@Param(value = "start_date") String start_date,
			@Param(value = "end_date") String end_date);

	/**
	 * 按照计划时间查询收入总金额
	 * 
	 * @param start_date
	 *          开始时间
	 * @param end_date
	 *          结束时间
	 * @return
	 */
	List<Map<String, Object>> queryReceivPlat(@Param(value = "start_date") String start_date,
			@Param(value = "end_date") String end_date);

	/**
	 * 按照实际时间查询收入总金额
	 * 
	 * @param start_date
	 *          开始时间
	 * @param end_date
	 *          结束时间
	 * @return
	 */
	List<Map<String, Object>> queryReceivReal(@Param(value = "start_date") String start_date,
			@Param(value = "end_date") String end_date);

	/**
	 * 按条件查询金额数据
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getCost(Map<String, Object> params);

	/**
	 * 按条件查询租金数据
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getLeaseCost(Map<String, Object> params);

	/**
	 * 查询物业费数据
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getEstateCost(Map<String, Object> params);

	/**
	 * 查询退款
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getRefundCost(Map<String, Object> params);

	/**
	 * 查询待缴费
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getWaitCost(Map<String, Object> params);

	/**
	 * 查询押金
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getDepositCost(Map<String, Object> params);

	/**
	 * 查询服务费
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getServiceCost(Map<String, Object> params);

	/**
	 * 查询家居
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getHomeCost(Map<String, Object> params);

	/**
	 * 查询家电
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getApplianceCost(Map<String, Object> params);

	/**
	 * 查询装修
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getDecorateCost(Map<String, Object> params);

	/**
	 * 查询其他
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getOtherCost(Map<String, Object> params);

	/**
	 * 查询水费
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getWaterCost(Map<String, Object> params);

	/**
	 * 查询电费
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getElectricCost(Map<String, Object> params);

	/**
	 * 查询燃气费
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getGasCost(Map<String, Object> params);

	/**
	 * 按分类查询
	 * 
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> getCategoryData(Map<String, Object> params);

	/**
	 * 查询应收、应付款项
	 * 
	 * @param params
	 * @return
	 */
	Map<String, Object> getViewCost(Map<String, Object> params);

}
