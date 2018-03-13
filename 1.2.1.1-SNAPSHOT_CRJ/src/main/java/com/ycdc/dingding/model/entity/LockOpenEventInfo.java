/**
 * date: 2017年5月22日
 */
package com.ycdc.dingding.model.entity;

/**
 * @name: LockOpenEventInfo.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月22日
 */
public class LockOpenEventInfo {
	/**
	 * 事件发生的时间戳，单位 ms
	 */
	private int time;
	/**
	 * 事件 ID: 1:开门
	 */
	private int eventid;
	/**
	 * 事件触发源: 2:密码开锁
	 */
	private int source;
	/**
	 * 触发源 ID，密码开锁即为密码 ID
	 */
	private int sourceid;
	/**
	 * 触发源名字，如密码 ID 的名字
	 */
	private String source_name;
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getEventid() {
		return eventid;
	}
	public void setEventid(int eventid) {
		this.eventid = eventid;
	}
	public int getSource() {
		return source;
	}
	public void setSource(int source) {
		this.source = source;
	}
	public int getSourceid() {
		return sourceid;
	}
	public void setSourceid(int sourceid) {
		this.sourceid = sourceid;
	}
	public String getSource_name() {
		return source_name;
	}
	public void setSource_name(String source_name) {
		this.source_name = source_name;
	}

}
