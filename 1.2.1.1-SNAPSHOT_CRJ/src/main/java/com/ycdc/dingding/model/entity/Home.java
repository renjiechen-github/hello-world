/**
 * date: 2017年5月19日
 */
package com.ycdc.dingding.model.entity;

/**
 * @name: Home.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月19日
 */
public class Home {
	/**
	 * 公寓类型，默认：1
	 * 1：分散式公寓
	 * 2：集中式公寓
	 */
	private int home_type = 1;
	/**
	 * 公寓所在国家，默认：中国
	 */
	private String country = "中国";
	/**
	 * 公寓所在城市，默认：南京
	 */
	private String city = "南京市";
	/**
	 * 公寓所在区域
	 */
	private String zone;
	/**
	 * 公寓具体地址信息，最好精确到门牌号
	 */
	private String location;
	/**
	 * 小区名
	 */
	private String block;
	/**
	 * 房源表中的房源id，后续可以通过 此 id 获取公寓的详细信息
	 */
	private String home_id;
	/**
	 * 公寓名称，比如:公寓编号,昵称
	 */
	private String home_name;
	/**
	 * 公寓的描述
	 */
	private String description = "";
	
	public int getHome_type() {
		return home_type;
	}
	public void setHome_type(int home_type) {
		this.home_type = home_type;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getBlock() {
		return block;
	}
	public void setBlock(String block) {
		this.block = block;
	}
	public String getHome_id() {
		return home_id;
	}
	public void setHome_id(String home_id) {
		this.home_id = home_id;
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
	/**
	 * 默认构造方法
	 */
	public Home() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param city
	 * @param zone
	 * @param location
	 * @param block
	 * @param home_id
	 * @param home_name
	 * @param description
	 */
	public Home(String city, String zone, String location, String block, String home_id, String home_name,
			String description) {
		super();
		this.city = city;
		this.zone = zone;
		this.location = location;
		this.block = block;
		this.home_id = home_id;
		this.home_name = home_name;
		this.description = description;
	}
	
}
