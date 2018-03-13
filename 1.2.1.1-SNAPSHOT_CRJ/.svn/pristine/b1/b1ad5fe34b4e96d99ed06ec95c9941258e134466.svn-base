package com.ycdc.spider.model;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import com.geccocrawler.gecco.annotation.PipelineName;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.scheduler.DeriveSchedulerContext;

/**
 * @author 孙诚明
 * @version 1.0
 * @since 2016年10月17日
 * @category com.ycdc.data.model
 */
@Component
@PipelineName("productListPipeline")
public class ProductListPipeline implements Pipeline<ProductList>
{

	@Override
	public void process(ProductList productList)
	{
		HttpRequest currRequest = productList.getRequest();
		// 页数截取
		String[] page = productList.getPage().split("/");
		// 当前页
		int currPage = Integer.valueOf(page[0]);
		// 总页数
		int totalPage = Integer.valueOf(page[1]);
		// 下一页继续抓取
		int nextPage = currPage + 1;
		if (nextPage <= totalPage)
		{
			String nextUrl = "";
			String currUrl = currRequest.getUrl();
			if (currUrl.indexOf("-p") != -1)
			{
				nextUrl = StringUtils.replaceOnce(currUrl, "-p" + currPage, "-p" + nextPage);
			}
			currRequest.setCharset("gb2312");
			DeriveSchedulerContext.into(currRequest.subRequest(nextUrl));
		}
	}

}
