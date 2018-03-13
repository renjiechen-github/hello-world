package pccom.web.flow.base;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import pccom.common.SQLconfig;
import pccom.common.util.Batch;
import pccom.common.util.BatchSql;
import pccom.common.util.DateHelper;
import pccom.web.beans.User;
import pccom.web.flow.bean.TaskBean;
import pccom.web.flow.bean.TaskCfg;
import pccom.web.flow.bean.TaskStepBean;
import pccom.web.flow.interfaces.FlowStepDisposeInterface;
import pccom.web.flow.util.FlowStepState;
import pccom.web.server.BaseService;
import sun.reflect.generics.scope.MethodScope;

/**
 * 流程处理基础类
 * 
 * @author 雷杨
 *
 */
public class FlowBase  extends BaseService{

	/**
	 * 
	 * 
	 * @author 雷杨
	 * @param obj
	 * @param sql
	 * @param params
	 * @return 1成功 0失败
	 */
	public int update(Object obj,String sql,Object[] params){
		if(obj instanceof BatchSql){
			((BatchSql)obj).addBatch(sql,params);
			return 1;
		} else if (obj instanceof Batch){
			try {
				return ((Batch)obj).update(sql, params);
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
		} else{
			logger.error("对象传递错误，无法查询结果："+sql);
			return 0;
		}
	}
	
	/**
	 * 新增并获取自增长序列
	 * @author 雷杨
	 * @param obj
	 * @param sql
	 * @param params
	 * @return
	 */
	public int insert(Object obj,String sql,Object[] params){
		if(obj instanceof BatchSql){
			return db.insert(sql, params);
		} else if (obj instanceof Batch){
			try {
				return ((Batch)obj).insert(sql, params);
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		} else{
			logger.error("对象传递错误，无法查询结果："+sql);
			return -1;
		}
	}
	
	public String queryString(Object obj,String sql,Object[] params){
		if(obj instanceof BatchSql){
			return db.queryForString(sql,params);
		} else if(obj instanceof Batch){
			return ((Batch)obj).queryForString(sql, params);
		} else {
			logger.error("对象传递错误，无法查询结果："+sql);
			return "";
		}
	}
	
	public Map<String,Object> queryMap(Object obj,String sql,Object[] params){
		if(obj instanceof BatchSql){
			return db.queryForMap(sql,params);
		} else if(obj instanceof Batch){
			return ((Batch)obj).queryForMap(sql, params);
		} else {
			logger.error("对象传递错误，无法查询结果："+sql);
			return new HashMap<String,Object>();
		}
	}
	
	public List<Map<String,Object>> queryList(Object obj,String sql,Object[] params){
		if(obj instanceof BatchSql){
			return db.queryForList(sql,params);
		} else if(obj instanceof Batch){
			try {
				return ((Batch)obj).queryForList(sql, params);
			} catch (Exception e) {
				e.printStackTrace();
				return new ArrayList<Map<String,Object>>();
			}
		} else {
			logger.error("对象传递错误，无法查询结果："+sql);
			return new ArrayList<Map<String,Object>>();
		}
	}
	
	/**
     * 獲取sql語句
     * @author 雷杨
     * @param key
     * @return
     */
    public String getSql(String key){
    	return SQLconfig.getSql(key);
    }
	
    public Map<String,Object> createFlow(User u,Batch db,TaskBean taskBean) throws Exception{
    	//检查各项参数是否正确
		if("".equals(taskBean.getTask_cfg_id())){
			return getReturnMap("-11","配置任务编号不能为空 ");
		}
		//检查task_cfg_id是否正确，是否存在
		Map<String,Object> map = queryMap(db, getSql("task.getTaskCfg"), new Object[]{taskBean.getTask_cfg_id()});
		if(map.isEmpty()){
			return getReturnMap("-21","未获取到任务对应的配置项");
		}
		
		TaskCfg taskcfg = (TaskCfg) JSONObject.toBean(JSONObject.fromObject(map), TaskCfg.class);
		if("0".equals(taskcfg.getState())){//任务已无效状态
			return getReturnMap("-31","任务已无效状态");
		}
		
		if("".equals(taskBean.getName())){
			taskBean.setName(taskcfg.getName());
		}
		
		if("".equals(taskBean.getCreate_oper())){
			return getReturnMap("-41","创建人工号不能为空");
		}
		
		if("".equals(taskBean.getPredict_complete_duration())){
			taskBean.setPredict_complete_duration(taskcfg.getPredict_complete_duration());
		}
		
		if("".equals(taskBean.getEarly_duration())){
			taskBean.setEarly_duration("0");
		}
		
		//进行插入流程主表操作
		int task_id = insert(db, getSql("task.insertTask"), new Object[]{taskBean.getTask_cfg_id(),taskBean.getName(),taskBean.getCreate_oper(),taskBean.getPredict_complete_duration(),
																			taskBean.getEarly_duration(),taskBean.getRemark(),taskBean.getFile_id(),taskBean.getSup_task_step_id(),taskBean.getResource_id()});
		if(task_id == -1 || task_id == -2){
			return getReturnMap("-51","插入主表任务失败");
		}
        String code=DateHelper.getToday("yyyyMMddHHmmss")+"T"+task_id+"C"+taskBean.getTask_cfg_id();
		//更新编号
		update(db, getSql("task.updateTaskCode"), new Object[]{code,task_id});
		
		//主线创建成功后进行创建步骤信息
		taskBean.setTask_id(task_id+"");
		//第一次创建直接取出第一条步骤记录
		Map<String,Object> stepMap = db.queryForMap(getSql("task.getStep").replace("##", "1"),new Object[]{taskBean.getTask_cfg_id()});
		if(stepMap.isEmpty()){//未查询到步骤配置信息
			return getReturnMap("-61","未查询到步骤配置信息");
		}
		
		//检查当前操作人是否有权限处理当前步骤信息
		String rule_id = str.get(stepMap, "rule_id");
		String org_id = str.get(stepMap, "org_id");
		String oper = str.get(stepMap, "oper");
		logger.debug("u.getId():"+u.getId()+"--"+oper);
		logger.debug("org_id:"+org_id);
		logger.debug("rule_id:"+rule_id);
		if(!u.getId().equals(oper)&&!"".equals(oper)){
			return getReturnMap("-72","没有权限处理当前步骤信息，或者没有权限发起当前流程");
		}
		if(!checkOrg(u, org_id)){
			return getReturnMap("-73","没有权限处理当前步骤信息，或者没有权限发起当前流程");
		}
		if(!checkRole(u, rule_id)){
			return getReturnMap("-71","没有权限处理当前步骤信息，或者没有权限发起当前流程");
		}
		
		//此处实际需要插入两步，第一步是发起人的操作步骤,发送第一步操作人处理结构
		TaskStepBean taskStepBean = new TaskStepBean();
		taskStepBean.setTask_id(task_id+"");
		taskStepBean.setOper_id(u.getId());
		taskStepBean.setStep_name(str.get(stepMap, "step_name"));
		taskStepBean.setIs_overtime("0");
		if(!"".equals(taskBean.getRemark())){
			taskStepBean.setRemark(taskBean.getRemark());
		}else{
			taskStepBean.setRemark("发起任务");
		}
		taskStepBean.setState("2");
		taskStepBean.setCfg_step_id(str.get(stepMap, "step_id"));
//    			taskStepBean.setNext_step_id(next_step_id);
		taskStepBean.setSup_step_id("-1");
		taskStepBean.setDispose_class(str.get(stepMap, "dispose_class"));
		
		String sqlOne = getSql("task.insertStep");
		int stepId = db.insert(sqlOne, new Object[]{task_id,taskStepBean.getStep_name(),taskStepBean.getSup_step_id(),taskStepBean.getCfg_step_id(),"","",taskStepBean.getOper_id()});
		if(stepId == -1){
			return getReturnMap(-71, "步骤创建失败");
		}
		//插入第二步信息
		Map<String,Object> stepTwoMap = db.queryForMap(getSql("task.getStep").replace("##", "2"),new Object[]{taskBean.getTask_cfg_id()});
		if(stepTwoMap.isEmpty()){//未查询到步骤配置信息
			return getReturnMap("-61","未查询到步骤配置信息");
		}
		 
		//检查当前操作人是否有权限处理当前步骤信息
		rule_id = str.get(stepTwoMap, "rule_id");
		org_id = str.get(stepTwoMap, "org_id");
		oper = str.get(stepTwoMap, "oper");
		
		if(!"".equals(taskBean.getNowOper())){
			oper = taskBean.getNowOper();
		}
		if(!"".equals(taskBean.getNowOrg())){
			org_id = taskBean.getNowOrg();
		}
		if(!"".equals(taskBean.getNowRole())){
			rule_id = taskBean.getNowRole();
		}
		TaskStepBean taskTwoStepBean = new TaskStepBean();
		taskTwoStepBean.setTask_id(task_id+"");
		taskTwoStepBean.setOper_id(u.getId());
		taskTwoStepBean.setStep_name(str.get(stepTwoMap, "step_name"));
		taskTwoStepBean.setIs_overtime("0");
		taskTwoStepBean.setRemark("");
		taskTwoStepBean.setState("0");
		taskTwoStepBean.setCfg_step_id(str.get(stepTwoMap, "step_id"));
//    			taskStepBean.setNext_step_id(next_step_id);
		taskTwoStepBean.setSup_step_id(stepId+"");
		taskTwoStepBean.setDispose_class(str.get(stepTwoMap, "dispose_class"));
		int stepTwoId = db.insert(sqlOne, new Object[]{task_id,taskTwoStepBean.getStep_name(),taskTwoStepBean.getSup_step_id(),taskTwoStepBean.getCfg_step_id(),
				org_id,rule_id,oper});
		if(stepTwoId == -1){
			return getReturnMap(-72, "步骤创建失败");
		}
		//更新当前第一步骤为已处理完成
		db.update(getSql("task.disposetTask.updateStep"),new Object[]{"",0,stepTwoId,u.getId(),"",2,stepId});
		
		//Map<String,Object> mapStep = disposeStep(request, db, taskStepBean);
		//String state = str.get(mapStep, "state");
		//if("1".equals(state)){
		//	mapStep.put("task_id", task_id);
		//	mapStep.put("msg", "操作成功");
		//}
		Map<String,Object> returnMap = getReturnMap(1, "操作成功");
		returnMap.put("task_id", task_id);
		returnMap.put("stepId", stepId);//第一步骤ID
		returnMap.put("code", code);//任务编号
		returnMap.put("stepTwoId", stepTwoId);//第二步骤ID
		return returnMap;
    }
    
	/**
	 * 发起任务信息
	 * @author 雷杨
	 * @param db 可传入bancth 或者 bancthsql
	 * @param params{
	 * 				task_cfg_id:总的任务id yc_task_cfg_tab中id，不为空
	 * 				name：当前任务名称， 可不传，如果不传默认取yc_task_cfg_tab中的名称
	 * 				create_oper：创建人工号，系统自动发起传入0 ，不为空
	 * 				predict_complete_duration：（天）预计任务完成时长，可作为后期任务超时对比，完成时长根据创建时间开始计算 ，不传入默认取yc_task_cfg_tab表中信息
	 * 				early_duration：当前任务超时提醒时间，剩余多久提醒 ,不传入或者为空默认就是0不提醒
	 * 				sup_task_step_id：上级任务步骤id，没有可不传入，当存在此项的时候此任务完成就会去同步更新处于挂起中的步骤  
	 * 				remark:当前任务备注信息  可不传入
	 * 				file_id：当前任务需要挂载的附件id,对应表：yc_flow_file中的file_id 可不传入，
	 * 
	 * 				nowRole：刚生成流程时 需要下一步处理的人 可不传入，
	 * 				nowOrg：刚生成流程时 需要下一步处理的人 可不传入，
	 * 				nowOper：刚生成流程时 需要下一步处理的人 可不传入，
	 * 			}
	 * @return 1 成功
	 * 			-11  task_cfg_id 不能为空
	 * 			-21 未获取到任务对应的配置项，请核对任务是否已配置生成 yc_task_cfg_tab
	 * 			-31  任务已无效状态
	 * 			-41 创建人工号不能为空
	 * 			-51 插入主表任务失败
	 * 			-61 未查询到步骤配置信息
	 * 			-71 没有权限处理当前步骤信息，或者没有权限发起当前流程
	 * 			-81 当前步骤创建失败 
	 * 			{
	 * 			state:
	 * 			msg:
	 * 			task_id:
	 * 		
	 * 			}
	 * @throws Exception 
	 */
	public Map<String,Object> createFlow(HttpServletRequest request,Batch db,TaskBean taskBean) throws Exception{
		User u = getUser(request);
		return createFlow(u, db, taskBean);
		
//		//检查各项参数是否正确
//		if("".equals(taskBean.getTask_cfg_id())){
//			return getReturnMap("-11","配置任务编号不能为空 ");
//		}
//		//检查task_cfg_id是否正确，是否存在
//		Map<String,Object> map = queryMap(db, getSql("task.getTaskCfg"), new Object[]{taskBean.getTask_cfg_id()});
//		if(map.isEmpty()){
//			return getReturnMap("-21","未获取到任务对应的配置项");
//		}
//		
//		TaskCfg taskcfg = (TaskCfg) JSONObject.toBean(JSONObject.fromObject(map), TaskCfg.class);
//		if("0".equals(taskcfg.getState())){//任务已无效状态
//			return getReturnMap("-31","任务已无效状态");
//		}
//		
//		if("".equals(taskBean.getName())){
//			taskBean.setName(taskcfg.getName());
//		}
//		
//		if("".equals(taskBean.getCreate_oper())){
//			return getReturnMap("-41","创建人工号不能为空");
//		}
//		
//		if("".equals(taskBean.getPredict_complete_duration())){
//			taskBean.setPredict_complete_duration(taskcfg.getPredict_complete_duration());
//		}
//		
//		if("".equals(taskBean.getEarly_duration())){
//			taskBean.setEarly_duration("0");
//		}
//		
//		//进行插入流程主表操作
//		int task_id = insert(db, getSql("task.insertTask"), new Object[]{taskBean.getTask_cfg_id(),taskBean.getName(),taskBean.getCreate_oper(),taskBean.getPredict_complete_duration(),
//																			taskBean.getEarly_duration(),taskBean.getRemark(),taskBean.getFile_id(),taskBean.getSup_task_step_id(),taskBean.getResource_id()});
//		if(task_id == -1 || task_id == -2){
//			return getReturnMap("-51","插入主表任务失败");
//		}
//        String code=DateHelper.getToday("yyyyMMddHHmmss")+"T"+task_id+"C"+taskBean.getTask_cfg_id();
//		//更新编号
//		update(db, getSql("task.updateTaskCode"), new Object[]{code,task_id});
//		
//		//主线创建成功后进行创建步骤信息
//		taskBean.setTask_id(task_id+"");
//		//第一次创建直接取出第一条步骤记录
//		Map<String,Object> stepMap = db.queryForMap(getSql("task.getStep").replace("##", "1"),new Object[]{taskBean.getTask_cfg_id()});
//		if(stepMap.isEmpty()){//未查询到步骤配置信息
//			return getReturnMap("-61","未查询到步骤配置信息");
//		}
//		
//		User u = getUser(request);
//		//检查当前操作人是否有权限处理当前步骤信息
//		String rule_id = str.get(stepMap, "rule_id");
//		String org_id = str.get(stepMap, "org_id");
//		String oper = str.get(stepMap, "oper");
//		logger.debug("u.getId():"+u.getId()+"--"+oper);
//		logger.debug("org_id:"+org_id);
//		logger.debug("rule_id:"+rule_id);
//		if(!u.getId().equals(oper)&&!"".equals(oper)){
//			return getReturnMap("-72","没有权限处理当前步骤信息，或者没有权限发起当前流程");
//		}
//		if(!checkOrg(request, org_id)){
//			return getReturnMap("-73","没有权限处理当前步骤信息，或者没有权限发起当前流程");
//		}
//		if(!checkRole(request, rule_id)){
//			return getReturnMap("-71","没有权限处理当前步骤信息，或者没有权限发起当前流程");
//		}
//		
//		//此处实际需要插入两步，第一步是发起人的操作步骤,发送第一步操作人处理结构
//		TaskStepBean taskStepBean = new TaskStepBean();
//		taskStepBean.setTask_id(task_id+"");
//		taskStepBean.setOper_id(u.getId());
//		taskStepBean.setStep_name(str.get(stepMap, "step_name"));
//		taskStepBean.setIs_overtime("0");
//		if(!"".equals(taskBean.getRemark())){
//			taskStepBean.setRemark(taskBean.getRemark());
//		}else{
//			taskStepBean.setRemark("发起任务");
//		}
//		taskStepBean.setState("2");
//		taskStepBean.setCfg_step_id(str.get(stepMap, "step_id"));
////		taskStepBean.setNext_step_id(next_step_id);
//		taskStepBean.setSup_step_id("-1");
//		taskStepBean.setDispose_class(str.get(stepMap, "dispose_class"));
//		
//		String sqlOne = getSql("task.insertStep");
//		int stepId = db.insert(sqlOne, new Object[]{task_id,taskStepBean.getStep_name(),taskStepBean.getSup_step_id(),taskStepBean.getCfg_step_id(),"","",taskStepBean.getOper_id()});
//		if(stepId == -1){
//			return getReturnMap(-71, "步骤创建失败");
//		}
//		//插入第二步信息
//		Map<String,Object> stepTwoMap = db.queryForMap(getSql("task.getStep").replace("##", "2"),new Object[]{taskBean.getTask_cfg_id()});
//		if(stepTwoMap.isEmpty()){//未查询到步骤配置信息
//			return getReturnMap("-61","未查询到步骤配置信息");
//		}
//		 
//		//检查当前操作人是否有权限处理当前步骤信息
//		rule_id = str.get(stepTwoMap, "rule_id");
//		org_id = str.get(stepTwoMap, "org_id");
//		oper = str.get(stepTwoMap, "oper");
//		
//		if(!"".equals(taskBean.getNowOper())){
//			oper = taskBean.getNowOper();
//		}
//		if(!"".equals(taskBean.getNowOrg())){
//			org_id = taskBean.getNowOrg();
//		}
//		if(!"".equals(taskBean.getNowRole())){
//			rule_id = taskBean.getNowRole();
//		}
//		TaskStepBean taskTwoStepBean = new TaskStepBean();
//		taskTwoStepBean.setTask_id(task_id+"");
//		taskTwoStepBean.setOper_id(u.getId());
//		taskTwoStepBean.setStep_name(str.get(stepTwoMap, "step_name"));
//		taskTwoStepBean.setIs_overtime("0");
//		taskTwoStepBean.setRemark("");
//		taskTwoStepBean.setState("0");
//		taskTwoStepBean.setCfg_step_id(str.get(stepTwoMap, "step_id"));
////		taskStepBean.setNext_step_id(next_step_id);
//		taskTwoStepBean.setSup_step_id(stepId+"");
//		taskTwoStepBean.setDispose_class(str.get(stepTwoMap, "dispose_class"));
//		int stepTwoId = db.insert(sqlOne, new Object[]{task_id,taskTwoStepBean.getStep_name(),taskTwoStepBean.getSup_step_id(),taskTwoStepBean.getCfg_step_id(),
//				org_id,rule_id,oper});
//		if(stepTwoId == -1){
//			return getReturnMap(-72, "步骤创建失败");
//		}
//		//更新当前第一步骤为已处理完成
//		db.update(getSql("task.disposetTask.updateStep"),new Object[]{"",0,stepTwoId,u.getId(),"",2,stepId});
//		
//		//Map<String,Object> mapStep = disposeStep(request, db, taskStepBean);
//		//String state = str.get(mapStep, "state");
//		//if("1".equals(state)){
//		//	mapStep.put("task_id", task_id);
//		//	mapStep.put("msg", "操作成功");
//		//}
//		Map<String,Object> returnMap = getReturnMap(1, "操作成功");
//		returnMap.put("task_id", task_id);
//		returnMap.put("stepId", stepId);//第一步骤ID
//		returnMap.put("code", code);//任务编号
//		returnMap.put("stepTwoId", stepTwoId);//第二步骤ID
//		return returnMap;
	}
	
	/**
	 * 发送处理步骤信息
	 * @author 雷杨
	 * @param request
	 * @param db
	 * @param taskStepBean
	 * @return >0 则为当前步骤的id主键值
	 * 			-111 当前步骤配置class的路径存在问题或不存在
	 * 			-121 步骤处理的class中的方法存在问题
	 * 			-131 步骤处理状态错误 
	 * 			-141 未或者到当前步骤id
	 * 			-151 taskid不存在
	 * 			-161 class返回错误类型
	 * 			-171 disposeClass接口定义错误
	 * @throws Exception 
	 */
	public Map<String,Object> disposeStep(HttpServletRequest request,Batch db,TaskStepBean taskStepBean) throws Exception{
		if("".equals(taskStepBean.getIs_overtime())){
			taskStepBean.setIs_overtime("0");
		}
		
		if("".equals(taskStepBean.getSup_step_id())){
			taskStepBean.setSup_step_id("0");
		}
		
		if("".equals(taskStepBean.getState())){
			return getReturnMap("-131","步骤处理状态错误 ");
		}
		
		if("".equals(taskStepBean.getStep_id())){
			return getReturnMap("-141","未或者到当前步骤id ");
		}
		if("".equals(taskStepBean.getTask_id())){
			return getReturnMap("-151","任务标识不存在 ");
		}
		
		if("".equals(taskStepBean.getDispose_class())){
			taskStepBean.setDispose_class("pccom.web.flow.interfaces.FlowStepBaseDispose");
		}
		
		//调用当前配置的类
		String disposeClass = taskStepBean.getDispose_class();
		if(!"".equals(disposeClass)){
			//进行反射调用
			try {
				Class cls = Class.forName(disposeClass);
				Object obj = cls.newInstance();
				if(obj instanceof FlowStepDisposeInterface){
					FlowStepDisposeInterface flow = (FlowStepDisposeInterface)obj;
					Object returnObj = flow.stepDispose(db,request,taskStepBean.getStep_id(),taskStepBean.getTask_id(),taskStepBean.getState(),taskStepBean.getCfg_step_id());
					if(returnObj instanceof Map){
						Map<String,Object> returnMap = (Map<String,Object>)returnObj;
						return returnMap;
					}else{
						return getReturnMap("-161","处理返回错误类型");
					}
				}else{
					return getReturnMap("-171","处理返回错误定义类型");
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				return getReturnMap("-111","处理类未找到");
			} catch (InstantiationException e) {
				e.printStackTrace();
				return getReturnMap("-111","处理类未找到");
			} catch (IllegalAccessException e) {
				e.printStackTrace();
				return getReturnMap("-111","处理类未找到");
			} catch(ExceptionInInitializerError e){
				e.printStackTrace();
				return getReturnMap("-111","处理类未找到");
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				return getReturnMap("-111","处理类未找到");
			} catch (TaskException e) {
				e.printStackTrace();
				return getReturnMap("-111",e.getMessage());
			} catch(Exception e){
				e.printStackTrace();
				return getReturnMap("-111","处理类未找到");
			}
		}else{
			return getReturnMap("1","处理成功");
		}
	}
	
	/**
	 * 创建步骤
	 * @author 雷杨
	 * @param db
	 * @param taskBean{
	 * 				task_id:必须传入
	 * 				
	 * 			}
	 * @return
	 */
	public int createStep(Batch db,TaskBean taskBean){
		
		return 1;
	}
	
	
	/**
	 * 结束当前步骤
	 * @author 雷杨
	 * @param db
	 * @return
	 */
	public int endStep(Batch db){                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 
		
		return 1;
	}
	
	
}
