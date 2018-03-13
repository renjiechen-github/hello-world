/**
 * date: 2017年5月23日
 */
package com.ycdc.dingding.model.entity;

import java.util.List;

/**
 * @name: TicketRelateHome.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月23日
 */
public class TicketRelateHome extends Home {
	/**
	 * 公寓别名
	 */
	private String home_alias;
	/**
	 * 
	 */
	private String region;
	/**
	 * 
	 */
	private String client_id;
	/**
	 * 公寓的房间
	 */
	private List<RoomInfo> rooms;
	
	class RoomInfo {
		/**
		 * 房间 id
		 */
		private String room_id;
		/**
		 * 房间名
		 */
		private String room_name;
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
		
	}
	
	/**
	 * 账户名
	 */
	private String user_id;

	public String getHome_alias() {
		return home_alias;
	}

	public void setHome_alias(String home_alias) {
		this.home_alias = home_alias;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public List<RoomInfo> getRooms() {
		return rooms;
	}

	public void setRooms(List<RoomInfo> rooms) {
		this.rooms = rooms;
	}
	public void addRoom(RoomInfo room) {
		this.rooms.add(room);
	}
	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
}
