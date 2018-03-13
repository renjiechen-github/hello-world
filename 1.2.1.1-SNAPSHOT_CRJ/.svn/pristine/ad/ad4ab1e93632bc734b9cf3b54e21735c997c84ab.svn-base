/**
 * date: 2017年4月6日
 */
package com.ycdc.huawei.model.entity;

/**
 * @name: ProtectInfo.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年4月6日
 */
public class ProtectInfo {
	/*
	 * 是否进入保护期。
	 * 取值：
	 * 1：呼叫进入保护期。
	 * 说明
	 * 如果不携带或者值为空，或者为其他值，则继承原有解绑逻辑，即不进入保护期，正常解绑。
	 */
	private String relationProtect;
	/*
	 * 保护期内呼叫方向。
	 * 取值：
	 * 0：aParty和bParty可以相互呼叫；
	 * 1：只能aParty呼叫bParty；
	 * 2：只能bParty呼叫aParty。
	 * 说明
	 * 1.relationProtect=1，该字段才生效，否则不生效。
	 * 2.如果不携带该参数或者值为空，或者为其他取值，则继承原有绑定关系的呼叫方向。
	 */
	private String bindDirection;
	/*
	 * 该绑定结束时间，UTC时间。格式：YYYY-MM-DD'T'hh:mm:ss'Z'。
	 * 示例：2016-05-20T23:59:59Z
	 * 说明
	 * 1.relationProtect=1时，该字段必选，否则返回错误标识。
	 * 2.日期应大于系统当前日期。
	 */
	private String endTime;
	
	public String getRelationProtect() {
		return relationProtect;
	}
	public void setRelationProtect(String relationProtect) {
		this.relationProtect = relationProtect;
	}
	public String getBindDirection() {
		return bindDirection;
	}
	public void setBindDirection(String bindDirection) {
		this.bindDirection = bindDirection;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
}
