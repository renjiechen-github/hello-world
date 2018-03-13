/**
 * date: 2017年4月6日
 */
package com.ycdc.huawei.model.entity;

/**
 * @name: VirUnbindEntity.java
 * @Description:
 * @author duanyongrui
 * @since: 2017年4月6日
 */
public class VirUnbindEntity
{

	/*
	 * 类型。用于指示解绑方式。 取值：
	 *  1：按虚拟号码解除绑定。 
	 *  2：按绑定ID解除绑定关系。 说明
	 * 如果不带有该参数，或者参数值为空，或者参数值为其他字符，则默认表示type取值为1，以虚拟号码解绑。
	 */
	private String type;
	/*
	 * 1.解绑对象：虚拟号码当type取值为1时，该字段必须携带并且取值不能为空。 2.当type取值为2时，该字段允许不携带
	 */
	private String number;
	/*
	 * 1.解绑对象：绑定ID当type取值为2时，该字段必须携带并且取值不能为空。 2.当type取值为1时，该字段允许不携带
	 */
	private String subscriptionId;

	// 受保护信息(可选)
	private ProtectInfo protectInfo;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getNumber()
	{
		return number;
	}

	public void setNumber(String number)
	{
		this.number = number;
	}

	public String getSubscriptionId()
	{
		return subscriptionId;
	}

	public void setSubscriptionId(String subscriptionId)
	{
		this.subscriptionId = subscriptionId;
	}

	public ProtectInfo getProtectInfo()
	{
		return protectInfo;
	}

	public void setProtectInfo(ProtectInfo protectInfo)
	{
		this.protectInfo = protectInfo;
	}

	/**
	 * @param type
	 * @param number
	 * @param subscriptionId
	 */
	public VirUnbindEntity(String type, String number, String subscriptionId)
	{
		super();
		this.type = type;
		this.number = number;
		this.subscriptionId = subscriptionId;
	}

}
