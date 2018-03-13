package pccom.web.mobile.flow.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;
import pccom.common.util.Batch;
import pccom.web.beans.SystemConfig;
import pccom.web.beans.User;
import pccom.web.flow.base.FlowBase;
import pccom.web.flow.bean.TaskStepBean;
import pccom.web.flow.interfaces.FlowStepDisposeInterface;
import pccom.web.flow.util.FlowStepState;
import pccom.web.flow.util.FlowTaskState;

/**
 * 流程处理实现类
 * @author 雷杨
 *
 */
@Service("miFlowService")
public class MiFlowService extends FlowBase{

	/**
	 * 获取状态信息
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	public Object getStateList(HttpServletRequest request,
			HttpServletResponse response) {
		String groupId = "FLOW.INFO.STATUS";// 
		String sql = getSql("systemconfig.querydictionary");
		String sql1 = getSql("task.getPath");
		request.setAttribute("task_path", db.queryForString(sql1));
		return db.queryForList(sql,new Object[] { groupId });
	}

	/**
	 * 获取任务类型
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	public Object getTypeList(HttpServletRequest request,
			HttpServletResponse response) {
		String sql = getSql("task.getType");
		return db.queryForList(sql,new Object[] { 0 });
	}

	/**
	 * 类型级联
	 * 
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	public Object getType(HttpServletRequest request,
			HttpServletResponse response) {
		String sup = req.getAjaxValue(request, "sup");
		String sql = getSql("task.getType");
		Map<String,Object> returnMap = getReturnMap(1);
		returnMap.put("list",db.queryForList(sql,new Object[]{sup}) );
		return returnMap;
	}

	/**
	 * 获取对应的我发起的任务列表信息
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	public Object getMyStartTaskList(HttpServletRequest request,
			HttpServletResponse response) { 
		String keyWord = req.getAjaxValue(request, "keyWord");//关键字  任务名称\任务编号
		String types = req.getValue(request, "taskType");//类型
		String taskState = req.getAjaxValue(request, "taskState");//状态
		String lx = req.getAjaxValue(request, "lx");//2代表获取我待处理的任务信息
		User user = getUser(request);
		List<String> params = new ArrayList<String>();
		String sql = getSql("task.myStartTask.main");
		if(!"".equals(keyWord))
		{
			sql += getSql("task.disposetTask.keyWord");
			params.add("%"+keyWord+"%");
			params.add("%"+keyWord+"%");
		}
		if(!"".equals(taskState))
		{
			sql += getSql("task.myStartTask.taskState");
			params.add(taskState); 
		}
		
		if(!"".equals(types))
		{
			sql += getSql("task.myStartTask.tasktypeids");
			params.add(types);
			params.add("%-"+types+"-%");
		}
		
		if("2".equals(lx))
		{
			//获取待处理信息
			sql += getSql("task.myStartTask.mycl");
			String org = user.getOrgId();
			String role = user.getRoles();
			String oper = user.getId();
			String[] orgs = org.split(",");
			String[] roles = role.split(",");
			String orgsql = "";
			String rulesql = "";
			for(int i=0;i<roles.length;i++)
			{
				if(!"".equals(roles[i]))
				{ 
					rulesql += " OR FIND_IN_SET(?,e.`now_role_id`) > 0";
					params.add(roles[i]);
				}
			}
			for(int i=0;i<orgs.length;i++)
			{
				if(!"".equals(orgs[i]))
				{
					orgsql += " OR FIND_IN_SET(?,e.`now_org_id`) > 0";
					params.add(orgs[i]);
				}
			}
			sql = sql.replace("#ORG#", orgsql).replace("#RULE#", rulesql);
			params.add(oper);
		}else if("1".equals(lx)){//我发起的流程
			sql += getSql("task.myStartTask.myfq");
			params.add(user.getId());
		}else if("3".equals(lx)){//我处理过的流程
			
		}
		sql += getSql("task.myStartTask.orderby");
		return this.getMobileList(request, sql, params);
	}

	/**
	 * 获取订单详细信息
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	public Object getShowFlowDetail(HttpServletRequest request,
			HttpServletResponse response) {
		logger.debug("获取用户信息：",getUser(request));
		request.setAttribute("isMobile", 1);
		String task_id = req.getAjaxValue(request, "task_id");
		String sql = getSql("task.showDetail.main")+ getSql("task.showDetail.taskid");
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("taskdetail", db.queryForMap(sql,new Object[]{task_id}));
		String sql1 = getSql("task.getPath");
		request.setAttribute("task_path", db.queryForString(sql1));
		sql = getSql("task.showDetail.showStepList.main")+getSql("task.showDetail.showStepList.orderby");
		returnMap.put("stepList", db.queryForList(sql,new Object[]{task_id}));
		return returnMap;
	}

	/**
	 * 获取步骤处理详细信息
	 * 
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showStepDetail(final HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("isMobile", 1);
		//获取当前步骤详细信息
		Object obj = db.doInTransaction(new Batch() {
			@Override
			public Object execute() throws Exception {
				String step_id = req.getAjaxValue(request, "step_id");
				String edit = req.getAjaxValue(request, "edit");
				String sql = getSql("task.showDetail.showBzDetail.main");
				Map<String,Object> map = db.queryForMap(sql,new Object[]{step_id});
				String html_path = str.get(map, "html_path");
				//获取对应的处理class
				if(!"".equals(str.get(map, "dispose_class"))){
					try {
						Class cls = Class.forName(str.get(map, "dispose_class"));
						Object obj = cls.newInstance();
						if(obj instanceof FlowStepDisposeInterface){
							FlowStepDisposeInterface flow = (FlowStepDisposeInterface)obj;
							Object returnObj = flow.showStepDetail(this,request,step_id,str.get(map, "task_id"),"1".equals(edit)?true:false,str.get(map, "cfg_step_id"));
							if(returnObj instanceof Map){
								request.setAttribute("flowDetaiMap", (Map<String,Object>)returnObj);
								return html_path;
							}else{
								return null;
							}
						}else{
							return null;
						}
					} catch(Exception e){
						e.printStackTrace();
						return -111;
					}
				}else{
					return html_path;
				}
			}
		});
		if(obj != null){
			String result = "flow/pages/base/default";
			return "".equals(String.valueOf(obj))?result:String.valueOf(obj);
		}else{
			return "flow/pages/base/default";
		}
	}

	/**
	 * 获取我发起的任务列表
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	public Object getDisposetTaskList(HttpServletRequest request,
			HttpServletResponse response) {
		String taskName = req.getAjaxValue(request, "keyWord");//任务名称
		String taskCode = req.getAjaxValue(request, "taskCode");//任务编号
		String sendTime = req.getAjaxValue(request, "sendTime");//发起时间
		String types = req.getAjaxValue(request, "taskType");//类型
		String typelike = req.getAjaxValue(request, "taskType");//类型
		String status = req.getAjaxValue(request, "taskState");//类型
		String lx = req.getAjaxValue(request, "lx");//2代表获取我待处理的任务信息
		User user = getUser(request);
		
		List<String> params = new ArrayList<String>();
		String sql = getSql("task.disposetTask.main");
		 
		if(!"".equals(taskName)){
			sql += getSql("task.disposetTask.keyWord");
			params.add("%"+taskName+"%");
			params.add("%"+taskName+"%");
		}
		
		if(!"".equals(status)){
			sql += getSql("task.disposetTask.taskState");
			params.add(status);
		}
		
		if(!"".equals(types)){
			sql += getSql("task.disposetTask.tasktypeids");
			params.add(types);
			params.add("%-"+typelike+"-%");
		}
		
		if(!"".equals(sendTime)){
			String state_time = sendTime.split("至")[0];
			String end_time = sendTime.split("至")[1];
			sql += getSql("task.disposetTask.state_time");
			sql += getSql("task.disposetTask.end_time");
			params.add(state_time);
			params.add(end_time);
		}
		logger.debug("user.getOrgId():"+user.getOrgId());
		sql += getSql("task.disposetTask.mycl");
		String org = user.getOrgId();
		String role = user.getRoles();
		String oper = user.getId();
		String[] orgs = org.split(",");
		String[] roles = role.split(",");
		String orgsql = "";
		String rulesql = "";
		for(int i=0;i<roles.length;i++){
			if(!"".equals(roles[i])){ 
				rulesql += " OR FIND_IN_SET(?,e.`now_role_id`) > 0";
				params.add(roles[i]);
			}
		}
		for(int i=0;i<orgs.length;i++){
			if(!"".equals(orgs[i])){
				orgsql += " OR FIND_IN_SET(?,e.`now_org_id`) > 0";
				params.add(orgs[i]);
			}
			logger.debug(orgs[i]);
		}
		sql = sql.replace("#ORG#", orgsql).replace("#RULE#", rulesql);
		params.add(oper);
		//getPageList(request,response, sql,params.toArray(), "task.disposetTask.orderby");
		
		sql += getSql("task.disposetTask.orderby");
		return this.getMobileList(request, sql, params);
	}
	
	
	/**
	 * 收入
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	public Object getFinancialList(HttpServletRequest request,HttpServletResponse response) 
	{
		String agreeRankId = req.getAjaxValue(request, "agreeRankId");//租赁合约ID
		String secondarytype = req.getAjaxValue(request, "secondarytype");//支付类型
		List<String> params = new ArrayList<String>();
		String sql = getSql("task.getFinancial.main");
		if(!"".equals(agreeRankId))
		{
			sql += getSql("task.getFinancial.agreeRankId");
			params.add(agreeRankId);
		}
		if(!"".equals(secondarytype))
		{
			sql += getSql("task.getFinancial.secondarytype");
			params.add(secondarytype);
		}
		return db.queryForList(sql, params);
	}
	
	
	/**
	 * 支出
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	public Object getPayList(HttpServletRequest request,HttpServletResponse response) 
	{
		String agreeRankId = req.getAjaxValue(request, "agreeRankId");//租赁合约ID
		String secondarytype = req.getAjaxValue(request, "secondarytype");//支付类型
		List<String> params = new ArrayList<String>();
		String sql = getSql("task.getPay.main");
		if(!"".equals(agreeRankId))
		{
			sql += getSql("task.getPay.agreeRankId");
			params.add(agreeRankId);
		}
		if(!"".equals(secondarytype))
		{
			sql += getSql("task.getPay.secondarytype");
			params.add(secondarytype);
		}
		return db.queryForList(sql, params);
		//return this.getMobileList(request, sql, params);
	}
	
	

	/**
	 * 待处理信息
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object showDetailFlowDetail(final HttpServletRequest request,
			HttpServletResponse response) {
		String step_id = req.getAjaxValue(request, "step_id");
		request.setAttribute("isMobile", 1);
		//获取当前步骤详细信息
		String sql = getSql("task.showDetail.showBzDetail.main");
		Map<String,Object> map = db.queryForMap(sql,new Object[]{step_id});
		logger.debug("map:"+map);
		sql = getSql("task.showDetail.main")+ getSql("task.showDetail.taskid");
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("stepMap", map);
		returnMap.put("taskdetail", db.queryForMap(sql,new Object[]{str.get(map, "task_id")}));
		String sql1 = getSql("task.getPath");
		request.setAttribute("task_path", db.queryForString(sql1));
		sql = getSql("task.showDetail.showStepList.main")+getSql("task.showDetail.showStepList.orderby"); 
		returnMap.put("stepList", db.queryForList(sql,new Object[]{str.get(map, "task_id")}));
		
		return returnMap;
	}

	/**
	 * 已经处理订单详细信息
	 * 
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	public Object yetDisposeDitail(HttpServletRequest request,
			HttpServletResponse response) {
		String task_id = req.getAjaxValue(request, "task_id");
		request.setAttribute("isMobile", 1);
		String sql = getSql("task.showDetail.main")+ getSql("task.showDetail.taskid");
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("taskdetail", db.queryForMap(sql,new Object[]{task_id}));
		String sql1 = getSql("task.getPath");
		request.setAttribute("task_path", db.queryForString(sql1));
		sql = getSql("task.showDetail.showStepList.main")+getSql("task.showDetail.showStepList.orderby");
		returnMap.put("stepList", db.queryForList(sql,new Object[]{task_id}));
		
		return returnMap;
	}

	/**
	 * 已处理订单列表
	 * 
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	public Object yetDisposeTask(HttpServletRequest request,
			HttpServletResponse response) {
		
		return null;
	}

	/**
	 * 获取我已处理的订单信息
	 * 
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	public Object getMyYetTaskList(HttpServletRequest request,
			HttpServletResponse response) {
		//data:{taskType:$('#taskType').val(),pageNumber:page_num,keyWord:$('#keyWord').val(),lx:3,taskState:$('#taskState').val()},
		
		String taskName = req.getAjaxValue(request, "keyWord");//任务名称
		String taskCode = req.getAjaxValue(request, "taskCode");//任务编号
		String sendTime = req.getAjaxValue(request, "sendTime");//发起时间
		String types = req.getAjaxValue(request, "taskType");//类型
		String status = req.getAjaxValue(request, "taskState");//类型
		String typelike = req.getAjaxValue(request, "taskType");//类型
		String lx = req.getAjaxValue(request, "lx");//2代表获取我待处理的任务信息
		User user = getUser(request);
		
		List<String> params = new ArrayList<String>();
		String sql = getSql("task.yetTaskList.main");
		if(!"".equals(taskName)){
			sql += getSql("task.disposetTask.keyWord");
			params.add("%"+taskName+"%");
			params.add("%"+taskName+"%");
		}
		if(!"".equals(status)){
			sql += getSql("task.yetTaskList.taskState");
			params.add(status);
		}
		if(!"".equals(types)){
			sql += getSql("task.yetTaskList.tasktypeids");
			params.add(types);
			params.add("%-"+typelike+"-%");
		}
		if(!"".equals(sendTime)){
			String state_time = sendTime.split("至")[0];
			String end_time = sendTime.split("至")[1];
			sql += getSql("task.yetTaskList.state_time");
			sql += getSql("task.yetTaskList.end_time");
			params.add(state_time);
			params.add(end_time);
		}
		if("2".equals(lx)){//获取待处理信息
			sql += getSql("task.yetTaskList.mycl");
			String org = user.getOrgId();
			String role = user.getRoles();
			String oper = user.getId();
			String[] orgs = org.split(",");
			String[] roles = role.split(",");
			String orgsql = "";
			String rulesql = "";
			for(int i=0;i<roles.length;i++){
				if(!"".equals(roles[i])){ 
					rulesql += " OR FIND_IN_SET(?,e.`now_role_id`) > 0";
					params.add(roles[i]);
				}
			}
			for(int i=0;i<orgs.length;i++){
				if(!"".equals(orgs[i])){
					orgsql += " OR FIND_IN_SET(?,e.`now_org_id`) > 0";
					params.add(orgs[i]);
				}
			}
			sql = sql.replace("#ORG#", orgsql).replace("#RULE#", rulesql);
			params.add(oper);
		}else if("1".equals(lx)){//我发起的流程
			sql += getSql("task.yetTaskList.mycl");
			params.add(user.getId());
		}else if("3".equals(lx)){//我处理过的流程
			sql += getSql("task.yetTaskList.mycl");
			params.add(user.getId());
		}
		sql += getSql("task.yetTaskList.orderby");
		return this.getMobileList(request, sql, params);
	//	getPageList(request,response, sql,params.toArray(), "task.yetTaskList.orderby");
	}

	/**
	 * 获取下一步处理信息
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	public Object getNowNextStepList(HttpServletRequest request,
			HttpServletResponse response) {
		String step_id = req.getAjaxValue(request, "step_id");//当前步骤id
		String task_cfg_id = req.getAjaxValue(request, "task_cfg_id");//当前配置的流程信息
		String sql = getSql("task.getNowNextStepList.main");
		String next_step_id = db.queryForString(sql,new Object[]{step_id});
		sql = getSql("task.getNowNextStepList.list");
		sql = sql.replace("##", next_step_id);
		//需要检查是否存在并列任务在同步执行
		return db.queryForList(sql,new Object[]{task_cfg_id});
	}

	/**
	 * 在任务步骤刚下发的情况下更改对应状态为刚阅读
	 * @author 雷杨
	 * @param request
	 * @param response
	 */
	public void changeFlowState(HttpServletRequest request,
			HttpServletResponse response) {
		String sql = getSql("task.disposetTask.changeState");
		db.update(sql,new Object[]{req.getAjaxValue(request, "step_id")});
	}

