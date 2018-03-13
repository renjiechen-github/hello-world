package pccom.web.flow.self.order;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;

import com.ycdc.core.plugin.sms.SmsSendContants;
import com.ycdc.core.plugin.sms.SmsSendMessage;
import pccom.common.util.Batch;
import pccom.common.util.DateHelper;
import pccom.common.util.FtpUtil;
import pccom.web.beans.SystemConfig;
import pccom.web.flow.base.OrderService;
import pccom.web.flow.base.TaskException;
import pccom.web.flow.interfaces.FlowStepBaseDispose;
import pccom.web.flow.util.FlowStepState;
import pccom.web.flow.util.FlowTaskState;
import pccom.web.interfaces.Financial;
import pccom.web.interfaces.RankHouse;
import pccom.web.server.sys.account.AccountService;

@Controller
public class LeaseOrder  extends FlowStepBaseDispose{
	@Override
	public Map<String, Object> stepDispose(Batch batch,HttpServletRequest request, String step_id, String task_id,String state, String cfg_step_id) throws Exception {
		Map<String,Object> returnMap = super.stepDispose(batch, request, step_id, task_id, state,cfg_step_id);
		String sql = "";
		String oper = this.getUser(request).getId();// 获取操作人
		String task_cfg_id = req.getAjaxValue(request, "task_cfg_id");
		//获取当前订单Id
		sql=getSql("task.getResource");
    	int order_Id=batch.queryForInt(sql, new Object[]{task_id});
		if("2".equals(cfg_step_id))
		{
			 if(FlowStepState.END_STEP.equals(state))
		     {  //接单
				//获取下一步配置信息
				Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "3"),new Object[]{task_cfg_id});
				//插入下一步
				int stepId = batch.insert(getSql("task.insertStep"), new Object[]{task_id,str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"","",oper});
				returnMap= stepDone(batch,returnMap,stepId,stepMap,task_id,0);
			 }
			else
			{
				String account = req.getAjaxValue(request, "account");
				// 获取操作人
				final String operName = this.getUser(request).getName();
				//获取订单信息
				String ordersql = getSql("orderService.getorder");
				Map<String, Object> rodermap = batch.queryForMap(ordersql,new Object[] {order_Id});
				//获取任务编号
				ordersql=getSql("task.getTaskCode");
				String code=batch.queryForString(ordersql,new Object[]{task_id});
				//被指派人手机号
				ordersql=getSql("account.getMobile");
				String mobile=batch.queryForString(ordersql,new Object[]{account});
				//封装任务map
				Map<String,String> msgMap = new HashMap<String, String>();
				msgMap.put("typename", "看房预约");
				msgMap.put("ocode", str.get(rodermap, "order_code"));
				msgMap.put("code", code);
				msgMap.put("operName", operName);
		        SmsSendMessage.smsSendMessage(mobile, msgMap, SmsSendContants.TASK_WARN);
				//自己处理的业务逻辑
				//获取下一步配置信息
				Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "2"),new Object[]{task_cfg_id});
				//插入下一步
				int stepId = batch.insert(getSql("task.insertStep"), new Object[]{task_id,str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"","",account});
				//手机推送
				String sqlu =getSql("orderService.insertPush"); 
			    batch.update(sqlu, new Object[]{"退租任务_"+str.get(rodermap, "order_code"),"您有新的任务，请点击进入处理。任务编码："+code+",订单编码："+str.get(rodermap, "order_code"),"任务处理","mobile/task/showDetailFlowDetail.do?step_id="+stepId+"&task_id="+task_id,mobile});
			    returnMap= stepDone(batch,returnMap,stepId,stepMap,task_id,0);
			}
		}
		else if("3".equals(cfg_step_id))
		{       
	        int res=this.insertCheckHouse(batch, request, task_id);
	        if (res!=1)
	        {
	        	throw new TaskException("信息录入失败");
		    }
			//获取下一步配置信息
			Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "5"),new Object[]{task_cfg_id});
			//插入下一步                                                                                                                                                                                                                                                                                                                                                                                                   
			int stepId = batch.insert(getSql("task.insertStep"), new Object[]{task_id,str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"","","96"});
			returnMap= stepDone(batch,returnMap,stepId,stepMap,task_id,0);
		}
		else if("4".equals(cfg_step_id))
		{
			//获取下一步配置信息
			Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "5"),new Object[]{task_cfg_id});
			//插入下一步                                                                                                                                                                                                                                                                                                                                                                                                       //指定到部门角色
			int stepId = batch.insert(getSql("task.insertStep"), new Object[]{task_id,str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"","","96"});
			sql = getSql("task.disposetTask.updateTask"); 
			returnMap= stepDone(batch,returnMap,stepId,stepMap,task_id,0);
	   	}
		else if("5".equals(cfg_step_id))
		{
			if(FlowStepState.END_STEP.equals(state))
			{	
				String ocost = req.getAjaxValue(request, "order_cost");
				//修改订单金额
		    	sql=getSql("orderService.updateinfos").replace("###", " order_cost=? "); 
				batch.update(sql, new Object[]{ocost,order_Id});
				//获取下一步配置信息
				Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "6"),new Object[]{task_cfg_id});
				//插入下一步                                                                                                                                                                                                                                                                                                                                                                                                       //指定到部门角色
				int stepId = batch.insert(getSql("task.insertStep"), new Object[]{task_id,str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"",AccountService.TOP_MANAGER,""});
				//更新主表
				returnMap= stepDone(batch,returnMap,stepId,stepMap,task_id,0);
			}
			else
			{
				String nowOper= getNowOper(batch, step_id, task_id);
				//获取下一步配置信息
				Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "3"),new Object[]{task_cfg_id});
				//插入下一步                                                                                                                                                                                                                                                                                                                                                                                                       //指定到部门角色
				int stepId = batch.insert(getSql("task.insertStep"), new Object[]{task_id,str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"","",nowOper});
				sql = getSql("task.disposetTask.updateTask"); 
				//更新主表
				returnMap= stepDone(batch,returnMap,stepId,stepMap,task_id,0);
			}
	   	}
		else if("6".equals(cfg_step_id))
		{
			if(FlowStepState.END_STEP.equals(state))
			{	
				//获取订单信息
				String ordersql = getSql("orderService.getorder");
				Map<String, Object> rodermap = batch.queryForMap(ordersql,new Object[] {order_Id});
				//释放房源
				repleaHouse( batch , oper , order_Id , task_id);
				//修改合约进入失效待清算
		    	sql = getSql("basehouse.rankHosue.updateRankAgreementStatus").replace("####", " status = ? , checkouttime=? ");
				batch.update(sql, new Object[]{"7" , str.get(rodermap, "service_time") , str.get(rodermap, "correspondent")});
				//获取下一步配置信息
				Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "7"),new Object[]{task_cfg_id});
				//插入下一步                                                                                                                                                                                                                                                                                                                                                                                                       //指定到部门角色
				int stepId = batch.insert(getSql("task.insertStep"), new Object[]{task_id,str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"",AccountService.PLATFORM,""});
				//更新主表
				returnMap= stepDone(batch,returnMap,stepId,stepMap,task_id,0);
			}
			else
			{
			 	sql=getSql("orderService.updateinfos").replace("###", " order_cost=? "); 
				batch.update(sql, new Object[]{"0.0",order_Id});
				//获取下一步配置信息
				Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "5"),new Object[]{task_cfg_id});
				//插入下一步                                                                                                                                                                                                                                                                                                                                                                                                       //指定到部门角色
				int stepId = batch.insert(getSql("task.insertStep"), new Object[]{task_id,str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"","","96"});
				sql = getSql("task.disposetTask.updateTask"); 
				//更新主表
				returnMap= stepDone(batch,returnMap,stepId,stepMap,task_id,0);
			}
	   	}
		else if("7".equals(cfg_step_id))
		{
			 if(FlowStepState.END_STEP.equals(state))
			 {	
					Financial financial = new Financial();
					//获取订单信息
					String ordersql = getSql("orderService.getorder");
					Map<String, Object> rodermap = batch.queryForMap(ordersql,new Object[] {order_Id});
					String ocost=str.get(rodermap, "order_cost");
					ordersql=getSql("certificateLeaveService.main")+ getSql("certificateLeaveService.orderId");
				    Map<String ,String> mapj=judgement(str.get(rodermap, "correspondent"),batch);
				    if (!"1".equals(str.get(mapj,"status")))
				    {
			    		throw new TaskException("财务未找到");
					}
					Map<String,String> pramas =new HashMap<String,String>();
					pramas.put("agreement_id", str.get(rodermap, "correspondent"));
					pramas.put("operid", oper);
					pramas.put("settlements_name",str.get(mapj, "settlements_name"));//房间名称
					pramas.put("settlements_id",str.get(mapj, "settlements_id"));//项目Id
					List <Map<String, String>> leaveMap = batch.queryForList(ordersql,new Object[] {order_Id});//查询所有代缴费信息
					for(Map<String, String> mp : leaveMap)
			        {
						String type=returnType(str.get(mp, "type"));//代缴费对应财务数据类型
						if (type!=null)
						{
							int res=1;
							if (Float.parseFloat(str.get(mp, "cost"))>0) //价格大于0，表示收入
							{
							     res=insertSel(pramas,mp,-1,type,batch);//插入收入数据
							}
							else if (Float.parseFloat(str.get(mp, "cost"))<0) //价格小于0，表示支出
							{
							     res=insertSel(pramas,mp,1,type,batch);//插入支出数据
							} 
							if (res!=1) 
							{
								throw new TaskException("财务未找到");
							}
						}
			        }
					if (Float.parseFloat(ocost)>0) 
					{
						Map<String,String> orderMap =new HashMap<String,String>();
						orderMap.put("agreement_id", str.get(rodermap, "correspondent"));
						orderMap.put("cost", ocost);
						orderMap.put("operid", oper);
						orderMap.put("order_id",String.valueOf(order_Id));
				        int resf=financial.orderRent(orderMap, batch);//插入财务数据
				    	if (resf!=1) 
				    	{
				    		throw new TaskException("财务未找到");
						}
				    	//修改订单状态进入待支付
				    	sql=getSql("orderService.updateinfos").replace("###", "order_status=?,order_cost=? "); 
				    	batch.update(sql, new Object[]{"2",ocost,order_Id});
						//获取下一步配置信息
						Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "8"),new Object[]{task_cfg_id});
						int thirdStep = batch.insert(getSql("task.insertStep"), new Object[]{task_id, str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"",AccountService.PLATFORM,""});
						//更新当前步骤处理信息
						sql = getSql("task.disposetTask.updateStep");
						batch.update(sql,new Object[]{"",0,thirdStep,oper,"",FlowTaskState.BREAK_TASK,step_id});
						//更新主表
						returnMap= stepDone(batch,returnMap,thirdStep,stepMap,task_id,0);
					}else if(Float.parseFloat(ocost)<=0)
					{
						if (Float.parseFloat(ocost)<0)
						{
							Map<String,String> orderMap =new HashMap<String,String>();
							orderMap.put("agreement_id", str.get(rodermap, "correspondent"));
							orderMap.put("cost", ocost.substring(1,ocost.length()));
							orderMap.put("operid", oper);
							orderMap.put("order_id",String.valueOf(order_Id));
					        int resf=financial.orderPay(orderMap, batch);//插入财务数据
					    	if (resf!=1) 
					    	{
					    		throw new TaskException("财务未找到");
							}
					    	//修改订单状态进入待支付
					    	sql=getSql("orderService.updateinfos").replace("###", "order_status=?, order_cost=? "); 
							batch.update(sql, new Object[]{"3",ocost,order_Id});
							//获取下一步配置信息
							Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "8"),new Object[]{task_cfg_id});
							int thirdStep = batch.insert(getSql("task.insertStep"), new Object[]{task_id, str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"",AccountService.PLATFORM,""});
							//更新当前步骤处理信息
							sql = getSql("task.disposetTask.updateStep");
							batch.update(sql,new Object[]{"",0,thirdStep,oper,"",FlowTaskState.BREAK_TASK,step_id});
							//更新主表
							returnMap= stepDone(batch,returnMap,thirdStep,stepMap,task_id,0);
						}else{
							sql=getSql("task.getHouseForOrder");
							Map<String,Object> rankMap = batch.queryForMap(sql,new Object[]{order_Id});
							sql=getSql("task.getHouseForString");
							String house_id=batch.queryForString(sql,new Object[]{str.get(rankMap, "father_id")});
							Financial fin=new Financial();
							Map<String,String> param = new HashMap<String, String>();
							param.put("agreement_id", str.get(rankMap, "id"));
							//调用租赁房源审批失败接口
							int res=fin.repealRentHouse(param, batch);	
							if (res!=1)
							{
								throw new TaskException("撤销财务失败");
							}
							//调用接口参数
							Map<String,Object> params = new HashMap<String, Object>();
							params.put("houseid",house_id );//基础房源id
							params.put("oper",oper);//操作人
							params.put("rankid",str.get(rankMap, "house_id"));//租赁id
							//插入历史表
							batch.insertTableLog(request, "yc_agreement_tab", " and id='"+str.get(rankMap, "id")+"'", "退租时无需支付置为失效", new ArrayList<String>(), getUser(request).getId());
							// 更新租赁合约状态
							sql = getSql("basehouse.rankHosue.updateRankAgreementStatus").replace("####", " status = ?,checkouttime=? ");
							batch.update(sql, new Object[]{"3",str.get(rodermap, "service_time"),str.get(rankMap, "id")});
							//自己处理的业务逻辑
							sql=getSql("orderService.updateinfos").replace("###", " order_status =? "); 
						    batch.update(sql, new Object[]{OrderService.DONE_STATE,order_Id});
							//获取下一步配置信息
							Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "9"),new Object[]{task_cfg_id});
							returnMap= stepClose(batch,returnMap,0,stepMap,task_id,0);
						}
					}
			}
			else
			{
				 	sql=getSql("orderService.updateinfos").replace("###", " order_cost=? "); 
					batch.update(sql, new Object[]{"0.0",order_Id});
					//获取下一步配置信息
					Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "5"),new Object[]{task_cfg_id});
					//插入下一步                                                                                                                                                                                                                                                                                                                                                                                                       //指定到部门角色
					int stepId = batch.insert(getSql("task.insertStep"), new Object[]{task_id,str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"","","96"});
					sql = getSql("task.disposetTask.updateTask"); 
					//更新主表
					returnMap= stepDone(batch,returnMap,stepId,stepMap,task_id,0);
			}
	   	}
		else if("8".equals(cfg_step_id))
		{
			 Financial financial = new Financial();
			 if(FlowStepState.END_STEP.equals(state))
			 { 
				String cost=batch.queryForString(getSql("orderService.queryocost"),new Object[]{order_Id});
				Map<String,String> orderMap =new HashMap<String,String>();
				orderMap.put("order_id",String.valueOf(order_Id));
				//cost
				int resf=0;
				if (Float.parseFloat(cost)>0) 
				{
				    resf=financial.repealOrder(orderMap, batch);
				}
				else
				{
					resf=financial.repealPay(orderMap, batch);
				}
			    if (resf!=1) 
			    {
			    	throw new TaskException("财务任务进行中无法删除");
			    }
				//自己处理的业务逻辑
				sql=getSql("orderService.updateinfos").replace("###", " order_cost=0, order_status =?"); 
			    batch.update(sql, new Object[]{OrderService.ACCEPT_WAITSTATE,order_Id});
				//获取下一步配置信息
				Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "5"),new Object[]{task_cfg_id});
				//插入下一步
				int stepId = batch.insert(getSql("task.insertStep"), new Object[]{task_id,str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"","","96"});
				returnMap= stepDone(batch,returnMap,stepId,stepMap,task_id,0);
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
	//	returnMap.put("orderList", batch.queryForList(sql,params.toArray()));
		returnMap.put("orderList", batch.queryForList(sql.replaceAll("###", SystemConfig.getValue("FTP_HTTP")+ SystemConfig.getValue("FTP_ORDER_HTTP_PATH")).replaceAll("#name#", "").replaceAll("#step_name#", ""),params.toArray()));
		//支出页面导出
		request.setAttribute("exportToExcel", req.getAjaxValue(request, "exportToExcel"));
		return returnMap;
	}
	@Override
	public Map<String, Object> deleteFlow(Batch batch, String task_id)
			throws Exception {
		update(batch, "", null);
		batch.rollBack();
		return getReturnMap(0, "服务任务不可删除");
	}
	
	private int insertCheckHouse(Batch batch,HttpServletRequest request,String task_id) throws Exception
	{
		String housedesc = req.getAjaxValue(request, "housedesc");// 验房说明
	    String housepic=req.getAjaxValue(request, "housepic");// 房源图片
	    String alipay = req.getAjaxValue(request, "alipay");// 支付宝账号
	    String bank = req.getAjaxValue(request, "bankcard");// 银行卡
	    String bankname = req.getAjaxValue(request, "bankname");//开户银行
	    String startwaterdegree = req.getAjaxValue(request, "startwaterdegree");// 退租水度数
	    String startgasdegree = req.getAjaxValue(request, "startgasdegree");// 燃气度数
	    String startelectricdegree = req.getAjaxValue(request, "startelectricdegree");// 电度数
	    String endwaterdegree = req.getAjaxValue(request, "endwaterdegree");// 退租水度数
	    String endgasdegree = req.getAjaxValue(request, "endgasdegree");// 燃气度数
	    String endelectricdegree = req.getAjaxValue(request, "endelectricdegree");// 电度数
	    String degreepic = req.getAjaxValue(request, "degreepic");// 度数附件
	    String key = req.getAjaxValue(request, "key");// 回收钥匙
	    String doorcard = req.getAjaxValue(request, "doorcard");// 门卡回收
	    String reasons = req.getAjaxValue(request, "reasons");// 退租原因
	    String otherdesc = req.getAjaxValue(request, "otherdesc");// 其他回收物品说明
	    String favourable = req.getAjaxValue(request, "favourable");// 参加优惠活动说明
	    
	    batch.update(getSql("checkhouse.deleteI"),new Object[] {task_id});
	    String sql = getSql("checkhouse.insert");
	    int res= batch.insert(sql,new Object[] {favourable,housedesc,"",alipay,bank,bankname,startwaterdegree,startgasdegree,startelectricdegree,endwaterdegree,endgasdegree,endelectricdegree,"",key,doorcard,otherdesc,reasons,task_id});
	    if (res==-2)
	    {
	    	throw new TaskException("信息录入失败");
	    }
	    Map<String,String >  newPath = Imageorder(housepic, Integer.parseInt(task_id), request);
		if ( "1".equals(str.get(newPath, "state")))
		{
			housepic=str.get(newPath, "newPath");
		}
		else
		{
			throw new TaskException("图片上传失败");
		}
		Map<String,String >  newPath1= Imageorder(degreepic, Integer.parseInt(task_id), request);
		if ( "1".equals(str.get(newPath1, "state")))
		{
			degreepic=str.get(newPath1, "newPath");
		}
		else
		{
			throw new TaskException("图片上传失败");
		}
		sql = getSql("checkhouse.updatepic");
		res=batch.update(sql, new  Object[] {housepic,degreepic,res});
	    return res;
	}
	//图片截取
	public Map<String, String> Imageorder(String images, int keyId, HttpServletRequest request)
	{
		String newPath = "";
		FtpUtil ftp = null;
	    Map<String , String> gmap=new HashMap<String, String>();
		try 
		{
			ftp = new FtpUtil();
			if (!"".equals(images)) 
			{
				String[] pathArray = images.split(",");
				for (int j = 0; j < pathArray.length; j++)
				{
					 if (pathArray[j].contains(SystemConfig.getValue("FTP_HTTP")+ SystemConfig.getValue("FTP_LEASEORDER_HTTP_PATH"))) 
					 {
						newPath +=pathArray[j].replace("upload/tmp/", "").replace(SystemConfig.getValue("FTP_HTTP")+ SystemConfig.getValue("FTP_LEASEORDER_HTTP_PATH"),"")+ ",";
					    continue;
					 }
					 String tmpPath = String.valueOf(keyId)+ UUID.randomUUID().toString().replaceAll("-", "")+ ".png";
					 newPath += "/" + keyId +"/" + tmpPath+",";
					 boolean flag = ftp.uploadFile(request.getRealPath("/")+ pathArray[j], tmpPath,SystemConfig.getValue("FTP_LEASEORDER_PATH") +keyId+"/");
					 if (!flag) 
					 {
				        gmap.put("state","-3");
						return gmap;
				     }
				}
			}
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			e.printStackTrace();
			gmap.put("state","-3");
			return gmap;
		} 
		finally
		{
			// 关闭流
			if (ftp != null) {
				ftp.closeServer();
			}
		}
		gmap.put("state","1");
		gmap.put("newPath", newPath);
		return gmap;
	}
	//更新主表
	private Map<String, Object> stepDone(Batch obj,Map<String, Object> returnMap,int stepId,Map<String, Object>  stepMap,String task_id,int state) throws Exception
	{
		String sql = getSql("task.disposetTask.updateTask"); 
		obj.update(sql.replace("#endtime#","null"),new Object[]{0,FlowTaskState.START_TASK,0,stepId,str.get(stepMap, "step_name"),task_id});
		returnMap.put("step_id", stepId);
	    returnMap.put("step_name",str.get(stepMap, "step_name"));
		returnMap.put("state", state);
		return returnMap;
	}
	//更新主表
	private Map<String, Object> stepClose(Batch obj,Map<String, Object> returnMap,int stepId,Map<String, Object>  stepMap,String task_id,int state) throws Exception
	{
		String sql = getSql("task.disposetTask.updateTask"); 
		obj.update(sql.replace("#endtime#","null"),new Object[]{0,FlowTaskState.OVER_TASK,0,stepId,str.get(stepMap, "step_name"),task_id});
		returnMap.put("step_id", stepId);
	    returnMap.put("step_name",str.get(stepMap, "step_name"));
		returnMap.put("state", state);
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
	    msgMap.put("typename", "退租订单_");
	    msgMap.put("ocode", str.get(rodermap, "order_code"));
	    msgMap.put("code",  str.get(rodermap, "task_code"));
	    msgMap.put("operName",operName);
	    SmsSendMessage.smsSendMessage(mobile, msgMap, SmsSendContants.TASK_WARN);
	    return rodermap;
	}
	//获取当前操作人
	private String getNowOper(Batch obj,String step_id,String task_id)
	{
	   String nowOper= obj.queryForString(getSql("task.disposetTask.getnowoper"),new Object[] {task_id});
	   return nowOper;
	}
	
    //释放房源
    private void repleaHouse(Batch obj,String oper,int order_Id,String task_id) throws ParseException, TaskException
    {
		String	sql = getSql("task.getHouseForOrder");
		Map<String, Object> rankMap = obj.queryForMap(sql,new Object[] { order_Id });
		sql=getSql("task.getHouseForString");
		String house_id = obj.queryForString(sql,new Object[] { str.get(rankMap, "father_id") });
		int res2 = obj.queryForInt(getSql("orderService.counthouse"),new Object[] {str.get(rankMap, "house_id"),str.get(rankMap, "id")});
		if (res2 > 1) 
		{
			return;
		}
		// 调用接口参数
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("houseid", house_id);// 基础房源id
		params.put("oper", oper);// 操作人
		params.put("rankid", str.get(rankMap, "house_id"));// 租赁id
		RankHouse rankHouse = new RankHouse();
		// 调用租赁房源审批失败接口
		int res = rankHouse.approveNo(params, obj);
		if (res != 1) 
		{
			throw new TaskException("释放房源失败");
		}
	 }
	private String  returnType(String  key) 
	{
		switch (key) {
		case "0":
			key="11";//水费
			return key;
		case "1":
			key="12";//电费
			return key;
		case "2":
			key="2";//物业
			return key;
		case "3":
			key="10";//其他 
			return key;
		case "4":
			key="13";//燃气
			return key;
		}
		return null;
	}
	private Map<String,String> judgement(String agreementId,Object obj)
	{
		    Map<String , String> returnMap=new HashMap<String, String>();
			String settlements_id = queryString(obj, getSql("financial.receivable.getcategory"), new Object[]{agreementId,1});
			String key="1";
			logger.debug("settlements_id:"+settlements_id);
			if("".equals(settlements_id))
			{
				key="-1";
			}
			//查询出出租房名称
			String settlements_name = queryString(obj, getSql("basehouse.agreement.getHousName"), new Object[]{agreementId});
			logger.debug("查询出出租房名称:"+settlements_name);
			if("".equals(settlements_name))
			{
				key="-2";
			}
			if ("1".equals(key))
			{
				returnMap.put("status", "1");
				returnMap.put("settlements_name",settlements_name);
				returnMap.put("settlements_id",settlements_id);
			}
			else
			{
				returnMap.put("status",key);
			}
			return returnMap;
	}
	private int insertSel(Map<String,String> params,Map<String,String> leaveMap,int state,String type ,Object obj)
	{
		String cost = str.get(leaveMap, "cost");//价格
		String operid = str.get(params, "operid");//操作人
		String agreement_id = str.get(params, "agreement_id");//合约Id
		String settlements_name = str.get(params, "settlements_name")+"_"+str.get(leaveMap, "name");//财务项目名称
		String settlements_id = str.get(params, "settlements_id");//财务项目Id
		String startTime = DateHelper.getToday("YYYY-MM-dd HH:mm:ss");//开始结束时间
		String sql="";
		if (state==-1) 
		{
			sql=getSql("financial.receivable.taskinsert");
		}
		else if (state==1) 
		{
			sql=getSql("financial.payable.taskinsert");
		}
		int exc = update(obj,sql , new Object[]{settlements_name,settlements_id,2,agreement_id,type,Float.parseFloat(cost),startTime,"","",null,operid,"","",startTime,startTime});
		if(exc == 0)
		{
			return 0;
		}
		return 1;
}
}
