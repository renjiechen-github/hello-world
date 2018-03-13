/**
 * date: 2017年4月6日
 */
package com.ycdc.huawei.model.entity;

/**
 * @name: VirModifyEntity.java
 * @Description:
 * @author duanyongrui
 * @since: 2017年4月6日
 */
public class VirModifyEntity
{
	// 绑定关系ID
	private String subscriptionId;

	/*
	 * 小号业务（AXB）场景，B方新的手机号B’。 
	 * 格式： 手机号码：国家码+手机号， 固话：国家码+区号+固话号。
	 * 国家码固定86，区号是去0后的号码，例如南京区号是025，填写为25，号码为7位或者8位的固话号码。 示例： 手机号码：8613800000001
	 * 固话：862586321234 说明 1.国家码前缀不能带有“+”或者“0”。 B方新号码不能是原始绑定B号码。
	 */
	private String bPartyNew;

	public String getSubscriptionId()
	{
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId)
	{
		this.subscriptionId = subscriptionId;
	}

	public String getbPartyNew()
	{
		return bPartyNew;
	}

	public void setbPartyNew(String bPartyNew)
	{
		this.bPartyNew = bPartyNew;
	}

	/**
	 * @param subscriptionId
	 * @param bPartyNew
	 * @param operId
	 */
	public VirModifyEntity(String subscriptionId, String bPartyNew)
	{
		super();
		this.subscriptionId = subscriptionId;
		this.bPartyNew = bPartyNew;
	}
}
