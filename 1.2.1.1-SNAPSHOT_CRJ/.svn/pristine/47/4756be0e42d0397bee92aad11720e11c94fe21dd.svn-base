package pccom.web.flow.self.financial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.room1000.workorder.service.IWorkOrderService;

import pccom.common.util.Batch;
import pccom.common.util.SpringHelper;
import pccom.web.beans.User;
import pccom.web.flow.base.FlowBase;
import pccom.web.flow.base.OrderService;
import pccom.web.flow.base.TaskException;
import pccom.web.flow.interfaces.FlowStepBaseDispose;
import pccom.web.flow.interfaces.FlowStepDisposeInterface;
import pccom.web.flow.util.FlowStepState;
import pccom.web.flow.util.FlowTaskState;
import pccom.web.interfaces.Financial;
import pccom.web.interfaces.RankHouse;
import pccom.web.server.sys.account.AccountService;

/**
 * 收入处理流程
 * @author 雷杨
 *
 */
public class FinancialPayable extends FlowStepBaseDispose {
	@Override
	public Map<String,Object> stepDispose(Batch batch, HttpServletRequest request,
			String step_id, String task_id, String state,String cfg_step_id) throws Exception {
		logger.debug("????22");
		if("3".equals(cfg_step_id)&&FlowStepState.END_STEP.equals(state)){//处理结果确认的时候进行保存数据信息并完成 进行更新状态信息
			String payable_tab_id = req.getAjaxValue(request, "payable_tab_id");
			if(!"".equals(payable_tab_id)){
				batch.update(getSql("financial.taskpayable.updateCode").replace("##", payable_tab_id),new Object[]{task_id});
			}
			String[] payable_tab_ids = payable_tab_id.split(",");
			//更新实收项目金额
			batch.update(getSql("financial.taskpayable.updateState"),new Object[]{task_id});
			
			for(int i=0;i<payable_tab_ids.length;i++)
			{
			   if("".equals(payable_tab_ids[i]))
			   {
					continue;
			   }
				//获取下一步配置信息
			   Map<String,Object> payMap = batch.queryForMap(getSql("financial.payable.queryCategory"),new Object[]{payable_tab_ids[i]});
			   if ("0".equals(str.get(payMap, "status"))&&"2".equals(str.get(payMap, "secondary_type")))
			   {
				    // 获取操作人
			        String oper = this.getUser(request).getId();
					try {
						IWorkOrderService workOrderService = (IWorkOrderService) SpringHelper.getBean("WorkOrderService");
						// 金额转换
						Long money = new Double(Double.valueOf(str.get(payMap,"cost")) * 100).longValue();
						workOrderService.payWithTrans(Long.valueOf(str.get(payMap, "secondary")),Long.valueOf(oper), money);
					} catch (Exception e) {
						batch.rollBack();
					}
					
			       /*
				   //任务流程
				   String sql= getSql("task.getMapForOrder");
				   Map<String,Object> taskMap = batch.queryForMap(sql,new Object[]{str.get(payMap, "secondary")});
				   if (taskMap==null||taskMap.size()==0) 
				   {
						batch.update(getSql("orderService.updateinfos").replace("###", "order_status=?"), new Object[]{"4",str.get(payMap, "secondary")});
				   }
				   else
				   {
				   if ("待支付".equals(str.get(taskMap, "step_name")))
				    {
					    //查询当前合约的{父级合约id，id，租赁房源id，}
						sql=getSql("task.getHouseForOrder");
						Map<String,Object> rankMap = batch.queryForMap(sql,new Object[]{str.get(payMap, "secondary")});
						User u = getUser(request);
						String typeCfg="";
						String stepCfg="";
						switch (str.get(rankMap, "order_type")) {
						case "1":
							typeCfg="6";//配置类型
							stepCfg="3";//配置步骤
							//获取下一步配置信息
							Map<String,Object> stepMap1 = batch.queryForMap(getSql("task.getStep").replace("##", stepCfg),new Object[]{typeCfg});
							sql = getSql("task.disposetTask.updateTask"); 
							int stepId1 = batch.insert(getSql("task.insertStep"), new Object[]{str.get(taskMap, "task_id"),str.get(stepMap1, "step_name"),step_id,str.get(stepMap1, "step_id"),"",AccountService.CUSTOMER_MANAGER,""});
							sql = getSql("task.disposetTask.updateTask"); 
							//更新主表
							batch.update(sql.replace("#endtime#","null"),new Object[]{0,FlowTaskState.START_TASK,0,stepId1,str.get(stepMap1, "step_name"),str.get(taskMap, "task_id")});
							//更新当前步骤处理信息
							sql = getSql("task.disposetTask.updateStep");
							batch.update(sql,new Object[]{"",0,stepId1,u.getId(),"","2",str.get(taskMap, "step_id")});
							//修改订单状态进入执行中
					    	sql=getSql("orderService.updateinfos").replace("###", "order_status=?"); 
							batch.update(sql, new Object[]{"6",str.get(payMap, "secondary")});
							break;
						case "2":
							typeCfg="7";
							stepCfg="3";
							  //获取下一步配置信息
							Map<String,Object> stepMap2 = batch.queryForMap(getSql("task.getStep").replace("##", stepCfg),new Object[]{typeCfg});
							sql = getSql("task.disposetTask.updateTask"); 
							int stepId2 = batch.insert(getSql("task.insertStep"), new Object[]{str.get(taskMap, "task_id"),str.get(stepMap2, "step_name"),step_id,str.get(stepMap2, "step_id"),"",AccountService.CUSTOMER_MANAGER,""});
							sql = getSql("task.disposetTask.updateTask"); 
							//更新主表
							batch.update(sql.replace("#endtime#","null"),new Object[]{0,FlowTaskState.START_TASK,0,stepId2,str.get(stepMap2, "step_name"),str.get(taskMap, "task_id")});
							//更新当前步骤处理信息
							sql = getSql("task.disposetTask.updateStep");
							batch.update(sql,new Object[]{"",0,stepId2,u.getId(),"","2",str.get(taskMap, "step_id")});
							
							//修改订单状态进入执行中
					    	sql=getSql("orderService.updateinfos").replace("###", "order_status=?"); 
							batch.update(sql, new Object[]{"6",str.get(payMap, "secondary")});
							break;
						case "7":
							typeCfg="10";
							stepCfg="9";
							//查询基础房源id
							sql=getSql("task.getHouseForString");
							String house_id=batch.queryForString(sql,new Object[]{str.get(rankMap, "father_id")});
							//插入历史表
							batch.insertTableLog(request, "yc_agreement_tab", " and id='"+str.get(rankMap, "id")+"'", "退租支付后将租赁合约置为无效", new ArrayList<String>(), getUser(request).getId());
							//获取订单信息							
							String ordersql = getSql("orderService.getorder");
							Map<String, Object> rodermap = batch.queryForMap(ordersql,new Object[] {str.get(payMap, "secondary")});
							
							// 更新租赁合约状态
							sql = getSql("basehouse.rankHosue.updateRankAgreementStatus").replace("####", " status = ?,checkouttime=? ");
							batch.update(sql, new Object[]{"3",str.get(rodermap, "service_time"),str.get(rankMap, "id")});
							
							Financial fin=new Financial();
							Map<String,String> param = new HashMap<String, String>();
							param.put("agreement_id", str.get(rankMap, "id"));
							//调用租赁房源审批失败接口
						    int  res=fin.repealRentHouse(param, batch);	
							if (res!=1)
							{
								throw new TaskException("撤销财务失败");
							}
							//获取下一步配置信息
							Map<String,Object> stepMap7 = batch.queryForMap(getSql("task.getStep").replace("##", stepCfg),new Object[]{typeCfg});
							sql = getSql("task.disposetTask.updateTask"); 
							//int stepId7 = batch.insert(getSql("task.insertStep"), new Object[]{str.get(taskMap, "task_id"),str.get(stepMap7, "step_name"),step_id,str.get(stepMap7, "step_id"),"",AccountService.CUSTOMER_MANAGER,""});
							//更新主表
							batch.update(sql.replace("#endtime#","null"),new Object[]{0,FlowTaskState.OVER_TASK,0,0,str.get(stepMap7, "step_name"),str.get(taskMap, "task_id")});
							//更新当前步骤处理信息
							
							sql = getSql("task.disposetTask.updateStep");
							batch.update(sql,new Object[]{"",0,0,u.getId(),"","2",str.get(taskMap, "step_id")});
							//batch.update(sql,new Object[]{"",0,str.get(stepMap7, "step_id"),u.getId(),"","2",stepId7});
							//修改订单状态进入已完成
					    	sql=getSql("orderService.updateinfos").replace("###", "order_status=?"); 
							batch.update(sql, new Object[]{OrderService.DONE_STATE,str.get(payMap, "secondary")});
							break;
						}
				    }
				   }
					//修改订单状态进入待支付
			    	sql=getSql("orderService.updateinfos").replace("###", "order_status=?, action_time=now()"); 
					batch.update(sql, new Object[]{"200",str.get(payMap, "secondary_type")});
			   */}
			}
			//更新对应的财务表数据信息
			batch.update(getSql("financial.taskpayable.updatePayState"),new Object[]{task_id});
			for(int i=0;i<payable_tab_ids.length;i++){
				if("".equals(payable_tab_ids[i])){
					continue;
				}
				//同时更新对应状态信息
				batch.update(getSql("financial.taskpayable.updateProCost"),new Object[]{payable_tab_ids[i]});
			}
			
		}else if("3".equals(cfg_step_id)&&FlowStepState.ROLLBACK_STEP.equals(state)){//第三步并且 为不通过状态 只更新流程结束信息
			batch.update(getSql("financial.taskpayable.updateState"),new Object[]{task_id});
		}else if("2".equals(cfg_step_id)&&FlowStepState.ROLLBACK_STEP.equals(state)){//第二步并且 为不通过状态 只更新流程结束信息
			batch.update(getSql("financial.taskpayable.updateState"),new Object[]{task_id});
		}
		return super.stepDispose(batch, request, step_id, task_id, state,cfg_step_id);
	}
	
