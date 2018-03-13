/**
 * date: 2017年5月22日
 */
package com.ycdc.dingding.model.entity;

/**
 * @name: PasswordUpdateInfo.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月22日
 */
public class PasswordUpdateInfo extends Password {
	/**
	 * 密码 id，密码 id 在此设备中唯一。 注意:管理员密码 id 为常量:999
	 */
	private String password_id;

	public String getPassword_id() {
		return password_id;
	}
	public void setPassword_id(String password_id) {
		this.password_id = password_id;
	}
	/**
	 * 
	 */
	public PasswordUpdateInfo() {
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
	 * @param password_id
	 * @param password
	 * @param name
	 * @param permission_begin
	 * @param permission_end
	 */
	public PasswordUpdateInfo(String home_id, String room_id, String uuid, String phonenumber, int is_default,
			boolean is_send_location, String password_id, String password, String name, int permission_begin, int permission_end) {
		super(home_id, room_id, uuid, phonenumber, is_default, is_send_location, password, name, permission_begin,
				permission_end);
		this.password_id = password_id;
		// TODO Auto-generated constructor stub
	}
	
}
