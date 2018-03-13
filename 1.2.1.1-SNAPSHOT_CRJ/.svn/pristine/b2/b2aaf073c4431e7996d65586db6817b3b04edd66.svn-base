package com.ycdc.task.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ycdc.task.entitys.Order;

/**
 * @author 孙诚明
 * @version 1.0
 * @date 2016年12月7日上午10:19:56
 */
public interface OrderTaskDAO
{

	/**
	 * 按照条件查询信息
	 * 
	 * @return
	 */
	List<Order> getUserInfo();

	/**
	 * 单个房间信息
	 * 
	 * @param id
	 * 
	 * @return
	 */
	List<Order> getSingleUserInfo(@Param(value = "id") String id);

	/**
	 * 插入数据，并返回主键
	 * 
	 * @param order
	 */
	int insertAndGetId(Order order);

	/**
	 * 更改订单代码
	 * 
	 * @param order
	 */
	int updateOrderCode(Order order);

	/**
	 * 校验数据是否存在
	 * 
	 * @param order
	 * @return
	 */
	int isExist(Order order);

}
