/**
 * date: 2017年5月23日
 */
package com.ycdc.dingding.model.entity;

/**
 * @name: SearchDeviceOpCondition.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月23日
 */
public class SearchDeviceOpCondition extends LockEventsCondition {
	/**
	 * 操作人（可选）
	 */
	private String operator;
	/**
	 * 操作类型 id（可选）, 取值：
	 * 20002 : 发放密码
	 * 20003 : 删除密码
	 * 20004 : 修改密码
	 * 20006 : 冻结密码
	 * 20007 : 激活密码
	 * 20008 : 查看动态密码
	 * 20009 : 重置管理员密码
	 * 200010 : 查看管理员密码
	 */
	private String op_id;
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public String getOp_id() {
		return op_id;
	}
	public void setOp_id(String op_id) {
		this.op_id = op_id;
	}
	/**
	 * 
	 */
	public SearchDeviceOpCondition() {
		super();
	}
	/**
	 * @param home_id
	 * @param room_id
	 * @param uuid
	 * @param offset
	 * @param count
	 * @param start_time
	 * @param end_time
	 * @param operator
	 * @param op_id
	 */
	public SearchDeviceOpCondition(String home_id, String room_id, String uuid, int offset, int count, int start_time,
			int end_time, String operator, String op_id) {
		super(home_id, room_id, uuid, offset, count, start_time, end_time);
		// TODO Auto-generated constructor stub
		this.operator = operator;
		this.op_id = op_id;
	}
	
}
