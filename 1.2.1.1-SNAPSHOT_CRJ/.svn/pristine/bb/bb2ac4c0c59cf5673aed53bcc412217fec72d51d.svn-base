package com.ycdc.spider.model;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.springframework.stereotype.Component;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.annotation.RequestParameter;
import com.geccocrawler.gecco.annotation.Text;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;

/**
 * @author 孙诚明
 * @version 1.0
 * @since 2016年10月17日
 * @category com.ycdc.data.model
 */
@Component
@Gecco(matchUrl = "http://nj.rent.house365.com/r_{code}.html", pipelines =
{ "consolePipeline", "productDetailPipeline" })
public class ProductDetail implements HtmlBean
{
	private static final long serialVersionUID = -4701912983155827951L;

	@Text
	@HtmlField(cssPath = "body > div:nth-child(5) > div.houseInformation.clearfix > div.left > div.titleT > h1")
	private String title;

	/**
	 * 房源编码
	 */
	@RequestParameter
	private String code;

	@Request
	private HttpRequest request;

	/**
	 * 详情页面URL地址
	 */
	private String url;

	/**
	 * 联系电话
	 */
	@Href
	@HtmlField(cssPath = "body > div:nth-child(5) > div.houseInformation.clearfix > div.left > div.houseInfo.clearfix > div.houseInfoMain > div.telephoneBox > div > div > div > p.tex > a.a")
	private String phoneNumber;

	/**
	 * 联系人
	 */
	@Text
	@HtmlField(cssPath = "body > div:nth-child(5) > div.showMain.clearfix > div.showLeft > div.telephoneBox.telephoneBoxBottom > div > div > div.show > p > span.name")
	private String contacts;

	/**
	 * 房源价格
	 */
	@Text
	@HtmlField(cssPath = "body > div:nth-child(5) > div.houseInformation.clearfix > div.left > div.houseInfo.clearfix > div.houseInfoMain > dl.item.clearfix > dd > div > span")
	private String price;

	/**
	 * 户型
	 */
	@Text
	@HtmlField(cssPath = "body > div:nth-child(5) > div.houseInformation.clearfix > div.left > div.houseInfo.clearfix > div.houseInfoMain > dl:nth-child(5) > dd")
	private String houseType;

	/**
	 * 装修
	 */
	@Text
	@HtmlField(cssPath = "body > div:nth-child(5) > div.houseInformation.clearfix > div.left > div.houseInfo.clearfix > div.houseInfoMain > dl:nth-child(3) > dd")
	private String renovation;

	/**
	 * 小区
	 */
	private String community;

	/**
	 * 小区1
	 */
	@Text
	@HtmlField(cssPath = "body > div:nth-child(5) > div.houseInformation.clearfix > div.left > div.houseInfo.clearfix > div.houseInfoMain > dl:nth-child(8) > dd > a:nth-child(1)")
	private String community1;

	/**
	 * 小区2
	 */
	@Text
	@HtmlField(cssPath = "body > div:nth-child(5) > div.houseInformation.clearfix > div.left > div.houseInfo.clearfix > div.houseInfoMain > dl:nth-child(8) > dd")
	private String community2;

	/**
	 * 发布时间
	 */
	@Text
	@HtmlField(cssPath = "body > div:nth-child(5) > div.houseInformation.clearfix > div.left > div.houseInfo.clearfix > div.houseInfoMain > div.time")
	private String releaseTime;

	/**
	 * 楼层
	 */
	@Text
	@HtmlField(cssPath = "body > div:nth-child(5) > div.houseInformation.clearfix > div.left > div.houseInfo.clearfix > div.houseInfoMain > dl:nth-child(6) > dd")
	private String floor;

	/**
	 * 面积
	 */
	@Text
	@HtmlField(cssPath = "body > div:nth-child(5) > div.houseInformation.clearfix > div.left > div.houseInfo.clearfix > div.houseInfoMain > dl:nth-child(2) > dd")
	private String acreage;

	/**
	 * 类型
	 */
	@Text
	@HtmlField(cssPath = "body > div:nth-child(5) > div.houseInformation.clearfix > div.left > div.houseInfo.clearfix > div.houseInfoMain > dl:nth-child(4) > dd")
	private String useType;

	/**
	 * 区域
	 */
	@Text
	@HtmlField(cssPath = "body > div:nth-child(5) > div.houseInformation.clearfix > div.left > div.houseInfo.clearfix > div.houseInfoMain > dl:nth-child(7) > dd > a:nth-child(1)")
	private String area;

	/**
	 * 商圈
	 */
	@Text
	@HtmlField(cssPath = "body > div:nth-child(5) > div.houseInformation.clearfix > div.left > div.houseInfo.clearfix > div.houseInfoMain > dl:nth-child(7) > dd > a:nth-child(3)")
	private String business_center;

	/**
	 * 房源来源
	 */
	private String source;

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getCommunity2()
	{
		return community2;
	}

	public void setCommunity2(String community2)
	{
		this.community2 = community2;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public HttpRequest getRequest()
	{
		return request;
	}

	public void setRequest(HttpRequest request)
	{
		this.request = request;
	}

	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getContacts()
	{
		return contacts;
	}

	public void setContacts(String contacts)
	{
		this.contacts = contacts;
	}

	public String getPrice()
	{
		return price;
	}

	public void setPrice(String price)
	{
		this.price = price;
	}

	public String getHouseType()
	{
		return houseType;
	}

	public void setHouseType(String houseType)
	{
		this.houseType = houseType;
	}

	public String getRenovation()
	{
		return renovation;
	}

	public void setRenovation(String renovation)
	{
		this.renovation = renovation;
	}

	public String getCommunity1()
	{
		return community1;
	}

	public void setCommunity1(String community1)
	{
		this.community1 = community1;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public String getCommunity()
	{
		return community;
	}

	public void setCommunity(String community)
	{
		this.community = community;
	}

	public String getReleaseTime()
	{
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime)
	{
		this.releaseTime = releaseTime;
	}

	public String getFloor()
	{
		return floor;
	}

	public void setFloor(String floor)
	{
		this.floor = floor;
	}

	public String getSource()
	{
		return source;
	}

	public void setSource(String source)
	{
		this.source = source;
	}

	public String getAcreage()
	{
		return acreage;
	}

	public void setAcreage(String acreage)
	{
		this.acreage = acreage;
	}

	public String getUseType()
	{
		return useType;
	}

	public void setUseType(String useType)
	{
		this.useType = useType;
	}

	public String getArea()
	{
		return area;
	}

	public void setArea(String area)
	{
		this.area = area;
	}

	public String getBusiness_center()
	{
		return business_center;
	}

	public void setBusiness_center(String business_center)
	{
		this.business_center = business_center;
	}

	@Override
	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
