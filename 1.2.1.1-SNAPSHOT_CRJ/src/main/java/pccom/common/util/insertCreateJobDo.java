package pccom.common.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.JobExecutionException;
import com.ycdc.core.plugin.sms.SmsSendContants;
import com.ycdc.core.plugin.sms.SmsSendMessage;
import pccom.web.beans.User;
import pccom.web.flow.base.FlowBase;
import pccom.web.flow.base.OrderService;
import pccom.web.flow.bean.TaskBean;
import pccom.web.flow.util.FlowTaskState;
import pccom.web.server.sys.account.AccountService;

/**
 * 催租任务生成定时器
 * 
 * */
public class insertCreateJobDo extends FlowBase {
	private int timeout;
	private static int i = 0;
	// 调度工厂实例化后，经过timeout时间开始执行调度

	public void setTimeout(int timeout) 
	{
		this.timeout = timeout;
	}
	
	protected Object executeInternal() throws  IOException,JobExecutionException
	{
		@SuppressWarnings("unchecked")
		Object i = db.doInTransaction(new Batch<Object>()
		{
			@Override
			public Integer execute() throws Exception 
			{   
		        List<Map<String,Object>> list= this.queryForList(getSql("pressRental.queryFinancial"));
		        if (list.size()>0) 
		        {
		        	list=list.subList(0, 1);
				}
		        for(Map<String, Object> mp : list)
		        {
		        	logger.debug("----------------------------------------------------------定时任务");
		           final String correspondent=StringHelper.get(mp, "id");
			       final String name="催租任务"+StringHelper.get(mp, "name");
			       final String service_time=StringHelper.get(mp, "start_time");
			       final String cost=StringHelper.get(mp, "cost");
			       final String agents=StringHelper.get(mp, "agents");
			       final String user_id=StringHelper.get(mp, "user_id");
			       final String user_mobile=StringHelper.get(mp, "user_mobile");
			       final String user_name=StringHelper.get(mp, "username");
			       
			       final Map<String, Object> aMap=this.queryForMap(getSql("pressRental.queryAccount"),new Object[]{agents});
			       
			       final String state=str.get(aMap, "state");//当前账号状态
			       
			       final String is_delete=str.get(aMap, "is_delete");//当前账号是否被删除
			       
			       Object o= new Object[]{OrderService.PRESS_RENTAL,name,OrderService.ACCEPT_WAITSTATE,user_id,
			    		   user_name,user_mobile,cost,"",service_time,0,0,correspondent,0};
			       //新增订单获取当前id
			       final  int keyId=createOrder(this,o);
		           if (keyId==-1)
		           {
		        	   this.rollBack();
		        	   return keyId;
				   }
		           //生成编码
		           StringBuffer sbCode = new StringBuffer();
		  		   sbCode.append("O").append(DateHelper.getToday("yyyyMMdd")).append("U").append(user_id).append("T").append(OrderService.PRESS_RENTAL).append(keyId);
		  		   //修改编码
		  		   int res=updateCode(this,sbCode.toString(),keyId);
		  		   if (res!=1)
		           {
		        	   this.rollBack();
		        	   return res;
				   }
		  		   //任务参数
		           TaskBean taskBean = new TaskBean();
		   		   taskBean.setCreate_oper("1");
		   		   taskBean.setTask_cfg_id("11");
		   		   taskBean.setName("催租订单_"+sbCode.toString());
		   		   taskBean.setResource_id(String.valueOf(keyId));
		   		   taskBean.setRemark("");
		   		   Map<String, Object> taskMap=new HashMap<String, Object>();
		   		   //判断当前员工是否离职
				   if ("1".equals(state)&&"1".equals(is_delete))
				   {
					   taskBean.setNowOper(agents);
					   taskMap=createTask(this,taskBean);
					   //发送短信
					   smsSend(name,sbCode.toString(),str.get(taskMap, "code"),str.get(aMap, "mobile"),"系统自动");
					   res= nextStep(taskMap,this,agents);//生成下一步.
					   if (res!=1)
					   {
						   this.rollBack();
			        	   return res;
					   }
				   }
				   else
				   {
					   taskBean.setNowRole(String.valueOf(AccountService.TOP_MANAGER));
					   taskMap=createTask(this,taskBean);
				   }
		        }
		        return 1;
			}
	    });
		return  getReturnMap(i);
	}
	//生成订单
	private int createOrder(Object bat,Object o)
	{
        String  sql=getSql("orderService.orderSave");			    
		return this.insert(bat, sql, (Object[]) o);
	}
	//修改订单编码
	private int updateCode(Object bat,String code, int keyId)
	{
		 String sql=getSql("orderService.updateCode");
		 return  this.update(bat,sql,new Object[]{code,keyId});
	}
	//开始任务
	private Map<String, Object> createTask(Batch bat,TaskBean taskBean) throws Exception
	{    
		User u = new User();
        u.setId("1");
        u.setOrgId("");
        u.setRoles("");
	    Map<String,Object> returnMap = createFlow(u, bat, taskBean);
	   return returnMap;
	}
	//执行下一步
	private int nextStep(Map<String,Object> returnMap,Batch bat,String agents)
	{
		int res=1;
		Map<String,Object> stepMap = queryMap(bat,getSql("task.getStep").replace("##", "3"),new Object[]{11});
	    String sqlOne = getSql("task.insertStep");
	    //生成新的步骤
	    final int stepId = insert(bat,sqlOne, new Object[]{str.get(returnMap, "task_id"),str.get(stepMap, "step_name"),str.get(returnMap, "stepTwoId"),str.get(stepMap, "step_id"),"","",agents});
		//更新上一步骤为已完成
	    res=update(bat,getSql("task.disposetTask.updateStep"),new Object[]{"",0,stepId,0,"",2,str.get(returnMap, "stepTwoId")});
		if (res!=1)
		{
			return res;
		}
	    //更新主表
	    res=update(bat,getSql("task.disposetTask.updateTask").replace("#endtime#","null"),new Object[]{0,FlowTaskState.START_TASK,0,stepId,str.get(stepMap, "step_name"),str.get(returnMap, "task_id")});
		return res;
	}
	//sms信息发送
	private  void smsSend(String name,String ocode,String code,String mobile,String opername)
	{
	   Map<String,String> msgMap = new HashMap<String, String>();
	   msgMap.put("typename", name);
	   msgMap.put("ocode", ocode);
	   msgMap.put("code", code);
	   msgMap.put("operName", opername);
	   SmsSendMessage.smsSendMessage(mobile, msgMap, SmsSendContants.TASK_WARN);
	}

}