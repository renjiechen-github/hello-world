/**
 * date: 2017年5月23日
 */
package com.ycdc.dingding.model.entity;

import java.util.List;

/**
 * @name: Ticket.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月23日
 */
public class Ticket {
	/**
	 * 公寓 id
	 */
	private String home_id;
	/**
	 * 工单服务对象: 网关:1 门锁: 2 电表: 3
	 */
	private int service_target;
	/**
	 * 工单类型: 预约安装:1 售后维修:2
	 */
	private int service_type;
	/**
	 * 一组 roomid,即需要预约安装或者维修的房间 
	 * 例：["55e155ba8eb018a374466d34","55e155ba8eb018a374466d34"]
	 */
	private List<String> room_ids;
	/**
	 * 工单信息
	 */
	private TicketSubscribe subscribe;
	/**
	 * 工单描述(service_type 为 2 时需要)(可选)
	 */
	private String ticket_description;
	public String getHome_id() {
		return home_id;
	}
	public void setHome_id(String home_id) {
		this.home_id = home_id;
	}
	public int getService_target() {
		return service_target;
	}
	public void setService_target(int service_target) {
		this.service_target = service_target;
	}
	public int getService_type() {
		return service_type;
	}
	public void setService_type(int service_type) {
		this.service_type = service_type;
	}
	public List<String> getRoom_ids() {
		return room_ids;
	}
	public void setRoom_ids(List<String> room_ids) {
		this.room_ids = room_ids;
	}
	public void addRoom_id(String room_id) {
		this.room_ids.add(room_id);
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
	public Ticket() {
		super();
	}
	/**
	 * @param home_id
	 * @param service_target
	 * @param service_type
	 * @param room_ids
	 * @param subscribe
	 * @param ticket_description
	 */
	public Ticket(String home_id, int service_target, int service_type, List<String> room_ids,
			TicketSubscribe subscribe, String ticket_description) {
		super();
		this.home_id = home_id;
		this.service_target = service_target;
		this.service_type = service_type;
		this.room_ids = room_ids;
		this.subscribe = subscribe;
		this.ticket_description = ticket_description;
	}
	
}
