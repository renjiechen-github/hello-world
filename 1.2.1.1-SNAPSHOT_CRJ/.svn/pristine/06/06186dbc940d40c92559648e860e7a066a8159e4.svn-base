package com.ycdc.nbms.coupon.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.ycdc.bean.NextVal;
import com.ycdc.util.page.Page;

public interface CouponDao {

	/**
	 * 查询列表信息
	 * @param object
	 * @return
	 */
	List<Map<String, String>> selectQueryCoupon(Object object);
	
	
	/**
	 * 插入对应的卡券信息
	 * @return
	 */
	int addCoupon(Object object);
	
	/**
	 * 根据卡券获取对应的card编号
	 * @param object
	 * @return
	 */
	int getCardTypr(Object object);
	
	/**
	 * 更新对应状态信息
	 * 
	 * @param object
	 * @return
	 */
	int updateCoupon(Object object);
	
	/**
	 * 获取微信服务号对应的参数值
	 * @param object
	 * @return
	 */
	String getWxServerToken(Object object);
	
	/**
	 * 获取卡券编号
	 * @param object
	 * @return
	 */
	String getCardCode(Object object);
	
	/**
	 * 修改信息
	 * @param object
	 * @return
	 */
	int modifyCoupon(Object object);
	
	/**
	 * 获取卡券统计信息
	 * @param object
	 * @return
	 */
	List<Map<String, Object>> getDatacube(Object object);
	
	/**
	 * 插入卡券统计数据信息
	 * @param object
	 * @return
	 */
	int synDataCube(Object object);
	
}
