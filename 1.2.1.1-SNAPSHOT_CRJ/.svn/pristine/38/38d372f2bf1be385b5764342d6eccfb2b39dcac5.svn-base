/**
 * date: 2017年5月23日
 */
package com.ycdc.dingding.model.entity;

import java.util.List;

/**
 * @name: TicketUpdateInfo.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月23日
 */
public class TicketUpdateInfo {
	/**
	 * 系统生成的工单对应的唯一标识
	 */
	private String ticket_id;
	/**
	 * 预约房间
	 * 例：["55e155ba8eb018a374466d34","55e155ba8eb018a374466d34"]
	 */
	private List<String> room_ids;
	/**
	 * 工单信息(可选)
	 */
	private TicketSubscribe subscribe;
	/**
	 * 工单描述(可选)
	 */
	private String ticket_description;
	public String getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(String ticket_id) {
		this.ticket_id = ticket_id;
	}
	public List<String> getRoom_ids() {
		return room_ids;
	}
	public void setRoom_ids(List<String> room_ids) {
		this.room_ids = room_ids;
	}
	public TicketSubscribe getSubscribe() {
		return subscribe;
	}
	public void setSubscribe(TicketSubscribe subscribe) {
		this.subscribe = subscribe;
	}
	public String getTicket_description() {
		return ticket_description;
	}
	public void setTicket_description(String ticket_description) {
		this.ticket_description = ticket_description;
	}
	/**
	 * 
	 */
	public TicketUpdateInfo() {
		super();
	}
	/**
	 * @param ticket_id
	 * @param room_ids
	 * @param subscribe
	 * @param ticket_description
	 */
	public TicketUpdateInfo(String ticket_id, List<String> room_ids, TicketSubscribe subscribe, String ticket_description) {
		super();
		this.ticket_id = ticket_id;
		this.room_ids = room_ids;
		this.subscribe = subscribe;
		this.ticket_description = ticket_description;
	}

}
