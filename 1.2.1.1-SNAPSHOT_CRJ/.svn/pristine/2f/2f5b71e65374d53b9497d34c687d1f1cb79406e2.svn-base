package pccom.web.interfaces;

import java.text.ParseException;
import java.util.Map;

import org.springframework.stereotype.Service;

import pccom.common.util.Batch;
import pccom.web.beans.User;
import pccom.web.flow.base.OrderService;
import pccom.web.flow.bean.TaskBean;
import pccom.web.flow.util.FlowTaskState;

import com.room1000.core.utils.DateUtil;


/**
 * 服务
 * @author 刘飞
 *
 */
@Service("orderInterfaces")
public class OrderInterfaces extends Base
{
	/**
	 * 退租订单接口
	 * @author 刘飞
	 * @return 
	 * 		   -4当前房源已申请退租，请勿重复操作
	 *         -2 新增失败内部错误，请联系管理员
	 *         1  成功
	 * @throws ParseException 
	 */
	public int orderCreate(Map params, Object obj) throws ParseException
	{
		String correspondent =str.get(params,"correspondent"); //租赁ID
		String oserviceTime =str.get(params,"oserviceTime"); //预约时间
		String odesc =""; //订单说明
		String childtype =str.get(params,"childtype");//子类型 0常规退租，1违约退租
		Map<String,Object> rankmap=queryMap(obj, getSql("orderService.queryagree"), new Object [] {correspondent});
        String orderName ="退租订单"+ str.get(rankmap,"name");//订单名称   ：退租订单+合约名称
    	String ouserMobile =str.get(rankmap,"user_mobile");; //用户手机
		String ouserName =str.get(rankmap,"username");//用户名
		String userId =str.get(rankmap,"uid");//用户名       	
		
		int rest= queryForInt(obj, getSql("orderService.checkIsDo"), new Object [] {correspondent,7});
		if (rest>0) 
		{
			return -4;
		}	 
		rest=queryForInt(obj, getSql("orderService.checkIsDo2"), new Object [] {correspondent,7});
		if (rest>0) 
		{
			return -4;
		}
	    String sql=getSql("orderService.orderSave");
	     //新增订单获取id
	    int keyId =insert(obj, sql, new Object [] {7, orderName,OrderService.GOTO_WAITSTATE, userId, ouserName, ouserMobile,0,odesc, oserviceTime, 0,childtype,correspondent,0}); 
	    if (keyId==-2)
	    {
	    	return -2;
		}
	    StringBuffer sbCode = new StringBuffer();
	    sbCode.append("O").append(DateUtil.getTodayTimeString(DateUtil.YYYYMMDD)).append("U").append(userId).append("T").append(7).append(keyId);
	    //修改订单编码
	    sql=getSql("orderService.updateCode");
	    update(obj,sql,new Object[]{sbCode,keyId});
	    return 1;
	    
		}
	/**
	 * 退租订单接口
	 * @author 刘飞
	 * @return 
	 * 		   -4当前房源已申请退租，请勿重复操作
	 *         -2 新增失败内部错误，请联系管理员
	 *         1  成功
	 * @throws Exception 
	 */
	public int appCreate(Map params, Object obj) throws Exception
	{
		String correspondent =str.get(params,"correspondent"); //租赁ID
		String oserviceTime =str.get(params,"oserviceTime"); //预约时间
		String odesc =str.get(params,"odesc"); //订单说明
		String childtype =str.get(params,"childtype");//子类型 0常规退租，1违约退租
		String oper =str.get(params,"oper");//操作人				       
		Map<String,Object> rankmap=queryMap(obj, getSql("orderService.queryagree"), new Object [] {correspondent});
        String orderName ="退租订单"+ str.get(rankmap,"name");//订单名称   ：退租订单+合约名称
    	String ouserMobile =str.get(rankmap,"user_mobile");; //用户手机
		String ouserName =str.get(rankmap,"username");//用户名
		String userId =str.get(rankmap,"uid");//用户名       	
		//验证是否已经生成任务订单---start
		int rest= queryForInt(obj, getSql("orderService.checkIsDo"), new Object [] {correspondent,7});
		if (rest>0) 
		{
			return -4;
		}	 
		rest=queryForInt(obj, getSql("orderService.checkIsDo2"), new Object [] {correspondent,7});
		if (rest>0) 
		{
			return -4;
		}
		//-------end
		//新增订单 ---start
	    String sql=getSql("orderService.orderSave");
	    int keyId =insert(obj, sql, new Object [] {7, orderName,OrderService.ACCEPT_WAITSTATE, userId, ouserName, ouserMobile,0,odesc, oserviceTime, oper,childtype,correspondent,0}); 
	    if (keyId==-2)
	    {
	    	return -2;
		}
	    //--------end 
	    //生成订单编码修改当前编码------start
	    StringBuffer sbCode = new StringBuffer();
	    sbCode.append("O").append(DateUtil.getTodayTimeString(DateUtil.YYYYMMDD)).append("U").append(userId).append("T").append(7).append(keyId);
	    //修改订单编码
	    sql=getSql("orderService.updateCode");
	    update(obj,sql,new Object[]{sbCode,keyId});
	    //--------end 
	    //先发起流程信息-------start
		TaskBean taskBean = new TaskBean();
		taskBean.setCreate_oper(oper);
		taskBean.setTask_cfg_id("10");
		taskBean.setName("退租订单_"+sbCode.toString());
		taskBean.setResource_id(String.valueOf(keyId));
		taskBean.setNowOper(oper);
		taskBean.setRemark(odesc);
		User u = new User();
		u.setId(oper);
		u.setOrgId("");
		u.setRoles("");
		Map<String,Object> returnMap = createFlow(u, (Batch)obj, taskBean);
		//+---------end
		//查询配置信息
		Map<String,Object> stepMap = queryMap(obj,getSql("task.getStep").replace("##", "3"),new Object[]{10});
		String sqlOne = getSql("task.insertStep");
		//生成新的步骤
		int stepId = insert(obj,sqlOne, new Object[]{str.get(returnMap, "task_id"),str.get(stepMap, "step_name"),str.get(returnMap, "stepTwoId"),str.get(stepMap, "step_id"),"","",oper});
		//更新上一步骤为已完成
		update(obj,getSql("task.disposetTask.updateStep"),new Object[]{"",0,stepId,oper,"",2,str.get(returnMap, "stepTwoId")});
		//更新当前任务
		//更新主表
		update(obj,getSql("task.disposetTask.updateTask").replace("#endtime#","null"),new Object[]{0,FlowTaskState.START_TASK,0,stepId,str.get(stepMap, "step_name"),str.get(returnMap, "task_id")});
	    return 1;
		}
}
