/**
 * date: 2017年5月22日
 */
package com.ycdc.dingding.model.entity;

import java.util.Map;

/**
 * @name: HomeCenterInfo.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月22日
 */
public class HomeCenterInfo {
	/**
	 * 设备的 MAC 地址
	 */
	private String mac;
	/**
	 * 设备序列号
	 */
	private String sn;
	/**
	 * 设备注册时间时间戳，单位 S
	 */
	private int bind_time;
	/**
	 * 网关状态: 1:网关正常 2: 网关离线
	 */
	private int inoff_line;
	/**
	 * 最近一次在线状态更新时间戳，单位 S
	 */
	private int onoff_time;
	/**
	 * 设备uuid
	 */
	private String uuid;
	/**
	 * 设备名
	 */
	private String name;
	/**
	 * 设备类型
	 */
	private String model;
	/**
	 * 设备品牌
	 */	
	private String brand;
	/**
	 * 设备版本
	 */
	private Map<String, String> versions;
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public int getBind_time() {
		return bind_time;
	}
	public void setBind_time(int bind_time) {
		this.bind_time = bind_time;
	}
	public int getInoff_line() {
		return inoff_line;
	}
	public void setInoff_line(int inoff_line) {
		this.inoff_line = inoff_line;
	}
	public int getOnoff_time() {
		return onoff_time;
	}
	public void setOnoff_time(int onoff_time) {
		this.onoff_time = onoff_time;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public Map<String, String> getVersions() {
		return versions;
	}
	public void setVersions(Map<String, String> versions) {
		this.versions = versions;
	}
	
}
