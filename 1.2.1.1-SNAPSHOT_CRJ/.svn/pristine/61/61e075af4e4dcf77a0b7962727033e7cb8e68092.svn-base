package com.ycdc.nbms.datapermission.model;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 数据权限
 * 
 * @author sunchengming
 * @date 2017年9月20日 下午6:38:38
 * 
 */
public class DataPermissionResponse implements Serializable
{
	private static final long serialVersionUID = 6514582611100990906L;
	
	/**
	 * 状态
	 */
	int state;
	
	/**
	 * 数据集合
	 */
	List<DataPermission> data;

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
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
