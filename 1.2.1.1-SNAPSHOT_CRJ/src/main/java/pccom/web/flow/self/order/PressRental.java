package pccom.web.flow.self.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import com.ycdc.core.plugin.sms.SmsSendContants;
import com.ycdc.core.plugin.sms.SmsSendMessage;
import pccom.common.util.Batch;
import pccom.web.beans.SystemConfig;
import pccom.web.flow.interfaces.FlowStepBaseDispose;
import pccom.web.flow.util.FlowStepState;
import pccom.web.flow.util.FlowTaskState;
import pccom.web.server.sys.account.AccountService;
@Controller
public class PressRental  extends FlowStepBaseDispose
{
	@Override
	public Map<String, Object> stepDispose(Batch batch,HttpServletRequest request, String step_id, String task_id,String state, String cfg_step_id) throws Exception
	{
		Map<String,Object> returnMap = super.stepDispose(batch, request, step_id, task_id, state,cfg_step_id);
		final String oper = this.getUser(request).getId();// 获取操作人id
		final String operName = this.getUser(request).getName();//获取操作人名称
		final String task_cfg_id = req.getAjaxValue(request, "task_cfg_id");
		if("2".equals(cfg_step_id))
		{
			String account = req.getAjaxValue(request, "account");
			Map<String,Object> rodermap=smsSend(batch,task_id,account,operName);
			Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "3"),new Object[]{task_cfg_id});
			int stepId = batch.insert(getSql("task.insertStep"), new Object[]{task_id,str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"","",account});
			String sqlu =getSql("orderService.insertPush"); 
		    batch.update(sqlu, new Object[]{"催租任务_"+str.get(rodermap, "order_code"),"您有新的任务，请点击进入处理。任务编码："+str.get(rodermap, "code")+",订单编码："+str.get(rodermap, "order_code"),"任务处理","mobile/task/showDetailFlowDetail.do?step_id="+stepId+"&task_id="+task_id,str.get(rodermap, "mobile")});
		   
		    returnMap= stepDone(batch,returnMap,stepId,stepMap,task_id);
		}else if("3".equals(cfg_step_id))
		{   
			if (FlowStepState.END_STEP.equals(state))
		    {
			   //获取下一步配置信息
			   Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "4"),new Object[]{task_cfg_id});
			   //插入下一步                                                                                                                                                                                                                                                                                                                                                                                                       //指定到部门角色
			   int stepId = batch.insert(getSql("task.insertStep"), new Object[]{task_id,str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"",AccountService.TOP_MANAGER,""});
			   returnMap= stepDone(batch,returnMap,stepId,stepMap,task_id);
		    }
		    else if (FlowStepState.FOLLOW_UP.equals(state))
		    {
				//获取下一步配置信息
				Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "3"),new Object[]{task_cfg_id});
				int stepId = batch.insert(getSql("task.insertStep"), new Object[]{task_id,str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"","",oper});
				returnMap= stepDone(batch,returnMap,stepId,stepMap,task_id);
			}
		}
		else if("4".equals(cfg_step_id))
		{
			if (FlowStepState.END_STEP.equals(state))
		    {
			   //获取下一步配置信息
			   Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "5"),new Object[]{task_cfg_id});
			   //插入下一步                                                                                                                                                                                                                                                                                                                                                                                                       //指定到部门角色
			   int stepId = batch.insert(getSql("task.insertStep"), new Object[]{task_id,str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"",AccountService.CUSTOMER_MANAGER,""});
			   returnMap= stepDone(batch,returnMap,stepId,stepMap,task_id);
			   
			   
			   
			   
		    }
		    else if (FlowStepState.ROLLBACK_STEP.equals(state))
		    {
		    	String nowOper= getNowOper(batch, step_id, task_id);
				//获取下一步配置信息
				Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "3"),new Object[]{task_cfg_id});
				int stepId = batch.insert(getSql("task.insertStep"), new Object[]{task_id,str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"","",nowOper});
				returnMap= stepDone(batch,returnMap,stepId,stepMap,task_id);
			}
	    }
		return returnMap;
	}
	@Override
	public Map<String, Object> showStepDetail(Batch batch,HttpServletRequest request, String step_id, String task_id, boolean iscl, String cfg_step_id) throws Exception {
		Map<String,Object> returnMap = super.showStepDetail(batch, request, step_id, task_id,iscl,cfg_step_id);
		List<String> params = new ArrayList<String>();
		String	sql=getSql("task.getResource");
    	String order_Id=batch.queryForString(sql, new Object[]{task_id});
		//查询出当前订单信息
		 sql = getSql("orderService.query.main");
		 sql += getSql("orderService.query.id");
		//获取当前订单Id
		params.add(order_Id);
		returnMap.put("orderList", batch.queryForList(sql.replaceAll("###", SystemConfig.getValue("FTP_HTTP")+ SystemConfig.getValue("FTP_ORDER_HTTP_PATH")).replaceAll("#name#", "").replaceAll("step_name", ""),params.toArray()));
		//支出页面导出
		request.setAttribute("exportToExcel", req.getAjaxValue(request, "exportToExcel"));
		return returnMap;
	}
	@Override
	public Map<String, Object> deleteFlow(Batch batch, String task_id)throws Exception 
	{
		update(batch, "", null);
		batch.rollBack();
		return getReturnMap(0, "服务任务不可删除");
	}
	//更新主表
	private Map<String, Object> stepDone(Batch obj,Map<String, Object> returnMap,int stepId,Map<String, Object>  stepMap,String task_id) throws Exception
	{
		String sql = getSql("task.disposetTask.updateTask"); 
		obj.update(sql.replace("#endtime#","null"),new Object[]{0,FlowTaskState.START_TASK,0,stepId,str.get(stepMap, "step_name"),task_id});
		returnMap.put("step_id", stepId);
	    returnMap.put("step_name",str.get(stepMap, "step_name"));
		returnMap.put("state", 0);
		return returnMap;
	}
    //sms信息发送
	private Map<String, Object> smsSend(Batch obj,String task_id,String account,String operName )
	{
		final String ordersql = "select o.id,t.task_code,o.order_code from yc_task_info_tab t,yc_order_tab o where t.resource_id=o.id and t.task_id=?";
		Map<String, Object> rodermap = obj.queryForMap(ordersql,new Object[] {task_id});
	   //被指派人手机号
	    String mobile=obj.queryForString(getSql("account.getMobile"),new Object[]{account});
	    Map<String,String> msgMap = new HashMap<String, String>();
	    msgMap.put("typename", "催租任务");
	    msgMap.put("ocode", str.get(rodermap, "order_code"));
	    msgMap.put("code",  str.get(rodermap, "task_code"));
	    msgMap.put("operName",operName);
	    SmsSendMessage.smsSendMessage(mobile, msgMap, SmsSendContants.TASK_WARN);
	    return rodermap;
	}
	//获取当前操作人
	private String getNowOper(Batch obj,String step_id,String task_id)
	{
	   String  sql="select now_oper from yc_task_step_info_tab where step_id in( select sup_step_id from yc_task_step_info_tab where task_id=? and step_id=?)";
	   String nowOper= obj.queryForString(sql,new Object[] {task_id,step_id});
	   return nowOper;
	}
	
}
