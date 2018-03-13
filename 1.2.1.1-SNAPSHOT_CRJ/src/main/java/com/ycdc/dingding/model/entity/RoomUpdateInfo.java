/**
 * date: 2017年5月22日
 */
package com.ycdc.dingding.model.entity;

/**
 * @name: RoomUpdateInfo.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月22日
 */
public class RoomUpdateInfo extends Room {

	/**
	 * 
	 */
	public RoomUpdateInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param home_id（添加房间时的公寓id）
	 * @param room_id
	 * @param room_name
	 * @param room_description
	 */
	public RoomUpdateInfo(String home_id, String room_id, String room_name, String room_description) {
		super(home_id, room_id, room_name, room_description);
		// TODO Auto-generated constructor stub
	}
	
}
