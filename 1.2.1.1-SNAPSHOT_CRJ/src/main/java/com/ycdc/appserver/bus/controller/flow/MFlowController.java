
package com.ycdc.appserver.bus.controller.flow;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pccom.common.util.StringHelper;

import com.ycdc.appserver.bus.controller.BaseController;
import com.ycdc.appserver.bus.service.flow.MFlowService;

/**
 * 流程
 * 
 * @author suntf
 * @date 2016年12月26日
 */
@Controller
@RequestMapping("appserver/flow/")
public class MFlowController extends BaseController
{

	@Autowired
	private MFlowService mFlowService;

	/**
	 * 加载任务列表信息
	 */
	@RequestMapping(value = "loadTaskConditionList.do", method = RequestMethod.POST)
	public @ResponseBody
	Object loadTaskConditionList(@RequestBody JSONObject pjson)
	{
		JSONObject json = new JSONObject();
		json.put("stateList", mFlowService.taskStateList());
		String type = "0";
		if (StringHelper.get(pjson, "type") != null
				&& !"".equals(StringHelper.get(pjson, "type")))
		{
			type = StringHelper.get(pjson, "type");
		}
		json.put("typeList", mFlowService.taskTypeList(type));
		json.put("areas", mFlowService.loadAreaList("3"));
		log.debug(json.toString());
		// logger.debug(json);
		return json;
	}

	/**
	 * 加载我的待办
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping("todoTask.do")
	public @ResponseBody
	Object todoTask(@RequestBody JSONObject json)
	{
		log.debug(json.toString());
		// logger.debug(json);
		List list = mFlowService.todoTask(json);
		log.debug(JSONArray.fromObject(list).toString());
		// logger.debug(JSONArray.fromObject(list));
		return list;
	}
}
