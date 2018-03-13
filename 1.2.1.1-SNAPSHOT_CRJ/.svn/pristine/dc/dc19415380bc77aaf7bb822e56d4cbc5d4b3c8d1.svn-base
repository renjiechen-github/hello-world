package com.ycdc.spider;

import javax.annotation.Resource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.geccocrawler.gecco.GeccoEngine;
import com.geccocrawler.gecco.annotation.Request;
import com.geccocrawler.gecco.pipeline.PipelineFactory;
import com.geccocrawler.gecco.request.HttpGetRequest;
import com.geccocrawler.gecco.request.HttpRequest;
import com.geccocrawler.gecco.spider.HtmlBean;
import com.ycdc.spider.dao.ProductDAO;

/**
 * @author 孙诚明
 * @version 1.0
 * @since 2016年10月12日
 * @category com.ycdc.data
 */
@Component("spiderMessage")
public class StartMain implements ApplicationContextAware, HtmlBean
{
	private ApplicationContext context;
	
	private static final long serialVersionUID = 6438882840822456767L;

	@Request
	private HttpRequest request;
	
	@Resource
	private ProductDAO dao;

	public void spiderStart() throws Exception
	{
		dao.deleteTable();
		
		PipelineFactory springPipelineFactory = (PipelineFactory) context.getBean("springPipelineFactory");
		
		// 先获取分类列表
		HttpGetRequest start = new HttpGetRequest("http://nj.rent.house365.com/district_i1/dl_r1-p1.html");
		start.setCharset("gb2312");
		GeccoEngine.create().classpath("com.ycdc.spider.model")
		// 开始抓取的页面地址
		.start(start)
		// 开启几个爬虫线程
		.thread(20)
		.pipelineFactory(springPipelineFactory)
		// 单个爬虫每次抓取完一个请求后的间隔时间
		.interval(2000)
		.run();
	}
	
	public HttpRequest getRequest()
	{
		return request;
	}

	public void setRequest(HttpRequest request)
	{
		this.request = request;
	}

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException
	{
		this.context = context;
	}

}
