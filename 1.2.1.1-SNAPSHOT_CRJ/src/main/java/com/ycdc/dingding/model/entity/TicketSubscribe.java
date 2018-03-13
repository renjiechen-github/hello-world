/**
 * date: 2017年5月23日
 */
package com.ycdc.dingding.model.entity;

/**
 * @name: TicketSubscribe.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月23日
 */
public class TicketSubscribe {
	/**
	 * 预约日期，用时间戳 ms，只有日期位有效, service_type 为 2 时，不需要此字段
	 */
	private String date;
	/**
	 * 100 - 全天，101 - 上午，102 - 下午，103 - 晚上，或者是时间戳
	 */
	private String time;
	/**
	 * 联系人姓名
	 */
	private String name;
	/**
	 * 联系人手机号
	 */
	private String phone;
	/**
	 * 备注
	 */
	private String note;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	/**
	 * 
	 */
	public TicketSubscribe() {
		super();
	}
	/**
	 * @param date
	 * @param time
	 * @param name
	 * @param phone
	 * @param note
	 */
	public TicketSubscribe(String date, String time, String name, String phone, String note) {
		super();
		this.date = date;
		this.time = time;
		this.name = name;
		this.phone = phone;
		this.note = note;
	}
	
}
