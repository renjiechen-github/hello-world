package com.ycdc.spider;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.geccocrawler.gecco.pipeline.Pipeline;
import com.geccocrawler.gecco.spider.SpiderBean; 
import com.ycdc.core.plugin.jpush.PushManagerUtil;

@Service("consolePipeline")
public class ConsolePipeline implements Pipeline<SpiderBean> {
	public static final Logger logger = org.slf4j.LoggerFactory.getLogger(ConsolePipeline.class);
	@Override
	public void process(SpiderBean bean) {
		logger.debug(JSON.toJSONString(bean));
	}

}
