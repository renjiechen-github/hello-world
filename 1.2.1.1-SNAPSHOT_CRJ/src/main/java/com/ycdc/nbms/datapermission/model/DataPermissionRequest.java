package com.ycdc.nbms.datapermission.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 数据配置
 * 
 * @author sunchengming
 * @date 2017年9月20日 下午3:07:58
 * 
 */
public class DataPermissionRequest implements Serializable
{
	private static final long serialVersionUID = -1449685207443745725L;

	/**
	 * 角色主键
	 */
	int role_id;

	List<DataPermission> data;

	public int getRole_id()
	{
		return role_id;
	}

	public void setRole_id(int role_id)
	{
		this.role_id = role_id;
	}

	public List<DataPermission> getData()
	{
		return data;
	}

	public void setData(List<DataPermission> data)
	{
		this.data = data;
	}

	/**
	 * 格式化输出
	 */
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
