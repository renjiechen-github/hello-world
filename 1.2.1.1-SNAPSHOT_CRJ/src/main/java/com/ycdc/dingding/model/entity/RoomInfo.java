/**
 * date: 2017年5月22日
 */
package com.ycdc.dingding.model.entity;

/**
 * @name: RoomInfo.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月22日
 */
public class RoomInfo {
	/**
	 * 需要添加的房间id（必须唯一）
	 */
	private String room_id;
	/**
	 * 需要添加的房间别名
	 */
	private String room_name;
	/**
	 * 需要添加的房间描述
	 */
	private String description;
	/**
	 * 1:未安装 2:可管理
	 */
	private int sp_state;
	/**
	 * 3:已分配安装 4:未分配安装 5:已完成安装
	 */
	private int install_state;
	public String getRoom_id() {
		return room_id;
	}
	public void setRoom_id(String room_id) {
		this.room_id = room_id;
	}
	public String getRoom_name() {
		return room_name;
	}
	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getSp_state() {
		return sp_state;
	}
	public void setSp_state(int sp_state) {
		this.sp_state = sp_state;
	}
	public int getInstall_state() {
		return install_state;
	}
	public void setInstall_state(int install_state) {
		this.install_state = install_state;
	}
	
}
