package com.ycdc.appserver.model.syscfg;

/**
 * 区域
 * @author suntf
 * @date 2016年12月1日
 */
public class Area
{
	private String id;
	
	private String area_name;
	
	private String area_type;

	/**
	 * @return the area_type
	 */
	public String getArea_type()
	{
		return area_type;
	}

	/**
	 * @param area_type the area_type to set
	 */
	public void setArea_type(String area_type)
	{
		this.area_type = area_type;
	}

	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return the area_name
	 */
	public String getArea_name()
	{
		return area_name;
	}

	/**
	 * @param area_name the area_name to set
	 */
	public void setArea_name(String area_name)
	{
		this.area_name = area_name;
	}
}
