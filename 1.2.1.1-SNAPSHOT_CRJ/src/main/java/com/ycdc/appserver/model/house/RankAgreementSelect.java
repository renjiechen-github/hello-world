package com.ycdc.appserver.model.house;

import java.util.List;
import java.util.Map;

import com.ycdc.appserver.model.syscfg.DictiItem;
import com.ycdc.appserver.model.user.User;

/**
 * 出租合约下拉选择
 * @author suntf
 * @date 2016年12月2日
 */
public class RankAgreementSelect
{
	List<DictiItem> payTypeList;
	
	List<DictiItem> rankDateList;
	
	List<User> managerList;
	
	List<DictiItem> list;
	
	List<Map<String, Object>> infos;
	
	List<DictiItem> rentDepositList;
	
	/**
	 * @param payTypeList
	 * @param rankDateList
	 * @param decorateList
	 * @param managerList
	 * @date 2016年12月2日
	 */
	public RankAgreementSelect(List<DictiItem> payTypeList, List<DictiItem> rankDateList, List<User> managerList, List<DictiItem> list, List<Map<String, Object>> infos, List<DictiItem> rentDepositList)
	{
		super();
		this.payTypeList = payTypeList;
		this.rankDateList = rankDateList; 
		this.managerList = managerList;
		this.list = list;
		this.infos = infos;
		this.rentDepositList = rentDepositList;
	}

	/**
	 * @return the payTypeList
	 */
	public List<DictiItem> getPayTypeList()
	{
		return payTypeList;
	}

	/**
	 * @param payTypeList the payTypeList to set
	 */
	public void setPayTypeList(List<DictiItem> payTypeList)
	{
		this.payTypeList = payTypeList;
	}

	/**
	 * @return the rankDateList
	 */
	public List<DictiItem> getRankDateList()
	{
		return rankDateList;
	}

	/**
	 * @param rankDateList the rankDateList to set
	 */
	public void setRankDateList(List<DictiItem> rankDateList)
	{
		this.rankDateList = rankDateList;
	}
 

	/**
	 * @return the managerList
	 */
	public List<User> getManagerList()
	{
		return managerList;
	}

	/**
	 * @param managerList the managerList to set
	 */
	public void setManagerList(List<User> managerList)
	{
		this.managerList = managerList;
	}

	/**
	 * @return the list
	 */
	public List<DictiItem> getList()
	{
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<DictiItem> list)
	{
		this.list = list;
	}

	public List<Map<String, Object>> getInfos()
	{
		return infos;
	}

	public void setInfos(List<Map<String, Object>> infos)
	{
		this.infos = infos;
	}

	public List<DictiItem> getRentDepositList()
	{
		return rentDepositList;
	}

	public void setRentDepositList(List<DictiItem> rentDepositList)
	{
		this.rentDepositList = rentDepositList;
	}
	
}
