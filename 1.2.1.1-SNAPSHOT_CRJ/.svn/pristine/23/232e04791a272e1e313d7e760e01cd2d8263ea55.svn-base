package com.ycdc.appserver.bus.service.flow;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ycdc.appserver.model.syscfg.DictiItem;

/**
 * 流程
 * @author suntf
 * @date 2016年12月26日
 */
public interface MFlowService
{
	/**
	 * 任务状态列表
	 * @return
	 */
	List<DictiItem> taskStateList();
	
	/**
	 * 类型列表
	 * @return
	 */
	List<Map<String,String>> taskTypeList(String type);
	
	/**
	 * 类型列表
	 * @return
	 */
	List<Map<String,String>> loadAreaList(String type);
	
	
	/**
	 * 待办列表
	 * @param json
	 * @return
	 */
	List<Map<String,String>> todoTask(JSONObject json);
}
