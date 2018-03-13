package com.yc.rm.caas.appserver.model.user;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Login
{

	private String cookies = "";

	private String mobile = "";

	private User user = null;

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public String getCookies()
	{
		return cookies;
	}

	public void setCookies(String cookies)
	{
		this.cookies = cookies;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	/**
	 * 格式化输出
	 */
	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
