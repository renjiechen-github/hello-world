/**
 * date: 2017年4月6日
 */
package com.ycdc.huawei.model.entity;

import java.util.List;


/**
 * @name: OrderedPhoneNumberInfo.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年4月6日
 */
public class OrderedPhoneNumberInfo {
	// 虚拟号码。
	private String virtualNumber;
	/*
	 * 号码状态。
	 * 取值：
	 * 0：空闲，即该号码没有任何绑定关系。
	 * 1：使用中，即该号码至少有一个绑定关系。
	 */
	private String numberStatus;
	// 号码的绑定信息, 只有虚拟号码使用中才有绑定信息。
	private List<OriginalBindInfo> bindInfos;
	// 所属运营商ID。
	private String operator;
	// 所属省份ID。
	private String province;
	/*
	 * 商户申请号码时间，UTC时间。
	 * 格式：
	 * YYYY-MM-DD'T'hh:mm:ss'Z'
	 * 示例：2014-01-07T07:18:13Z
	 */
	private String createTime;
	public String getVirtualNumber() {
		return virtualNumber;
	}
	public void setVirtualNumber(String virtualNumber) {
		this.virtualNumber = virtualNumber;
	}
	public String getNumberStatus() {
		return numberStatus;
	}
	public void setNumberStatus(String numberStatus) {
		this.numberStatus = numberStatus;
	}
	public List<OriginalBindInfo> getBindInfos() {
		return bindInfos;
	}
	public void setBindInfos(List<OriginalBindInfo> bindInfos) {
		this.bindInfos = bindInfos;
	}
	public void addBindInfo(OriginalBindInfo bindInfo) {
		this.bindInfos.add(bindInfo);
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
