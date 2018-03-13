/**
 * date: 2017年5月22日
 */
package com.ycdc.dingding.model.entity;

/**
 * @name: DynamicPasswordInfo.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月22日
 */
public class DynamicPasswordInfo {
	/**
	 * 动态密码
	 */
	private String password;
	/**
	 * 过期时间， 单位 S
	 */
	private long invalid_time;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public long getInvalid_time() {
		return invalid_time;
	}
	public void setInvalid_time(long invalid_time) {
		this.invalid_time = invalid_time;
	}
	
}
