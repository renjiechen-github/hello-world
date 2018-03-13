package com.ycdc.task.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ycdc.task.entitys.Dun;

/**
 * 定时查询所有待付款详情，发送短信进行催租操作
 * 
 * @author 孙诚明
 * @version 1.0
 * @date 2017年4月12日上午9:52:27
 */
public interface DunTaskDAO
{
	/**
	 * 按照条件查询信息
	 * 
	 * @return
	 */
	List<Dun> getDunInfo(@Param(value = "querytime") String querytime);
}
