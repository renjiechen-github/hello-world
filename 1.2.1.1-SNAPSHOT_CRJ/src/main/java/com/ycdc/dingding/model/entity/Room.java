/**
 * date: 2017年5月22日
 */
package com.ycdc.dingding.model.entity;

/**
 * @name: Room.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月22日
 */
public class Room {
	/**
	 * 房间所属的公寓id(绑定公寓之后无法更新)
	 */
	private String home_id;
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
	private String room_description;
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
	public String getRoom_name() {
		return room_name;
	}
	public void setRoom_name(String room_name) {
		this.room_name = room_name;
	}
	public String getRoom_description() {
		return room_description;
	}
	public void setRoom_description(String room_description) {
		this.room_description = room_description;
	}
	
	
	
	/**
	 * 默认构造方法
	 */
	public Room() {
		super();
	}
	/**
	 * @param home_id
	 * @param room_id
	 * @param room_name
	 * @param room_description
	 */
	public Room(String home_id, String room_id, String room_name, String room_description) {
		super();
		this.home_id = home_id;
		this.room_id = room_id;
		this.room_name = room_name;
		this.room_description = room_description;
	}
	
}
