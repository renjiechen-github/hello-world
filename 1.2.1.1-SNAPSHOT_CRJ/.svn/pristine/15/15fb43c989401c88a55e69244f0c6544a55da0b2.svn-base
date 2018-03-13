/**
 * date: 2017年5月23日
 */
package com.ycdc.dingding.model.entity;

/**
 * @name: TicketDetailInfo.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月23日
 */
public class TicketDetailInfo {
	/**
	 * 工单 id
	 */
	private String ticket_id;
	/**
	 * 工单状态， 1: 待处理。2:待分配。3:已分配。4:已完成。5:已关闭
	 */
	private int ticket_state;
	/**
	 * 工单类型: 预约安装:1 售后维修:2
	 */
	private int service_type;
	/**
	 * 工单服务对象: 网关:1 门锁: 2 电表: 3
	 */
	private int service_target;
	/**
	 * 
	 */
	private TicketRelateHome relate_home;
	/**
	 * service_type 为 2即维修工单时，有此字段
	 */
	private String ticket_description;
	/**
	 * 操作人员
	 */
	private Operator operator_1;
	/**
	 * 安装人员
	 */
	private Operator operator_2;
	/**
	 * service_type 为 1 时:安装人员确认信息。 service_type 为 2 时:操作人员确认信息
	 */
	private String confirm;
	
	private boolean public_auth;
	
	
	public boolean isPublic_auth() {
		return public_auth;
	}

	public void setPublic_auth(boolean public_auth) {
		this.public_auth = public_auth;
	}

	/**
	 * @name: TicketDetailInfo.java
	 * @Description: 工单操作人类
	 * @author duanyongrui
	 * @since: 2017年5月23日
	 */
	class Operator {
		/**
		 * 操作人 id
		 */
		private String op_id;
		/**
		 * 操作人姓名
		 */
		private String op_name;
		/**
		 * 操作人手机号码
		 */
		private String op_phone;
		public String getOp_id() {
			return op_id;
		}
		public void setOp_id(String op_id) {
			this.op_id = op_id;
		}
		public String getOp_name() {
			return op_name;
		}
		public void setOp_name(String op_name) {
			this.op_name = op_name;
		}
		public String getOp_phone() {
			return op_phone;
		}
		public void setOp_phone(String op_phone) {
			this.op_phone = op_phone;
		}
		
	}
	
	/**
	 * @name: TicketDetailInfo.java
	 * @Description: 人员确认信息
	 * @author duanyongrui
	 * @since: 2017年5月23日
	 */
	class ConfirmInfo {
		/**
		 * 人员姓名
		 */
		private String name;
		/**
		 * 人员手机号码
		 */
		private String phone;
		/**
		 * 确认时间， 时间戳 ms
		 */
		private String date;
		/**
		 * 
		 */
		private String time;
		/**
		 * 备注
		 */
		private String note;
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
		public String getNote() {
			return note;
		}
		public void setNote(String note) {
			this.note = note;
		}
		
	}

	public String getTicket_id() {
		return ticket_id;
	}

	public void setTicket_id(String ticket_id) {
		this.ticket_id = ticket_id;
	}

	public int getTicket_state() {
		return ticket_state;
	}

	public void setTicket_state(int ticket_state) {
		this.ticket_state = ticket_state;
	}

	public int getService_type() {
		return service_type;
	}

	public void setService_type(int service_type) {
		this.service_type = service_type;
	}

	public int getService_target() {
		return service_target;
	}

	public void setService_target(int service_target) {
		this.service_target = service_target;
	}

	public TicketRelateHome getRelate_home() {
		return relate_home;
	}

	public void setRelate_home(TicketRelateHome relate_home) {
		this.relate_home = relate_home;
	}

	public String getTicket_description() {
		return ticket_description;
	}

	public void setTicket_description(String ticket_description) {
		this.ticket_description = ticket_description;
	}

	public Operator getOperator_1() {
		return operator_1;
	}

	public void setOperator_1(Operator operator_1) {
		this.operator_1 = operator_1;
	}

	public Operator getOperator_2() {
		return operator_2;
	}

	public void setOperator_2(Operator operator_2) {
		this.operator_2 = operator_2;
	}

	public String getConfirm() {
		return confirm;
	}

	public void setConfirm(String confirm) {
		this.confirm = confirm;
	}
	
}
