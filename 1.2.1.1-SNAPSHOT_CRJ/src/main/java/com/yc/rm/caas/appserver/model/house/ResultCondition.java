package com.yc.rm.caas.appserver.model.house;

import java.util.List;

import com.yc.rm.caas.appserver.model.user.User;
import com.yc.rm.caas.appserver.model.syscfg.DictiItem;


/**
 * 返回条件结果
 * @author suntf
 * @date 2016年12月1日
 */
public class ResultCondition
{
	/**
	 * 区域
	 */
	private List<Area> areas;
	
	/**
	 * 管家
	 */
	private List<User> users;
	
	/**
	 * 状态
	 */
	private List<DictiItem> stateList;
	
	/**
	 * 发布状态
	 */
	private List<DictiItem> publishList;

	/**
	 * @return the areas
	 */
	public List<Area> getAreas()
	{
		return areas;
	}

	/**
	 * @param areas the areas to set
	 */
	public void setAreas(List<Area> areas)
	{
		this.areas = areas;
	}

	/**
	 * @return the users
	 */
	public List<User> getUsers()
	{
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<User> users)
	{
		this.users = users;
	}

	/**
	 * @return the stateList
	 */
	public List<DictiItem> getStateList()
	{
		return stateList;
	}

	/**
	 * @param stateList the stateList to set
	 */
	public void setStateList(List<DictiItem> stateList)
	{
		this.stateList = stateList;
	}

	/**
	 * @return the publishList
	 */
	public List<DictiItem> getPublishList()
	{
		return publishList;
	}

	/**
	 * @param publishList the publishList to set
	 */
	public void setPublishList(List<DictiItem> publishList)
	{
		this.publishList = publishList;
	}
}
