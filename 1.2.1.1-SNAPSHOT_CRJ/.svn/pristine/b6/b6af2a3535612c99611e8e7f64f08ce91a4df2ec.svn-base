/**
 * date: 2017年5月22日
 */
package com.ycdc.dingding.model.entity;

import java.util.Date;

/**
 * @name: LockOpenCountCondition.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月22日
 */
public class LockOpenCountCondition {
	/**
	 * 公寓 id，如果第三方的 room_id 是全局唯一，可以不传 home_id，只传 home_id 时获取外门锁记录
	 */
	private String home_id;
	/**
	 * 房间的 room_id(可选)
	 */
	private String room_id;
	/**
	 * 门锁 uuid 
	 * 注:room_id 与 uuid 至少有一个，且优先取 uuid
	 */
	private String uuid;
	/**
	 * 查询起始时间戳，单位 S
	 */
	private int start_time;
	/**
	 * 查询结束时间戳，单位 S
	 * 默认当前时间点
	 */
	private int end_time = (int)(new Date().getTime()/1000);
	public String getHome_id() {
		return home_id;
	}
	public void setHome_id(String home_id) {
		this.home_id = home_id;
	}
	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public int getStart_time() {
		return start_time;
	}
	public void setStart_time(int start_time) {
		this.start_time = start_time;
	}
	public int getEnd_time() {
		return end_time;
	}
	public void setEnd_time(int end_time) {
		this.end_time = end_time;
	}
	/**
	 * 
	 */
	public LockOpenCountCondition() {
		super();
	}
	/**
	 * @param home_id
	 * @param room_id
	 * @param uuid
	 * @param start_time
	 * @param end_time
	 */
	public LockOpenCountCondition(String home_id, String room_id, String uuid, int start_time, int end_time) {
		super();
		this.home_id = home_id;
		this.room_id = room_id;
		this.uuid = uuid;
		this.start_time = start_time;
		this.end_time = end_time;
	}
	
}
