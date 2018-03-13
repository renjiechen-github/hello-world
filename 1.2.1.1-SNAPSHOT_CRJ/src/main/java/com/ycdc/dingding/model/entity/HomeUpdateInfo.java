/**
 * date: 2017年5月19日
 */
package com.ycdc.dingding.model.entity;

/**
 * @name: HomeUpdateInfo.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月19日
 */
public class HomeUpdateInfo {
	/**
	 * 需要更新的公寓id
	 */
	private String home_id;
	/**
	 * 不传此参数表示不改变原先属性。(可选)
	 * 1：未安装
	 * 2：可管理
	 */
	private int sp_state;
	/**
	 * 公寓具体地址信息，最好精确到门牌号
	 */
	private String location;
	/**
	 * 公寓名称，比如:公寓编号,昵称
	 */
	private String home_name;
	/**
	 * 公寓的描述
	 */
	private String description;
	public String getHome_id() {
		return home_id;
	}
	public void setHome_id(String home_id) {
		this.home_id = home_id;
	}
	public int getSp_state() {
		return sp_state;
	}
	public void setSp_state(int sp_state) {
		this.sp_state = sp_state;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getHome_name() {
		return home_name;
	}
	public void setHome_name(String home_name) {
		this.home_name = home_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
