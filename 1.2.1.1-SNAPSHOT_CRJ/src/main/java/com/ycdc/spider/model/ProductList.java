package com.ycdc.spider.model;

import java.util.List;

import org.springframework.stereotype.Component;

import com.geccocrawler.gecco.annotation.Gecco;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.annotation.Request;
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
@Gecco(matchUrl = "http://nj.rent.house365.com/district_i1/dl_r1-p{p}.html", pipelines =
{ "consolePipeline", "productListPipeline" })
public class ProductList implements HtmlBean
{
	private static final long serialVersionUID = -830146767923673894L;

	@Request
	private HttpRequest request;

	/**
	 * 抓取列表项的详情页地址
	 */
	@HtmlField(cssPath = "#JS_listPag .listItem.clearfix")
	private List<ProductBrief> details;

	/**
	 * 获得房源列表的当前页和总页数
	 */
	@Text
	@HtmlField(cssPath = ".listPagTitle > div.function1 > div > p")
	private String page;

	public List<ProductBrief> getDetails()
	{
		return details;
	}

	public void setDetails(List<ProductBrief> details)
	{
		this.details = details;
	}

	public HttpRequest getRequest()
	{
		return request;
	}

	public void setRequest(HttpRequest request)
	{
		this.request = request;
	}

	public String getPage()
	{
		return page;
	}

	public void setPage(String page)
	{
		this.page = page;
	}

}
