/**
 * date: 2017年5月22日
 */
package com.ycdc.dingding.model.entity;

/**
 * @name: LockEventsCondition.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月22日
 */
public class LockEventsCondition extends LockOpenCountCondition {
	/**
	 * 拉取的偏移量，默认为 0
	 */
	private int offset;
	/**
	 * 本次拉取的个数，默认是 50
	 */
	private int count = 50;
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	/**
	 * 
	 */
	public LockEventsCondition() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param home_id
	 * @param room_id
	 * @param uuid
	 * @param start_time
	 * @param end_time
	 */
	public LockEventsCondition(String home_id, String room_id, String uuid, int offset, int count, int start_time, int end_time) {
		super(home_id, room_id, uuid, start_time, end_time);
		// TODO Auto-generated constructor stub
		this.offset = offset;
		this.count = count;
	}
	
	
}
