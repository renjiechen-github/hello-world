package pccom.web.beans;

import java.io.Serializable;

/**
 * 用户信息对应的javabean ,此实例是放置在session中，根据session的有效时间，所以此数据存在一定延迟，如需要实时准确的数据请查询微信用户表
 * @author 雷杨
 *
 */
public class User implements Serializable {

	private String id;

	private String mobile;
	
	private String name;
	
	private String headimgurl;
	
	private String city;
	
	private String country;
	
	private String province;
	
	private String roles;
	
	private String orgId;
	
	private String type;
	
	private String roleNames;
	
	private String orgName;
	
	private String g_id;
	/**
	 * @param 判断用户是否是手机登录
	 * 0:电脑
	 * 1：手机APP
	 */
	private String is_mobile;
	
	public String getIs_mobile() {
		return is_mobile;
	}

	public void setIs_mobile(String is_mobile) {
		this.is_mobile = is_mobile;
	}

	public String getG_id()
	{
		return g_id;
	}

	public void setG_id(String g_id)
	{
		this.g_id = g_id;
	}

	/**
	 * @return the orgName
	 */
	public String getOrgName()
	{
		return orgName;
	}

	/**
	 * @param orgName the orgName to set
	 */
	public void setOrgName(String orgName)
	{
		this.orgName = orgName;
	}

	/**
	 * @return the roleNames
	 */
	public String getRoleNames()
	{
		return roleNames;
	}

	/**
	 * @param roleNames the roleNames to set
	 */
	public void setRoleNames(String roleNames)
	{
		this.roleNames = roleNames;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getHeadimgurl() {
		return headimgurl;
	}

	public void setHeadimgurl(String headimgurl) {
		this.headimgurl = headimgurl;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
}
