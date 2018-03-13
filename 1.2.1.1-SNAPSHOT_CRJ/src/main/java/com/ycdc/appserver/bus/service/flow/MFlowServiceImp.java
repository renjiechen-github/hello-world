
package com.ycdc.appserver.bus.service.flow;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pccom.common.util.StringHelper;

import com.ycdc.appserver.bus.dao.flow.IFlowDao;
import com.ycdc.appserver.bus.dao.syscfg.SyscfgMapper;
import com.ycdc.appserver.bus.service.BaseService;
import com.ycdc.appserver.model.syscfg.DictiItem;

/**
 * @author suntf
 * @date 2016年12月26日
 */
@Service("mFlowService")
public class MFlowServiceImp extends BaseService implements MFlowService
{

	@Autowired
	private SyscfgMapper syscfgMapper;
	@Autowired
	private IFlowDao flowDao;

	/*
	 * 任务列表
	 */
	@Override
	public List<DictiItem> taskStateList()
	{
		return syscfgMapper.getDictit("FLOW.INFO.STATUS");
	}

	/**
	 * 类型列表
	 * 
	 * @return
	 * @date 2016年12月26日
	 */
	@Override
	public List<Map<String, String>> taskTypeList(String type)
	{
		return flowDao.loadTaskType(type);
	}

	/**
	 * 区域列表
	 * 
	 * @return
	 * @date 2016年12月26日
	 */
	@Override
	public List<Map<String, String>> loadAreaList(String type)
	{
		return flowDao.loadAreaList(type);
	}

