package com.ycdc.nbms.report.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 工程数据指标
 * 
 * @author 孙诚明
 * @since 2016年11月30日
 */
public interface ProRepDao
{

	/**
	 * 管家数量（当前激活可用帐号数）
	 * 
	 * @return
	 */
	String getManTotal();

	/**
	 * 房源总套数
	 * 
	 * @return
	 */
	String getHouseSets();

	/**
	 * 总的施工完成天数
	 * 
	 * @return
	 */
	String getTotalDay();

	/**
	 * 总金额
	 * 
	 * @return
	 */
	String getTotalMoney();

	/**
	 * 开工量
	 * 
	 * @param start_date
	 * @param end_date
	 * @return
	 */
	List<Map<String, Object>> getConstruction(@Param(value = "start_date") String start_date, @Param(value = "end_date") String end_date);

	/**
	 * 全公司当前激活可用帐号数
	 * 
	 * @return
	 */
	String getManAll();

}
