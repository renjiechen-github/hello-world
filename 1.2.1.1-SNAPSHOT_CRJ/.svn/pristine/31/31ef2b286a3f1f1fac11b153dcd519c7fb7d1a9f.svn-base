/**
 * date: 2017年5月23日
 */
package com.ycdc.dingding.model.entity;


/**
 * @name: ActivationPassword.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月23日
 */
public class ActivationPassword {
	/**
	 * 公寓 id，如果第三方的 room_id 是全局唯一， 可以不传 home_id(可选)
	 * 只传 home_id 时增加外门锁密码
	 */
	private String home_id;
	/**
	 * 需要添加门锁的的房间的 room_id(可选)
	 */
	private String room_id;
	/**
	 * 门锁 uuid
	 * 注:room_id 与 uuid 至少有一个，且优先取 uuid
	 */
	private String uuid;
	/**
	 * 密码生成成功后要发送到的目标电话号码
	 */
	private String phonenumber;
	/**
	 * 激活码类型。
	 * 1: 租户激活码，密码 id 范围为 3025-3048 下发激活码数量超过id范围后，会覆盖前面的 id。
	 * 2: 员工激活码，密码 id 范围为 3001-3024 下发激活码数量超过id范围后，会覆盖前面的 id。
	 * 3: 管理员激活码，密码 id 为 3000，多次调 用增加管理员激活码，激活码在门锁上激活后，覆盖旧密码。
	 */
	private int CMD;
	/**
	 * 是否发送短信，默认不发送
	 */
	private boolean is_send_msg = false;
	/**
	 * 短信发送激活码是否携带公寓信息，默认不携带，is_send_msg 为 true 时有效。(可选)
	 */
	private boolean is_send_location = false;
	/**
	 * 密码的名称(可选)
	 */
	private String name;
	/**
	 * (可选)
	 * 注意: 结束时间，必须为下下整点之后的时 间。比如，现在时间为 15:28,结束时间必须 为 17:00 以后的时间。 开始时间，必须为现在，后台默认处理。 
	 * 管理员密码无需此字段
	 */
	private int permission_end;
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
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public boolean isIs_send_location() {
		return is_send_location;
	}
	public void setIs_send_location(boolean is_send_location) {
		this.is_send_location = is_send_location;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPermission_end() {
		return permission_end;
	}
	public void setPermission_end(int permission_end) {
		this.permission_end = permission_end;
	}
	public int getCMD() {
		return CMD;
	}
	public void setCMD(int cMD) {
		CMD = cMD;
	}
	public boolean isIs_send_msg() {
		return is_send_msg;
	}
	public void setIs_send_msg(boolean is_send_msg) {
		this.is_send_msg = is_send_msg;
	}
	/**
	 * 
	 */
	public ActivationPassword() {
		super();
	}
	/**
	 * @param home_id
	 * @param room_id
	 * @param uuid
	 * @param phonenumber
	 * @param cMD
	 * @param is_send_msg
	 * @param is_send_location
	 * @param name
	 * @param permission_end
	 */
	public ActivationPassword(String home_id, String room_id, String uuid, String phonenumber, int cMD,
			boolean is_send_msg, boolean is_send_location, String name, int permission_end) {
		super();
		this.home_id = home_id;
		this.room_id = room_id;
		this.uuid = uuid;
		this.phonenumber = phonenumber;
		CMD = cMD;
		this.is_send_msg = is_send_msg;
		this.is_send_location = is_send_location;
		this.name = name;
		this.permission_end = permission_end;
	}
}
