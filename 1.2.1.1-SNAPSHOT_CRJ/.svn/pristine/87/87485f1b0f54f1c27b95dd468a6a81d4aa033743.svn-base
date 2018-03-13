
package com.yc.rm.caas.appserver.bus.controller.flow;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.yc.rm.caas.appserver.base.support.BaseController;
import com.yc.rm.caas.appserver.bus.controller.flow.fo.FlowFo;
import com.yc.rm.caas.appserver.bus.service.flow.IFlowServ;
import com.yc.rm.caas.appserver.model.flow.FlowBean;

/**
 * 流程
 * 
 * @author suntf
 * @date 2016年12月26日
 */
@Controller
@RequestMapping("/caas/caasflow")
public class CaasFlowController extends BaseController
{

	@Autowired
	private IFlowServ _flowServImpl;

	/**
	 * 加载我的待办
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping("getWorkOrderConditionList")
	public @ResponseBody
	Object getWorkOrderConditionList()
	{	

		JSONObject json = new JSONObject();
		json.put("stateList", _flowServImpl.orderStateList());
		json.put("typeList", _flowServImpl.orderTypeList());
		log.debug(json.toString());
		return json;
	}
	
	/**
	 * 加载我的待办
	 * 
	 * @param json
	 * @return
	 */
	@RequestMapping("getWorkOrderList")
	public @ResponseBody
	Object getWorkOrderList(FlowFo fo)
	{
		log.debug(JSONObject.toJSON(fo).toString());
		List<FlowBean> list = _flowServImpl.getWorkOrderList(fo);
		log.debug(JSONObject.toJSONString(list));
		return list;
	}
}
