package com.yc.rm.caas.appserver.model.syscfg;

/**
 * CA对象
 * @author suntf
 * @date 2016年12月14日
 */
public class CABean
{
	/**
	 * 访问地址
	 */
	private String url;
	
	
	private String appid;
	
	private String content;
	
	private String signed;
	
	private String key;
	
	

	/**
	 * @param url
	 * @param appid
	 * @param content
	 * @param signed
	 * @param key
	 * @date 2016年12月14日
	 */
	public CABean(String url, String appid, String content, String signed, String key)
	{
		super();
		this.url = url;
		this.appid = appid;
		this.content = content;
		this.signed = signed;
		this.key = key;
	}

	/**
	 * @return the url
	 */
	public String getUrl()
	{
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url)
	{
		this.url = url;
	}

	/**
	 * @return the appid
	 */
	public String getAppid()
	{
		return appid;
	}

	/**
	 * @param appid the appid to set
	 */
	public void setAppid(String appid)
	{
		this.appid = appid;
	}

	/**
	 * @return the content
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(String content)
	{
		this.content = content;
	}

	/**
	 * @return the signed
	 */
	public String getSigned()
	{
		return signed;
	}

	/**
	 * @param signed the signed to set
	 */
	public void setSigned(String signed)
	{
		this.signed = signed;
	}

	/**
	 * @return the key
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key)
	{
		this.key = key;
	}
}
