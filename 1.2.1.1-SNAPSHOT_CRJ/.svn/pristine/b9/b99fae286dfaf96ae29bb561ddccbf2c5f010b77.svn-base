
package com.ycdc.core.plugin.jpush.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.ycdc.core.plugin.jpush.controller.fo.JPushFo;
import com.ycdc.core.plugin.jpush.entity.PushMsgBean;
import com.ycdc.core.plugin.jpush.serv.IPushServ;

/**
 * @author 冷文佩
 * @version 1.0
 * @since 2017年3月6日
 * @category com.ycdc.core.plugin.jpush.controller
 */
@Controller
@RequestMapping("test")
public class PushMessage extends BaseController
{

	@Resource
	private IPushServ pushService;

	/**
	 * 测试消息发送
	 * 
	 * @return
	 */
	@RequestMapping(value = "/pushmessage.do", method = RequestMethod.GET)
	public @ResponseBody
	String test()
	{
		PushMsgBean pushBean = new PushMsgBean();
		// pushBean.setDelayDate("2017-3-6");
		pushBean.setExtrasparam("111");
		pushBean.setIfSend(1);
		pushBean.setMsg("message");
		pushBean.setName("test");
		pushBean.setAlias("-1");
		pushBean.setType(0);
		pushService.send(pushBean);
		// logger.debug(JSONObject.fromObject(u));
		return null;
	}

	/**
	 * 展示推送消息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/selectPushList.do", method = RequestMethod.GET)
	public @ResponseBody
	List<PushMsgBean> getPushList(JPushFo jPushFo)
	{
		jPushFo = new JPushFo();
		List<PushMsgBean> pushMsgList = null;
		jPushFo.setIfSend(0);
		log.debug("ifsend:" + jPushFo.getIfSend());
		jPushFo.setText("2");
		jPushFo.setBeginTime("2017-1-1");
		jPushFo.setEndTime("2017-3-10");
		jPushFo.setModel("task");
		pushMsgList = pushService.selectPushList(jPushFo);
		log.debug("pushMsgList:" + JSONObject.toJSONString(pushMsgList));
		return pushMsgList;
	}
}
