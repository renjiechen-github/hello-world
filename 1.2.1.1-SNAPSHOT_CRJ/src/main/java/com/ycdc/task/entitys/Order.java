package com.ycdc.task.entitys;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author 孙诚明
 * @version 1.0
 * @date 2016年12月7日上午10:31:03
 */
public class Order
{
	// 主键ID
	private int id;
	// 房源名称
	private String name;
	// 订单代码
	private String order_code;
	// 用户ID
	private String user_id;
	// 用户名称
	private String user_name;
	// 手机号
	private String user_mobile;
	// 合约ID
	private String correspondent;
	// 区域名称
	private String area_name;
	// 描述
	private String order_desc;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getOrder_code()
	{
		return order_code;
	}

	public void setOrder_code(String order_code)
	{
		this.order_code = order_code;
	}

	public String getUser_id()
	{
		return user_id;
	}

	public void setUser_id(String user_id)
	{
		this.user_id = user_id;
	}

	public String getUser_name()
	{
		return user_name;
	}

	public void setUser_name(String user_name)
	{
		this.user_name = user_name;
	}

	public String getUser_mobile()
	{
		return user_mobile;
	}

	public void setUser_mobile(String user_mobile)
	{
		this.user_mobile = user_mobile;
	}

	public String getCorrespondent()
	{
		return correspondent;
	}

	public void setCorrespondent(String correspondent)
	{
		this.correspondent = correspondent;
	}

	public String getArea_name()
	{
		return area_name;
	}

	public void setArea_name(String area_name)
	{
		this.area_name = area_name;
	}

	public String getOrder_desc()
	{
		return order_desc;
	}

	public void setOrder_desc(String order_desc)
	{
		this.order_desc = order_desc;
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
