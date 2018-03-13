package com.ycdc.nbms.payDiscount.dao;

import java.util.List;
import java.util.Map;

import com.ycdc.nbms.payDiscount.pojo.PayDiscount;

public interface PayDiscountMapper {

	List<PayDiscount> queryPayCountList(Map<String, Object> map);
	
	/*
	 * 根据合约编码查询合约id
	 */
	String queryIdByCode(Map<String, Object> map);
	
	/*
	 * 修改代缴费优惠信息
	 */
	int modifyPayDiscount(Map<String, Object> map);
	
	/*
	 * 删除代缴费优惠信息
	 */
	int deletePayDiscount(Map<String, Object> map);
	
	/*
	 * 新增代缴费优惠信息
	 */
	int addPayDiscount(Map<String, Object> map);
}