	/**
	 * 加载任务列表
	 * 
	 * @param json
	 * @return
	 * @date 2016年12月26日
	 */
	@Override
	public List<Map<String, String>> todoTask(JSONObject json)
	{
		String keyWord = StringHelper.get(json, "keyWord");
		String state = StringHelper.get(json, "state");
		String typeids = StringHelper.get(json, "typeId");
		String operId = StringHelper.get(json, "operId");
		int pageNumber = Integer.valueOf(StringHelper.get(json, "startPage"));
		int pageSize = Integer.valueOf(StringHelper.get(json, "pageSize"));
		String area_id = StringHelper.get(json, "area_id");
		String trading = StringHelper.get(json, "trading");
		String read_all = StringHelper.get(json, "read_all");
		String typeId = "4";// StringHelper.get(json, "typeids");
		String g_id = StringHelper.get(json, "g_id");
		String todoType = StringHelper.get(json, "todoType"); // 类型 1 我发起的 2 待办任务 3 已处理任务
		if ("1".equals(todoType))
		{
			List<Map<String, String>> returnList = flowDao.myStartTask(keyWord, state,
					typeId, pageNumber*pageSize, pageSize, operId, area_id,
					trading, typeids);
			List<Map<String, String>> newList = new ArrayList<>();
			for (Map<String, String> mp : returnList)
			{
				Map<String, String> tmpMap = new HashMap<String, String>();
				tmpMap.put("html_path", StringHelper.get(mp, "html_path"));
				tmpMap.put("dispose_class", StringHelper.get(mp, "dispose_class"));
				tmpMap.put("task_cfg_id", StringHelper.get(mp, "task_cfg_id"));
				tmpMap.put("step_id", StringHelper.get(mp, "step_id"));
				tmpMap.put("task_id", StringHelper.get(mp, "task_id"));
				tmpMap.put("typenames", StringHelper.get(mp, "typenames"));
				tmpMap.put("task_code", StringHelper.get(mp, "task_code"));
				tmpMap.put("name", StringHelper.get(mp, "name"));
				tmpMap.put("createtime", StringHelper.get(mp, "createtime"));
				tmpMap.put("statename", StringHelper.get(mp, "statename"));
				tmpMap.put("step_state_name", StringHelper.get(mp, "step_state_name"));
				tmpMap.put("step_name", StringHelper.get(mp, "step_name"));
				tmpMap.put("tradingname", StringHelper.get(mp, "tradingname"));
				tmpMap.put("areaname", StringHelper.get(mp, "areaname"));
				tmpMap.put("address", StringHelper.get(mp, "address"));
				tmpMap.put("opername", StringHelper.get(mp, "opername"));
				tmpMap.put("rolename", StringHelper.get(mp, "rolename"));
				tmpMap.put("ll", StringHelper.get(mp, "ll"));
				newList.add(tmpMap);
			}
			return newList;
		}
		else if ("2".equals(todoType))
		{
			String[] roles = StringHelper.get(json, "roleIds").split(",");
			String[] orgs = StringHelper.get(json, "orgIds").split(",");
			String orgsql = "";
			String rulesql = "";
			for (int i = 0; i < roles.length; i++)
			{
				if (!"".equals(roles[i]))
				{
					rulesql += " OR FIND_IN_SET(" + roles[i] + ",e.`now_role_id`) > 0";
				}
			}
			for (int i = 0; i < orgs.length; i++)
			{
				if (!"".equals(orgs[i]))
				{
					orgsql += " OR FIND_IN_SET(" + orgs[i] + ",e.`now_org_id`) > 0";
				}
				log.debug(orgs[i]);
				// logger.debug(orgs[i]);
			}
			List<Map<String, String>> returnList = flowDao.disposetTask(keyWord, state,
					typeId, pageNumber * pageSize, pageSize, operId, rulesql, orgsql,
					g_id, area_id, trading, typeids, read_all);
			List<Map<String, String>> newList = new ArrayList<>();
			for (Map<String, String> mp : returnList)
			{
				Map<String, String> tmpMap = new HashMap<String, String>();
				tmpMap.put("html_path", StringHelper.get(mp, "html_path"));
				tmpMap.put("dispose_class", StringHelper.get(mp, "dispose_class"));
				tmpMap.put("task_cfg_id", StringHelper.get(mp, "task_cfg_id"));
				tmpMap.put("step_id", StringHelper.get(mp, "step_id"));
				tmpMap.put("task_id", StringHelper.get(mp, "task_id"));
				tmpMap.put("typenames", StringHelper.get(mp, "typenames"));
				tmpMap.put("task_code", StringHelper.get(mp, "task_code"));
				tmpMap.put("name", StringHelper.get(mp, "name"));
				tmpMap.put("createtime", StringHelper.get(mp, "createtime"));
				tmpMap.put("statename", StringHelper.get(mp, "statename"));
				tmpMap.put("step_state_name", StringHelper.get(mp, "step_state_name"));
				tmpMap.put("step_name", StringHelper.get(mp, "step_name"));
				tmpMap.put("tradingname", StringHelper.get(mp, "tradingname"));
				tmpMap.put("areaname", StringHelper.get(mp, "areaname"));
				tmpMap.put("address", StringHelper.get(mp, "address"));
				tmpMap.put("opername", StringHelper.get(mp, "opername"));
				tmpMap.put("rolename", StringHelper.get(mp, "rolename"));
				tmpMap.put("ll", StringHelper.get(mp, "ll"));
				newList.add(tmpMap);
			}
			return newList;
		}
		else if ("3".equals(todoType))
		{
			List<Map<String, String>> returnList = flowDao.yetTaskList(keyWord, state,
					typeId, pageNumber * pageSize, pageSize, operId, area_id,
					trading, typeids);
			List<Map<String, String>> newList = new ArrayList<>();
			for (Map<String, String> mp : returnList)
			{
				Map<String, String> tmpMap = new HashMap<String, String>();
				tmpMap.put("html_path", StringHelper.get(mp, "html_path"));
				tmpMap.put("dispose_class", StringHelper.get(mp, "dispose_class"));
				tmpMap.put("task_cfg_id", StringHelper.get(mp, "task_cfg_id"));
				tmpMap.put("step_id", StringHelper.get(mp, "step_id"));
				tmpMap.put("task_id", StringHelper.get(mp, "task_id"));
				tmpMap.put("typenames", StringHelper.get(mp, "typenames"));
				tmpMap.put("task_code", StringHelper.get(mp, "task_code"));
				tmpMap.put("name", StringHelper.get(mp, "name"));
				tmpMap.put("createtime", StringHelper.get(mp, "createtime"));
				tmpMap.put("statename", StringHelper.get(mp, "statename"));
				tmpMap.put("step_state_name", StringHelper.get(mp, "step_state_name"));
				tmpMap.put("step_name", StringHelper.get(mp, "step_name"));
				tmpMap.put("tradingname", StringHelper.get(mp, "tradingname"));
				tmpMap.put("areaname", StringHelper.get(mp, "areaname"));
				tmpMap.put("address", StringHelper.get(mp, "address"));
				tmpMap.put("opername", StringHelper.get(mp, "opername"));
				tmpMap.put("rolename", StringHelper.get(mp, "rolename"));
				tmpMap.put("ll", StringHelper.get(mp, "ll"));
				newList.add(tmpMap);
			}
			return newList;
		}
		else
		{
			return new ArrayList<>();
		}
	}
}
