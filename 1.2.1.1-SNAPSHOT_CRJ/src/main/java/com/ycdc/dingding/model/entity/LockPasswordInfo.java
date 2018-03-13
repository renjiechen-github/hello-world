/**
 * date: 2017年5月22日
 */
package com.ycdc.dingding.model.entity;

/**
 * @name: LockPasswordInfo.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月22日
 */
public class LockPasswordInfo {
	/**
	 * 密码 id，密码 id 在此设备中唯一
	 */
	private int id;
	/**
	 * 密码创建时间时间戳，单位 ms
	 */
	private int time;
	/**
	 * 是否管理员密码。0:非管理员密码 1:管理员密码
	 */
	private int is_default;
	/**
	 * 1: 初始状态 2: 已生效 3: 将生效 4: 已过 期 5: 已冻结 (只有 2 时，密码有效)
	 */
	private int pwd_state;
	/**
	 * 密码当前所处的操作: 1:添加 2:删除 3:更新 4:冻结 5:解冻结
	 */
	private int operation;
	/**
	 * 当前操作所处的阶段: 1:进行中，正在等待设备反馈 2:操作失败 3:操作成功
	 */
	private int operation_stage;
	/**
	 * 密码当前权限状态: 1:正常 2:有效期外
	 */
	private int permission_state;
	/**
	 * 操作出错原因描述
	 */
	private int description;
	/**
	 * 密码名(如租户姓名)
	 */
	private int name;
	/**
	 * 密码生成时的发送对象(如租户手机号)
	 */
	private int send_to;
	/**
	 * 锁密码权限
	 */
	private LockPasswordPermission permission;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTime() {
		return time;
	}
	public void setTime(int time) {
		this.time = time;
	}
	public int getIs_default() {
		return is_default;
	}
	public void setIs_default(int is_default) {
		this.is_default = is_default;
	}
	public int getPwd_state() {
		return pwd_state;
	}
	public void setPwd_state(int pwd_state) {
		this.pwd_state = pwd_state;
	}
	public int getOperation() {
		return operation;
	}
	public void setOperation(int operation) {
		this.operation = operation;
	}
	public int getOperation_stage() {
		return operation_stage;
	}
	public void setOperation_stage(int operation_stage) {
		this.operation_stage = operation_stage;
	}
	public int getPermission_state() {
		return permission_state;
	}
	public void setPermission_state(int permission_state) {
		this.permission_state = permission_state;
	}
	public int getDescription() {
		return description;
	}
	public void setDescription(int description) {
		this.description = description;
	}
	public int getName() {
		return name;
	}
	public void setName(int name) {
		this.name = name;
	}
	public int getSend_to() {
		return send_to;
	}
	public void setSend_to(int send_to) {
		this.send_to = send_to;
	}
	public LockPasswordPermission getPermission() {
		return permission;
	}
	public void setPermission(LockPasswordPermission permission) {
		this.permission = permission;
	}
	
}
