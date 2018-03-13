package com.ycdc.spider.model;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.ycdc.spider.dao.ProductDAO;

/**
 * @author 孙诚明
 * @version 1.0
 * @since 2016年10月17日
 * @category com.ycdc.data.model
 */
@Component
public class ProductDetailPipeline implements Pipeline<ProductDetail>
{

	@Resource
	private ProductDAO dao;

	@Override
	public void process(ProductDetail productDetail)
	{
		HttpRequest currRequest = productDetail.getRequest();
		productDetail.setUrl(currRequest.getUrl());

		// 处理小区信息
		String community = productDetail.getCommunity1();
		if (null != community && !StringUtils.equals(community, ""))
			productDetail.setCommunity(community);
		else
			productDetail.setCommunity(productDetail.getCommunity2());

		// 处理房源发布时间
		String time = productDetail.getReleaseTime();
		if (time.contains("更新时间："))
		{
			time = time.split("\\：")[1];
		}
		productDetail.setReleaseTime(time);

		// 处理手机号码
		String phoneNumber = productDetail.getPhoneNumber();
		phoneNumber = StringUtils.substring(phoneNumber, phoneNumber.indexOf("=") + 1,
				phoneNumber.length());
		productDetail.setPhoneNumber(phoneNumber);

		// 处理房源标题乱码
		String title = productDetail.getTitle();
		for (int i = 0; i < title.length(); i++)
		{
			int a = title.codePointAt(i);
			if (a == 65533 || a == 79)
			{
				if (title.contains("平米"))
				{
					title = title.replace(String.valueOf(title.charAt(i)), "");	
				} else 
				{
					title = title.replace(String.valueOf(title.charAt(i)), "㎡");
				}
				
				if (i == title.length() - 1)
				{
					break;
				} else
				{
					i = 0;
				}
			}
		}
		
		productDetail.setTitle(title);
		
		// 处理面积乱码
		String acreage = productDetail.getAcreage();
		for (int i = 0; i < acreage.length(); i++)
		{
			int a = acreage.codePointAt(i);
			if (a == 65533)
			{
				acreage = acreage.replace(String.valueOf(acreage.charAt(i)), "");
			}
		}
		
		productDetail.setAcreage(acreage.substring(0, acreage.length() - 1));
		
		// 处理信息来源
		productDetail.setSource("1");

		dao.insertDetail(productDetail);
	}
}
