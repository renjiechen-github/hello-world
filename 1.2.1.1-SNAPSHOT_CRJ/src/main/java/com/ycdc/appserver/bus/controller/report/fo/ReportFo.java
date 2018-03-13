/**
 * 
 */
package com.ycdc.appserver.bus.controller.report.fo;

/**
 * @author stephen
 * 
 */
public class ReportFo
{
	private int agreeType; // 合约类型 1 收房 2出租
	private String begin_date;// 累积增长开始节点 例如 1周内 = 当前日期-7
	private String date;// 当前日期
	private String time_flag; // 时间标识，可用于charts图表x轴展示

	public int getAgreeType()
	{
		return agreeType;
	}

	public void setAgreeType(int agreeType)
	{
		this.agreeType = agreeType;
	}

	public String getBegin_date()
	{
		return begin_date;
	}

	public void setBegin_date(String begin_date)
	{
		this.begin_date = begin_date;
	}

	public String getDate()
	{
		return date;
	}

	public void setDate(String date)
	{
		this.date = date;
	}

	public String getTime_flag()
	{
		return time_flag;
	}

	public void setTime_flag(String time_flag)
	{
		this.time_flag = time_flag;
	}

}
