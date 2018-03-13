package com.ycdc.appserver.model.house;

import java.util.List;

import com.ycdc.appserver.model.syscfg.DictiItem;

/**
 * 
 * @author suntf
 * @date 2016年12月1日
 */
public class HouseSelectInfo
{
	/**
	 * 房源装修类型
	 */
	private List<DictiItem> decorateList;
	
	/**
	 * 室
	 */
	private List<DictiItem> shiList;
	
	/**
	 * 厅
	 */
	private List<DictiItem> tingList;
	
	/**
	 * 卫
	 */
	private List<DictiItem> weiList;
	
	/**
	 * 厨房
	 */
	private List<DictiItem> chuList;
	
	/**
	 * 阳台
	 */
	private List<DictiItem> yangList;
	
	/**
	 * 电梯
	 */
	private List<DictiItem> leftList;
	
	/**
	 * 用途
	 */
	private List<DictiItem> purposeList;
	
	/**
	 * 出租年限
	 */
	private List<DictiItem> rentList;
	
	/**
	 * 支付类型
	 */
	private List<DictiItem> payTypeList;

	/**
	 * @return the decorateList
	 */
	public List<DictiItem> getDecorateList()
	{
		return decorateList;
	}

	/**
	 * @param decorateList the decorateList to set
	 */
	public void setDecorateList(List<DictiItem> decorateList)
	{
		this.decorateList = decorateList;
	}

	/**
	 * @return the shiList
	 */
	public List<DictiItem> getShiList()
	{
		return shiList;
	}

	/**
	 * @param shiList the shiList to set
	 */
	public void setShiList(List<DictiItem> shiList)
	{
		this.shiList = shiList;
	}

	/**
	 * @return the tingList
	 */
	public List<DictiItem> getTingList()
	{
		return tingList;
	}

	/**
	 * @param tingList the tingList to set
	 */
	public void setTingList(List<DictiItem> tingList)
	{
		this.tingList = tingList;
	}

	/**
	 * @return the weiList
	 */
	public List<DictiItem> getWeiList()
	{
		return weiList;
	}

	/**
	 * @param weiList the weiList to set
	 */
	public void setWeiList(List<DictiItem> weiList)
	{
		this.weiList = weiList;
	}

	/**
	 * @return the rentList
	 */
	public List<DictiItem> getRentList()
	{
		return rentList;
	}

	/**
	 * @param rentList the rentList to set
	 */
	public void setRentList(List<DictiItem> rentList)
	{
		this.rentList = rentList;
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
	 * @return the chuList
	 */
	public List<DictiItem> getChuList()
	{
		return chuList;
	}

	/**
	 * @param chuList the chuList to set
	 */
	public void setChuList(List<DictiItem> chuList)
	{
		this.chuList = chuList;
	}

	/**
	 * @return the yangList
	 */
	public List<DictiItem> getYangList()
	{
		return yangList;
	}

	/**
	 * @param yangList the yangList to set
	 */
	public void setYangList(List<DictiItem> yangList)
	{
		this.yangList = yangList;
	}

	/**
	 * @return the leftList
	 */
	public List<DictiItem> getLeftList()
	{
		return leftList;
	}

	/**
	 * @param leftList the leftList to set
	 */
	public void setLeftList(List<DictiItem> leftList)
	{
		this.leftList = leftList;
	}

	/**
	 * @return the purposeList
	 */
	public List<DictiItem> getPurposeList()
	{
		return purposeList;
	}

	/**
	 * @param purposeList the purposeList to set
	 */
	public void setPurposeList(List<DictiItem> purposeList)
	{
		this.purposeList = purposeList;
	}
}
