package com.ycdc.core.plugin.jpush.controller.fo;

/**
 * 
 * @author Administrator (Ailk NO.)
 * @version 1.0
 * @since 2017年3月9日
 * @category com.ycdc.core.plugin.jpush.controller.fo
 * @copyright Ailk NBS-Network Mgt. RD Dept.
 *
 */
public class JPushFo
{
	private int ifSend=-1;
	private String model;
	private String beginTime;
	private String endTime;
	private String text;
	private int startPage=0;
	private int pageSize=25;
	public int getIfSend()
	{
		return ifSend;
	}
	public void setIfSend(int ifSend)
	{
		this.ifSend = ifSend;
	}
	public String getModel()
	{
		return model;
	}
	public void setModel(String model)
	{
		this.model = model;
	}
	public String getBeginTime()
	{
		return beginTime;
	}
	public void setBeginTime(String beginTime)
	{
		this.beginTime = beginTime;
	}
	public String getEndTime()
	{
		return endTime;
	}
	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}
	public String getText()
	{
		return text;
	}
	public void setText(String text)
	{
		this.text = text;
	}
	public int getStartPage()
	{
		return startPage;
	}
	public void setStartPage(int startPage)
	{
		this.startPage = startPage;
	}
	public int getPageSize()
	{
		return pageSize;
	}
	public void setPageSize(int pageSize)
	{
		this.pageSize = pageSize;
	}
}
