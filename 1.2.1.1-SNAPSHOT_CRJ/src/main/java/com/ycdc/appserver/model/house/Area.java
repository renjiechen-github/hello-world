package com.ycdc.appserver.model.house;

import org.apache.ibatis.type.Alias;

/**
 * 小区信息
 * @author suntf
 * @date 2016年12月20日
 */
public class Area
{
	private String id;
	
	private String areaid;
	
	private String group_name;
	
	private String coordinate;
	
	private String area_name;
	
	private String zones;

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
	 * @return the areaid
	 */
	public String getAreaid()
	{
		return areaid;
	}

	/**
	 * @param areaid the areaid to set
	 */
	public void setAreaid(String areaid)
	{
		this.areaid = areaid;
	}

	/**
	 * @return the group_name
	 */
	public String getGroup_name()
	{
		return group_name;
	}

	/**
	 * @param group_name the group_name to set
	 */
	public void setGroup_name(String group_name)
	{
		this.group_name = group_name;
	}

	/**
	 * @return the coordinate
	 */
	public String getCoordinate()
	{
		return coordinate;
	}

	/**
	 * @param coordinate the coordinate to set
	 */
	public void setCoordinate(String coordinate)
	{
		this.coordinate = coordinate;
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

	/**
	 * @return the zones
	 */
	public String getZones()
	{
		return zones;
	}

	/**
	 * @param zones the zones to set
	 */
	public void setZones(String zones)
	{
		this.zones = zones;
	}
}
