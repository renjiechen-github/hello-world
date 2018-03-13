package com.yc.rm.caas.appserver.model.house;

/**
 * 工程配置清单
 * @author suntf
 * @date 2016年12月1日
 */
public class ProjectList
{
	/**
	 * 材料名称
	 */
	private String name;
	
	/**
	 * 供应商
	 */
	private String supname;
	
	/**
	 * 品牌
	 */
	private String brand;
	
	/**
	 * 规格
	 */
	private String spec;
	
	/**
	 * 编码
	 */
	private String code;
	
	/**
	 * 单价
	 */
	private String price;

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the supname
	 */
	public String getSupname()
	{
		return supname;
	}

	/**
	 * @param supname the supname to set
	 */
	public void setSupname(String supname)
	{
		this.supname = supname;
	}

	/**
	 * @return the brand
	 */
	public String getBrand()
	{
		return brand;
	}

	/**
	 * @param brand the brand to set
	 */
	public void setBrand(String brand)
	{
		this.brand = brand;
	}

	/**
	 * @return the spec
	 */
	public String getSpec()
	{
		return spec;
	}

	/**
	 * @param spec the spec to set
	 */
	public void setSpec(String spec)
	{
		this.spec = spec;
	}

	/**
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * @return the price
	 */
	public String getPrice()
	{
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(String price)
	{
		this.price = price;
	}
}
