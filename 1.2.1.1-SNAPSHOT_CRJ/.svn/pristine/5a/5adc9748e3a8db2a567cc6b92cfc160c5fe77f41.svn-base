/**
 * date: 2017年5月19日
 */
package com.ycdc.dingding.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ycdc.dingding.model.entity.*;

/**
 * @name: LockController.java
 * @Description: 
 * @author duanyongrui
 * @since: 2017年5月19日
 */

@Controller
@RequestMapping(value = "/dingding")
public class LockController {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	/**
	 * @param lockEvent
	 * @param request
	 * @return 回调结果
	 * @Description: 门锁和网关操作回调
	 *//*
	@RequestMapping(value = "/eventCallBack.do", method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody BaseLockEntity eventCallBack(@RequestBody LockEvent lockEvent, HttpServletRequest request) {
		logger.debug("eventCallBack = "+ JSONObject.toJSONString(lockEvent).toString());
		return new BaseLockEntity(0, "回调成功");
	}*/
	
	/**
	 * @param lockEvent
	 * @param request
	 * @return 回调结果
	 * @Description: 密码操作回调
	 */
	@RequestMapping(value = "/passwordServiceCallBack.do", method=RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public @ResponseBody BaseLockEntity passwordServiceCallBack(@RequestBody Map<String, Object> lockCall, HttpServletRequest request) {
		if (lockCall.containsKey("service")) {
			// 密码操作回调
			LockPasswordService service = JSONObject.toJavaObject((JSON)JSONObject.toJSON(lockCall), LockPasswordService.class);
		} else {
			// 门锁和网关操作回调
			LockEvent event = JSONObject.toJavaObject((JSON)JSONObject.toJSON(lockCall), LockEvent.class);
		}
		logger.debug("passwordServiceCallBack = "+ lockCall.toString());
		return new BaseLockEntity(0, "回调成功");
	}
}
