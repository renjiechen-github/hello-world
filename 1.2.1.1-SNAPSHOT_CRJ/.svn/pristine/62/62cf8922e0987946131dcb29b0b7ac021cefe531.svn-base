/**
 * date: 2017年5月22日
 */
package com.ycdc.dingding.model.entity;

import java.util.Date;

/**
 * @name: Password.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月22日
 */
public class Password {
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
	 * 是否是管理员密码，管理员密码针对每一个 设备时唯一的。
	 * 0:非管理员密码
	 * 1:管理员密码
	 */
	private int is_default;
	/**
	 * 短信发送激活码是否携带公寓信息，默认不携带(可选)
	 */
	private boolean is_send_location = false;
	/**
	 * 开锁密码(6 位)
	 */
	private String password;
	/**
	 * 密码的名称(可选)
	 */
	private String name;
	/**
	 * 密码有效期的开始时 间时间戳，单位 S
	 * 默认当前时间
	 */
	private int permission_begin = (int)(new Date().getTime()/1000);
	/**
	 * 密码有效期的结束时 间时间戳，单位 S
	 * 注意：permission_begin 或者 permission_end 为0，则为永久权限
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
	public int getIs_default() {
		return is_default;
	}
	public void setIs_default(int is_default) {
		this.is_default = is_default;
	}
	public boolean isIs_send_location() {
		return is_send_location;
	}
	public void setIs_send_location(boolean is_send_location) {
		this.is_send_location = is_send_location;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPermission_begin() {
		return permission_begin;
	}
	public void setPermission_begin(int permission_begin) {
		this.permission_begin = permission_begin;
	}
	public int getPermission_end() {
		return permission_end;
	}
	public void setPermission_end(int permission_end) {
		this.permission_end = permission_end;
	}
	
	/**
	 * 
	 */
	public Password() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param home_id
	 * @param room_id
	 * @param uuid
	 * @param phonenumber
	 * @param is_default
	 * @param is_send_location
	 * @param password
	 * @param name
	 * @param permission_begin
	 * @param permission_end
	 */
	public Password(String home_id, String room_id, String uuid, String phonenumber, int is_default,
			boolean is_send_location, String password, String name, int permission_begin, int permission_end) {
		super();
		this.home_id = home_id;
		this.room_id = room_id;
		this.uuid = uuid;
		this.phonenumber = phonenumber;
		this.is_default = is_default;
		this.is_send_location = is_send_location;
		this.password = password;
		this.name = name;
		this.permission_begin = permission_begin;
		this.permission_end = permission_end;
	}
	
}