	/**
	 * 处理步骤信息
	 * 
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object saveDisposeFlow(final HttpServletRequest request,
			HttpServletResponse response) {
		Object obj = db.doInTransaction(new Batch() {

			@Override
			public Object execute() throws Exception {
				String flowStepState = req.getAjaxValue(request, "flowStepState");
				String flowRemark = req.getAjaxValue(request, "flowRemark");
				String task_id = req.getAjaxValue(request, "task_id");
				String task_code = req.getAjaxValue(request, "task_code");
				String step_id = req.getAjaxValue(request, "step_id");
				String cfg_step_id = req.getAjaxValue(request, "cfg_step_id");
				String task_cfg_id = req.getAjaxValue(request, "task_cfg_id");
				User u = getUser(request);
				logger.debug("flowStepState:"+flowStepState);
				if("".equals(flowStepState)){
					return getReturnMap(-1,"请选择处理状态");
				}
				Map<String,Object> stepMap = this.queryForMap(getSql("task.getStep").replace("##", cfg_step_id),new Object[]{task_cfg_id});
				logger.debug("stepMap:"+stepMap);
				if(stepMap.isEmpty()){//未查询到步骤配置信息
					return getReturnMap("-61","未查询到当前步骤配置信息");
				}
				TaskStepBean taskStepBean = new TaskStepBean();
				taskStepBean.setTask_id(task_id+"");
				taskStepBean.setStep_id(step_id);
				taskStepBean.setOper_id(u.getId());
				taskStepBean.setStep_name(str.get(stepMap, "step_name"));
				taskStepBean.setNext_step_id("");
				taskStepBean.setIs_overtime("0");
				taskStepBean.setCfg_step_id(cfg_step_id);
				taskStepBean.setRemark(flowRemark);
				taskStepBean.setState(flowStepState);
				taskStepBean.setDispose_class(str.get(stepMap, "dispose_class"));
				//先处理当前步骤信息
				Map<String,Object> mapStep = disposeStep(request, this, taskStepBean);
				int isover = 0;//任务是否全部执行完毕
				String taskStepId = "";
				String taskStepName = "";
				logger.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+str.get(mapStep, "state"));
				if("0".equals(str.get(mapStep, "state"))){//自定义的下一步操作步骤
					if("".equals(str.get(mapStep, "step_id"))){
						return getReturnMap(-99,"自定义步骤失败");
					}
					if("".equals(str.get(mapStep, "step_name"))){
						return getReturnMap(-99,"自定义步骤失败");
					}
					taskStepId = str.get(mapStep, "step_id");
					taskStepName = str.get(mapStep, "step_name");
					taskStepBean.setNext_step_id(str.get(mapStep, "step_id"));
					taskStepBean.setFile_id(str.get(mapStep, "file_id"));
					//更新当前步骤处理信息
					String sql = getSql("task.disposetTask.updateStep");
					this.update(sql,new Object[]{taskStepBean.getFile_id(),taskStepBean.getIs_overtime(),taskStepBean.getNext_step_id(),taskStepBean.getOper_id(),taskStepBean.getRemark(),taskStepBean.getState(),taskStepBean.getStep_id()});
					return getReturnMap(1,"操作成功");
				}else if("1".equals(str.get(mapStep, "state"))){//系统统一定义下一步
					if(FlowStepState.END_STEP.equals(flowStepState)){//
						//根据当前步骤的配置信息查下一步的步骤信息
						List<Map<String,Object>> nextList = this.queryForList(getSql("task.getStep").replace("##", str.get(stepMap, "next_step_id")),new Object[]{task_cfg_id});
						String nextStepIds = "";
						String nextStepNames = "";
						logger.debug("nextList:"+nextList);
						//循环插入下一步信息
						for(int i=0;i<nextList.size();i++){
							Map<String,Object> nextStepMap = nextList.get(i);
							//
							String sql = getSql("task.insertStep");
							
							int stepId = this.insert(sql, new Object[]{task_id,str.get(nextStepMap, "step_name"),step_id,str.get(nextStepMap, "step_id"),str.get(nextStepMap, "org_id"),str.get(nextStepMap, "rule_id"),str.get(nextStepMap, "oper")});
							if(stepId == -1){
								return getReturnMap(-33,"创建下一步任务失败");
							}
							if("0".equals(str.get(nextStepMap, "next_step_id"))){//当前是最后一步，直接结束任务
								sql = getSql("task.disposetTask.updateStep");
								this.update(sql,new Object[]{"","0","0",u.getId(),"",FlowStepState.END_STEP,stepId});
								isover = 1;
							}
							nextStepIds += stepId+",";
							nextStepNames += str.get(nextStepMap, "step_name")+",";
						}
						taskStepId = nextStepIds.substring(0,nextStepIds.length()-1);
						taskStepName = nextStepNames.substring(0,nextStepNames.length()-1);
						taskStepBean.setNext_step_id(nextStepIds.substring(0,nextStepIds.length()-1));
					}else if(FlowStepState.HANG_STEP.equals(flowStepState)){//挂起 不发送下一步处理 只记录当前步骤信息
						taskStepId = step_id;
						taskStepName = str.get(stepMap, "step_name");
						isover = 2;
					}else if(FlowStepState.ROLLBACK_STEP.equals(flowStepState)){//回退当前任务到上一步操作
						//回退的时候需要区分是否是首第一个步骤就退回了，如果是第一个步骤就进行回退流程直接结束，否则继续
						
						//查询出当前步骤对应的上一步处理信息
						String sql = getSql("task.getStepMap");
						Map<String,Object> nowStepMap = this.queryForMap(sql,new Object[]{step_id});
						if(nowStepMap.isEmpty()){//未查询到当前步骤信息
							return getReturnMap(-34,"未查询到当前步骤信息");
						}
						String nowSupStepId = str.get(nowStepMap, "sup_step_id");//当前步骤对应的上一步处理信息stepid
						Map<String,Object> supStepMap1 = this.queryForMap(sql,new Object[]{nowSupStepId});
						String supStepId = str.get(supStepMap1,"sup_step_id");
						logger.debug("nowSupStepId:"+nowSupStepId);
						if("-1".equals(supStepId)){//在回退就回退到第一步了，此处就直接结束流程
							isover = 1;
							//查询出上一步处理人信息
							Map<String,Object> supStepMap = this.queryForMap(getSql("task.getOverStep"),new Object[]{task_cfg_id});
							if(supStepMap.isEmpty()){
								return getReturnMap(-34,"未查询到上一步骤信息");
							}
							sql = getSql("task.insertStep");
							int stepId = this.insert(sql, new Object[]{task_id,str.get(supStepMap, "step_name"),step_id,str.get(supStepMap, "step_id"),"","",str.get(supStepMap, "oper_id")});
							if(stepId == -1){
								return getReturnMap(-33,"创建下一步任务失败");
							}
							
							sql = getSql("task.disposetTask.updateStep");
							this.update(sql,new Object[]{"","0","0",u.getId(),"",FlowStepState.END_STEP,stepId});
							
							taskStepId = stepId+"";
							taskStepName = str.get(supStepMap, "step_name");
							taskStepBean.setNext_step_id(stepId+"");
						}else{
							//查询出上一步处理人信息
							Map<String,Object> supStepMap = this.queryForMap(sql,new Object[]{nowSupStepId});
							
							if(supStepMap.isEmpty()){
								return getReturnMap(-34,"未查询到上一步骤信息");
							}
							
							sql = getSql("task.insertStep");
							int stepId = this.insert(sql, new Object[]{task_id,str.get(supStepMap, "step_name"),step_id,str.get(supStepMap, "cfg_step_id"),"","",str.get(supStepMap, "oper_id")});
							if(stepId == -1){
								return getReturnMap(-33,"创建下一步任务失败");
							}
							taskStepId = stepId+"";
							taskStepName = str.get(supStepMap, "step_name");
							taskStepBean.setNext_step_id(stepId+"");
						}
					}
					//更新当前步骤处理信息
					String sql = getSql("task.disposetTask.updateStep");
					this.update(sql,new Object[]{taskStepBean.getFile_id(),taskStepBean.getIs_overtime(),taskStepBean.getNext_step_id(),taskStepBean.getOper_id(),taskStepBean.getRemark(),taskStepBean.getState(),taskStepBean.getStep_id()});
					//更新主线任务状态
					sql = getSql("task.disposetTask.updateTask"); 
					this.update(sql.replace("#endtime#", isover==1?"now()":"null"),new Object[]{0,isover==2?FlowTaskState.BREAK_TASK:isover==1?FlowTaskState.OVER_TASK:FlowTaskState.START_TASK,0,taskStepId,taskStepName,task_id});
				}else if("2".equals(str.get(mapStep, "state"))){//自定义下一步操作，不更改当前步骤状态信息
					
					if("".equals(str.get(mapStep, "step_id"))){
						return getReturnMap(-99,"自定义步骤失败");
					}
					if("".equals(str.get(mapStep, "step_name"))){
						return getReturnMap(-99,"自定义步骤失败");
					}
					taskStepId = str.get(mapStep, "step_id");
					taskStepName = str.get(mapStep, "step_name");
					taskStepBean.setNext_step_id(str.get(mapStep, "step_id"));
					taskStepBean.setFile_id(str.get(mapStep, "file_id"));
					
					return getReturnMap(1,"操作成功");
				}
				return mapStep;
			}
			
		});
		if(obj == null){
			return getReturnMap(-1,"处理异常");
		}else{
			return (Map<String,Object>)obj;
		}
	}

	/**
	 * 获取步骤处理详细信息
	 * 
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String showTaskDetail(final HttpServletRequest request,
			HttpServletResponse response) {
		//获取当前步骤详细信息
		Object obj = db.doInTransaction(new Batch() {
			@Override
			public Object execute() throws Exception {
				String task_cfg_id = req.getAjaxValue(request, "task_cfg_id");
				String task_id = req.getAjaxValue(request, "task_id");
				String step_id = req.getAjaxValue(request, "step_id");
				String edit = req.getAjaxValue(request, "edit");
				String sql = getSql("task.showDetail.showBzDetail.main");
				Map<String,Object> map = db.queryForMap(sql,new Object[]{step_id});
				
				sql = getSql("task.getCfgTask");
				Map<String,Object> taskMap = db.queryForMap(sql,new Object[]{task_cfg_id});
				
				String html_path = str.get(taskMap, "html_path");
				String dispose = str.get(taskMap, "dispose_class");
				logger.debug("dispose:"+dispose);
				//获取对应的处理class
				if(!"".equals(dispose)){
					try {
						Class cls = Class.forName(dispose);
						Object obj = cls.newInstance();
						if(obj instanceof FlowStepDisposeInterface){
							FlowStepDisposeInterface flow = (FlowStepDisposeInterface)obj;
							Object returnObj = flow.showStepDetail(this,request,step_id,task_id,"1".equals(edit)?true:false,str.get(map, "cfg_step_id"));
							if(returnObj instanceof Map){
								request.setAttribute("flowDetaiMap", (Map<String,Object>)returnObj);
								return html_path;
							}else{
								return null;
							}
						}else{
							return null;
						}
					} catch(Exception e){
						e.printStackTrace();
						return -111;
					}
				}else{
					return html_path;
				}
			}
		});
		if(obj != null){
			String result = "flow/pages/base/default";
			return "".equals(String.valueOf(obj))?result:String.valueOf(obj);
		}else{
			return "flow/pages/base/default";
		}
	}

	/**
	 * 删除操作
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object deleteFlow(final HttpServletRequest request,
			HttpServletResponse response) {
		Object obj = db.doInTransaction(new Batch() {

			@Override
			public Object execute() throws Exception {
				String task_id = req.getAjaxValue(request, "task_id");
				String task_cfg_id = req.getAjaxValue(request, "task_cfg_id");
				String sql = getSql("task.getCfgTask");
				Map<String,Object> taskMap = db.queryForMap(sql,new Object[]{task_cfg_id});
				String dispose = str.get(taskMap, "dispose_class");
				
				User u = getUser(request);
				//删除流程本身信息
				List<String> params = new ArrayList<String>();
				params.add(task_id);
				this.insertTableLog(request, "yc_task_info_tab", " and a.task_id = ?", "删除流程", params,u.getId());
				this.update(getSql("task.deleteFlow"),params.toArray());
				this.insertTableLog(request, "yc_task_step_info_tab", "  and a.task_id = ?", "删除流程", params,u.getId());
				this.update(getSql("task.deleteFlowStep"),params.toArray());
				//获取对应的处理class
				if(!"".equals(dispose)){
					try {
						Class cls = Class.forName(dispose);
						Object obj = cls.newInstance();
						if(obj instanceof FlowStepDisposeInterface){
							FlowStepDisposeInterface flow = (FlowStepDisposeInterface)obj;
							Object returnObj = flow.deleteFlow(this, task_id);
							if(returnObj instanceof Map){
								return (Map<String,Object>)returnObj;
							}else{
								return null;
							}
						}else{
							return null;
						}
					} catch(Exception e){
						e.printStackTrace();
						return null;
					}
				}else{
					return null;
				}
			}
		});
		if(obj == null){
			return getReturnMap(-3, "操作失败");
		}else{
			return (Map<String,Object>)obj;
		}
	}
	
	/**
	 * 获取任务类型
	 * @author 雷杨
	 * @param request
	 * @param response
	 * @return
	 */
	public Object getTypeCreateList(HttpServletRequest request,
			HttpServletResponse response) {
		String sql = getSql("task.getType");
		return db.queryForList(sql,new Object[] { 0 });
	}
	
	/**
	 * 获取管理人员名单
	 * @return list
	 * @author liuf
	 * @date 2016年9月26日
	 */
	public List<?> queryManager(HttpServletRequest request,HttpServletResponse response)
	{
		String role_Id = req.getAjaxValue(request, "role_Id");
		String sql = getSql("systemconfig.queryManager.main").replace("####", "("+role_Id+")");
		return db.queryForList( sql);
	}
	/**
	 * 获取录入数据
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public Object getInfo(HttpServletRequest request,HttpServletResponse response)
	{
		String task_id = req.getAjaxValue(request, "task_id");
		String sql = getSql("checkhouse.query.main");
		List<String> params = new ArrayList<String>();
		if (!"".equals(task_id))
		{
			sql += getSql("checkhouse.query.task_id");
			params.add(task_id);
		}
		Map<String, Object> returnMap = db.queryForMap(sql.replaceAll("###", SystemConfig.getValue("FTP_HTTP")+ SystemConfig.getValue("FTP_LEASEORDER_HTTP_PATH")),params.toArray());
		return returnMap;
	}
}
