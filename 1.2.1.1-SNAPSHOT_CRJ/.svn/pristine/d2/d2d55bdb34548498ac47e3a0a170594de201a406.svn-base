/**
 * date: 2017年4月6日
 */
package com.ycdc.huawei.model.entity;

/**
 * @name: OriginalBindInfo.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年4月6日
 */
public class OriginalBindInfo {
	// 绑定关系的绑定ID。
	private String subscriptionId;
	/*
	 * 小号业务（AXB）场景，virtualNumber 绑定的A方真实手机号。
	 * 个人小号业务（AX）场景，不返回该字段。
	 * 格式：
	 * 手机号码：国家码+手机号，
	 * 固话：国家码+区号+固话号。
	 * 国家码固定86，区号是去0后的号码，例如南京区号是025，填写为25，号码为7位或者8位的固话号码。
	 * 示例：
	 * 手机号码：8613800000001
	 * 固话：862586321234
	 */
	private String aParty;
	/*
	 * 小号业务（AXB）场景，virtualNumber 绑定的B方真实手机号。
	 * 个人小号业务（AX）场景，不返回该字段。
	 * 格式：
	 * 手机号码：国家码+手机号，
	 * 固话：国家码+区号+固话号。
	 * 国家码固定86，区号是去0后的号码，例如南京区号是025，填写为25，号码为7位或者8位的固话号码。
	 * 示例：
	 * 手机号码：8613800000001
	 * 固话：862586321234
	 */
	private String bParty;
	/*
	 * 该绑定关系的绑定时间。
	 * 格式：YYYY-MM-DDTHH:MM:SSZ
	 * 示例：2014-01-07T07:18:13Z
	 */
	private String bindTime;
	public String getSubscriptionId() {
		return subscriptionId;
	}
	public void setSubscriptionId(String subscriptionId) {
		this.subscriptionId = subscriptionId;
	}
	public String getaParty() {
		return aParty;
	}
	public void setaParty(String aParty) {
		this.aParty = aParty;
	}
	public String getbParty() {
		return bParty;
	}
	public void setbParty(String bParty) {
		this.bParty = bParty;
	}
	public String getBindTime() {
		return bindTime;
	}
	public void setBindTime(String bindTime) {
		this.bindTime = bindTime;
	}
	
}
