package com.ycdc.core.plugin.sms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ycdc.core.base.BaseController;

@Controller
public class Lock extends BaseController
{
	@RequestMapping("/lockMsg.do")
	public void LockSendMsg(HttpServletResponse response) 
	{
		String phoneNumber = request.getParameter("phoneNumber");
		String password = request.getParameter("password");
		int templateCode = 11;
		Map<String, String> map = new HashMap<String, String>();
		map.put("user", phoneNumber);
		map.put("password", password);
		String result = SmsSendMessage.smsSendMessage(phoneNumber, map, templateCode);
		this.writeText(result, response);
	}
}
