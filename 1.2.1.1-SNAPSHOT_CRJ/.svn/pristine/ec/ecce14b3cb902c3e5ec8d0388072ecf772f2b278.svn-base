/**
 * date: 2017年4月1日
 */
package com.ycdc.huawei.model.entity;

/**
 * @name: VirtualManageEntity.java
 * @Description:
 * @author duanyongrui
 * @since: 2017年4月1日
 */
public class VirBindEntity
{
	/*
	 * 格式：国家码+手机号。 示例：8613800000000 说明: 
	 * 1.国家码前缀不能带有“+”或者“0”。
	 * 2.如果不带有该参数或者参数值为空，则根据cityCode随机分配指定城市虚拟号码。
	 * 3.如果cityCode也为空，则根据商户购买的码号资源中随机分配。
	 */
	private String virtualNumber;
	/*
	 * 格式： 手机号码：国家码+手机号， 固话：国家码+区号+固话号。
	 * 国家码固定86，区号是去0后的号码，例如南京区号是025，填写为25，号码为7位或者8位的固话号码。 示例： 手机号码：8613800000001
	 * 固话：862586321234 说明: 国家码前缀不能带有“+”或者“0”
	 */
	private String aParty; // 管家号码
	private String bParty; // 租客号码
	// 是否录音。默认录音;0：不录音,1：录音
	private String isRecord = "1";
	/*
	 * 呼叫方向。 取值： 0：aParty和bParty可以相互呼叫； 1：只能aParty呼叫bParty； 2：只能bParty呼叫aParty；
	 * 默认：0
	 */
	private String bindDirection = "0";
	/*
	 * (可选) 小号平台自动分配的虚号码所属城市的区号。 取值示例：025 说明 1.优先使用virtualNumber
	 * 2.virtualNumber为空，则按cityCode分配virtualNumber和cityCode都为空，则平台从商户购买的码号资源中随机分配
	 */
	private String cityCode;
	/*
	 * (可选) 该绑定关系结束日期，UTC时间。 格式：YYYY-MM-DD'T'hh:mm:ss'Z' 示例：2016-05-20T23:59:59Z
	 * 说明 1.日期要大于系统当前日期。 2.如果携带本字段，则到期后小号平台自动解绑。
	 */
	private String endTime;
	// 操作人编号
	private String operId;
	// 绑定时无需设置
	private int type = 1;
	// 绑定时无需设置
	private String createTime;
	// 本次绑定关系的绑定ID。绑定时无需设置
	private String subscriptionId;

	public String getVirtualNumber()
	{
		return virtualNumber;
	}

	public void setVirtualNumber(String virtualNumber)
	{
		this.virtualNumber = virtualNumber;
	}

	public String getaParty()
	{
		return aParty;
	}

	public void setaParty(String aParty)
	{
		this.aParty = aParty;
	}

	public String getbParty()
	{
		return bParty;
	}

	public void setbParty(String bParty)
	{
		this.bParty = bParty;
	}

	public String getIsRecord()
	{
		return isRecord;
	}

	public void setIsRecord(String isRecord)
	{
		this.isRecord = isRecord;
	}

	public String getBindDirection()
	{
		return bindDirection;
	}

	public void setBindDirection(String bindDirection)
	{
		this.bindDirection = bindDirection;
	}

	public String getCityCode()
	{
		return cityCode;
	}

	public void setCityCode(String cityCode)
	{
		this.cityCode = cityCode;
	}

	public String getEndTime()
	{
		return endTime;
	}

	public void setEndTime(String endTime)
	{
		this.endTime = endTime;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(String createTime)
	{
		this.createTime = createTime;
	}

	public String getSubscriptionId()
	{
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId)
	{
		this.subscriptionId = subscriptionId;
	}

	public String getOperId()
	{
		return operId;
	}

	public void setOperId(String operId)
	{
		this.operId = operId;
	}

	/**
	 * @param virtualNumber
	 * @param aParty
	 * @param bParty
	 * @param operId
	 */
	public VirBindEntity(String virtualNumber, String aParty, String bParty, String operId)
	{
		super();
		this.virtualNumber = virtualNumber;
		this.aParty = aParty;
		this.bParty = bParty;
		this.operId = operId;
	}

}