	@Override
	public Map<String, Object> showStepDetail(Batch batch,
			HttpServletRequest request, String step_id, String task_id,boolean iscl,String cfg_step_id) throws Exception {
		Map<String,Object> returnMap = super.showStepDetail(batch, request, step_id, task_id,iscl,cfg_step_id);
		List<String> params = new ArrayList<String>();
		//查询出当前订单信息
		String sql = getSql("financial.taskpayable.main");
		int secondary_type=batch.queryForInt(getSql("financial.taskpayable.checkinfo"),new Object[]{task_id});
		if (secondary_type==2)
		{
			 sql = getSql("financial.taskpayable.ordermain");
		}
		/*sql += getSql("financial.taskpayable.task");*/
		params.add(task_id);
		returnMap.put("payablelist", batch.queryForList(sql,params.toArray()));
		//支出页面导出
		request.setAttribute("exportToExcel", req.getAjaxValue(request, "exportToExcel"));
		return returnMap;
	}
	
	@Override
	public Map<String, Object> deleteFlow(Batch batch, String task_id) throws Exception {
		Map<String,Object> returnMap = super.deleteFlow(batch, task_id);
		
		String sql = getSql("financial.taskpayable.deleteFlow");
		batch.update(sql,new Object[]{task_id});
		
		return returnMap;
	}
	
}
