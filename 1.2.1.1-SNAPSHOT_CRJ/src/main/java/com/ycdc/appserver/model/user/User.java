
package com.ycdc.appserver.model.user;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class User implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String id;//
	private String name;//
	private String pwd;//
	private String status; // 用户状态
	private String mobile; // 手机号码
	private String certificateno; // 身份证号码
	private String ca_author; // 用户是否认证
	private String email; // 邮箱
	private String loginDate; // 登录时间
	private String rolesName; // 用户角色
	private String rolesId; // 用户id
	private String headerImage; // 用户头像
	private String orgIds; // 组织编号
	private String orgnames; // 组织名称
	private String terminaltype; // 终端类型
	private String version_code; // 版本
	private String address; // 用户地址
	private String g_id; // 网格编号
	private String areaname; // 区域名称
	private String areaid; // 区域id
	private String state; //
	private String loginStatus; // 登录状态 1 登录成功 其他登录错误 错误提示为 loginMsg
	private String loginMsg; // 登录返回信息
	private String registrationID;
	private int sex;//用户性别
	private String birthday;//用户出生年月
	private String province;//用户户籍所在省份
	private List<Map<String, Object>> teamIds;//负责的团队id集合
	
	public List<Map<String, Object>> getTeamIds()
	{
		return teamIds;
	}
	

	public void setTeamIds(List<Map<String, Object>> teamIds)
	{
		this.teamIds = teamIds;
	}

	public String getProvince()
	{
		return province;
	}

	public void setProvince(String province)
	{
		this.province = province;
	}

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * @return the ca_author
	 */
	public String getCa_author()
	{
		return ca_author;
	}

	/**
	 * @param ca_author
	 *            the ca_author to set
	 */
	public void setCa_author(String ca_author)
	{
		this.ca_author = ca_author;
	}

	/**
	 * @return the certificateno
	 */
	public String getCertificateno()
	{
		return certificateno;
	}

	/**
	 * @param certificateno
	 *            the certificateno to set
	 */
	public void setCertificateno(String certificateno)
	{
		this.certificateno = certificateno;
	}

	private String token;

	/**
	 * @return the token
	 */
	public String getToken()
	{
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token)
	{
		this.token = token;
	}

	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile()
	{
		return mobile;
	}

	/**
	 * @param mobile
	 *            the mobile to set
	 */
	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	/**
	 * @return the loginStatus
	 */
	public String getLoginStatus()
	{
		return loginStatus;
	}

	/**
	 * @param loginStatus
	 *            the loginStatus to set
	 */
	public void setLoginStatus(String loginStatus)
	{
		this.loginStatus = loginStatus;
	}

	/**
	 * @return the loginMsg
	 */
	public String getLoginMsg()
	{
		return loginMsg;
	}

	/**
	 * @param loginMsg
	 *            the loginMsg to set
	 */
	public void setLoginMsg(String loginMsg)
	{
		this.loginMsg = loginMsg;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getPwd()
	{
		return pwd;
	}

	public void setPwd(String pwd)
	{
		this.pwd = pwd;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @date 2016年11月25日
	 */
	public User()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the loginDate
	 */
	public String getLoginDate()
	{
		return loginDate;
	}

	/**
	 * @param loginDate
	 *            the loginDate to set
	 */
	public void setLoginDate(String loginDate)
	{
		this.loginDate = loginDate;
	}

	/**
	 * @return the rolesName
	 */
	public String getRolesName()
	{
		return rolesName;
	}

	/**
	 * @param rolesName
	 *            the rolesName to set
	 */
	public void setRolesName(String rolesName)
	{
		this.rolesName = rolesName;
	}

	/**
	 * @return the rolesId
	 */
	public String getRolesId()
	{
		return rolesId;
	}

	/**
	 * @param rolesId
	 *            the rolesId to set
	 */
	public void setRolesId(String rolesId)
	{
		this.rolesId = rolesId;
	}

	/**
	 * @return the headerImage
	 */
	public String getHeaderImage()
	{
		return headerImage;
	}

	/**
	 * @param headerImage
	 *            the headerImage to set
	 */
	public void setHeaderImage(String headerImage)
	{
		this.headerImage = headerImage;
	}

	/**
	 * @return the orgIds
	 */
	public String getOrgIds()
	{
		return orgIds;
	}

	/**
	 * @param orgIds
	 *            the orgIds to set
	 */
	public void setOrgIds(String orgIds)
	{
		this.orgIds = orgIds;
	}

	/**
	 * @return the orgnames
	 */
	public String getOrgnames()
	{
		return orgnames;
	}

	/**
	 * @param orgnames
	 *            the orgnames to set
	 */
	public void setOrgnames(String orgnames)
	{
		this.orgnames = orgnames;
	}

	/**
	 * @return the terminaltype
	 */
	public String getTerminaltype()
	{
		return terminaltype;
	}

	/**
	 * @param terminaltype
	 *            the terminaltype to set
	 */
	public void setTerminaltype(String terminaltype)
	{
		this.terminaltype = terminaltype;
	}

	/**
	 * @return the version_code
	 */
	public String getVersion_code()
	{
		return version_code;
	}

	/**
	 * @param version_code
	 *            the version_code to set
	 */
	public void setVersion_code(String version_code)
	{
		this.version_code = version_code;
	}

	/**
	 * @return the address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * @param address
	 *            the address to set
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}

	/**
	 * @return the g_id
	 */
	public String getG_id()
	{
		return g_id;
	}

	/**
	 * @param g_id
	 *            the g_id to set
	 */
	public void setG_id(String g_id)
	{
		this.g_id = g_id;
	}

	/**
	 * @return the areaname
	 */
	public String getAreaname()
	{
		return areaname;
	}

	/**
	 * @param areaname
	 *            the areaname to set
	 */
	public void setAreaname(String areaname)
	{
		this.areaname = areaname;
	}

	/**
	 * @return the areaid
	 */
	public String getAreaid()
	{
		return areaid;
	}

	/**
	 * @param areaid
	 *            the areaid to set
	 */
	public void setAreaid(String areaid)
	{
		this.areaid = areaid;
	}

	
	public String getRegistrationID()
	{
		return registrationID;
	}

	
	public void setRegistrationID(String registrationID)
	{
		this.registrationID = registrationID;
	}

	public int getSex()
	{
		return sex;
	}

	public void setSex(int sex)
	{
		this.sex = sex;
	}

	public String getBirthday()
	{
		return birthday;
	}

	public void setBirthday(String birthday)
	{
		this.birthday = birthday;
	}

	 
}
