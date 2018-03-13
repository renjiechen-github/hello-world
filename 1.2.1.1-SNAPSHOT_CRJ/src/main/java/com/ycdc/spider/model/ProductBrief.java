package com.ycdc.spider.model;

import org.springframework.stereotype.Component;

import com.geccocrawler.gecco.annotation.Href;
import com.geccocrawler.gecco.annotation.HtmlField;
import com.geccocrawler.gecco.spider.HtmlBean;

@Component
public class ProductBrief implements HtmlBean
{
	private static final long serialVersionUID = 5381345807950155840L;

	@Href(click = true)
	@HtmlField(cssPath = ".info > .name > a")
	private String detailUrl;

	public String getDetailUrl()
	{
		return detailUrl;
	}

	public void setDetailUrl(String detailUrl)
	{
		this.detailUrl = detailUrl;
	}

}