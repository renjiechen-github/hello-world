package pccom.web.flow.self.order;

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
import pccom.common.util.FtpUtil;
import pccom.web.beans.SystemConfig;
import pccom.web.flow.base.OrderService;
import pccom.web.flow.base.TaskException;
import pccom.web.flow.interfaces.FlowStepBaseDispose;
import pccom.web.flow.util.FlowStepState;
import pccom.web.flow.util.FlowTaskState;
import pccom.web.interfaces.Financial;
import pccom.web.server.sys.account.AccountService;
@Controller
public class WorkOrder  extends FlowStepBaseDispose{
	@Override
	public Map<String, Object> stepDispose(Batch batch,
			HttpServletRequest request, String step_id, String task_id,
			String state, String cfg_step_id) throws Exception {
		Map<String,Object> returnMap = super.stepDispose(batch, request, step_id, task_id, state,cfg_step_id);
		String sql = "";
		String task_cfg_id = req.getAjaxValue(request, "task_cfg_id");
		String oper = this.getUser(request).getId();// 获取操作人
		Financial financial = new Financial();
		sql=getSql("task.getResource");
    	int order_Id=batch.queryForInt(sql, new Object[]{task_id});
		if("2".equals(cfg_step_id))
		{
			 if(FlowStepState.END_STEP.equals(state))
		     {  
				//获取下一步配置信息
				Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "3"),new Object[]{task_cfg_id});
				//插入下一步
				int stepId = batch.insert(getSql("task.insertStep"), new Object[]{task_id,str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"","",oper});
				sql = getSql("task.disposetTask.updateTask"); 
				//更新主表
				batch.update(sql.replace("#endtime#","null"),new Object[]{0,FlowTaskState.START_TASK,0,stepId,str.get(stepMap, "step_name"),task_id});
				returnMap.put("step_id", stepId);
				returnMap.put("step_name",str.get(stepMap, "step_name"));
				returnMap.put("state", 0);
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
				msgMap.put("typename", "维修服务");
				msgMap.put("ocode", str.get(rodermap, "order_code"));
				msgMap.put("code", code);
				msgMap.put("operName", operName);
		        SmsSendMessage.smsSendMessage(mobile, msgMap, SmsSendContants.TASK_WARN);
				//获取下一步配置信息
				Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "2"),new Object[]{task_cfg_id});
				//插入下一步
				int stepId = batch.insert(getSql("task.insertStep"), new Object[]{task_id,str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"","",account});
				//手机推送
				String sqlu =getSql("orderService.insertPush"); 
			    batch.update(sqlu, new Object[]{"维修订单_"+str.get(rodermap, "order_code"),"您有新的任务，请点击进入处理。任务编码："+code+",订单编码："+str.get(rodermap, "order_code"),"任务处理","mobile/task/showDetailFlowDetail.do?step_id="+stepId+"&task_id="+task_id,mobile});
				
			    sql = getSql("task.disposetTask.updateTask"); 
				//更新主表
				batch.update(sql.replace("#endtime#","null"),new Object[]{0,FlowTaskState.START_TASK,0,stepId,str.get(stepMap, "step_name"),task_id});
				returnMap.put("step_id", stepId);
				returnMap.put("state", 0);
				returnMap.put("step_name",str.get(stepMap, "step_name"));
			}
		}
		else if("3".equals(cfg_step_id))
		{
			if(FlowStepState.MODIFY_STEP.equals(state))
			{
				//修改
				String oname = req.getAjaxValue(request, "oname");//订单名称
				String odesc = req.getAjaxValue(request, "odesc");//订单说明
				String ocost = req.getAjaxValue(request, "ocost");//订单费用
				String childtype = req.getAjaxValue(request, "childtype");//订单费用
				String upurl = req.getAjaxValue(request, "upurl");//图片地址
				String service = req.getAjaxValue(request, "servicetime");//预约时间
				//如果图片地址不为空、
				String urlPath="";
		    	Map<String,String >  newPath = Imageorder(upurl, Integer.parseInt(task_id), request);
				if ( "1".equals(str.get(newPath, "state")))
				{
					urlPath=str.get(newPath, "newPath");
				}
				else
				{
					throw new TaskException("图片上传失败");
				}
			    String oldcost=batch.queryForString(getSql("orderService.queryocost"),new Object[]{order_Id});
			    String nowUpOper=batch.queryForString(getSql("orderService.getoper"),new Object[]{order_Id});
			    if (nowUpOper!=null&&!"".equals(nowUpOper)&&!"0".equals(nowUpOper))
			    {
			    	nowUpOper=nowUpOper+"|"+oper;
				}else{
					nowUpOper="|"+oper;
				}
				if (Float.parseFloat(oldcost)!=Float.parseFloat(ocost)&&Float.parseFloat(ocost)>0) 
				{
					String AgreeId=batch.queryForString(getSql("orderService.queryAgreeId"),new Object[]{order_Id});	
					Map<String,String> orderMap =new HashMap<String,String>();
					orderMap.put("agreement_id", AgreeId);
					orderMap.put("cost", ocost);
					orderMap.put("operid", oper);
					orderMap.put("order_id",String.valueOf(order_Id));
			        int resf=financial.orderRent(orderMap, batch);//插入财务数据
			    	if (resf!=1) 
			    	{
			    		throw new TaskException("财务未找到");
					}
			    	//修改订单状态进入待支付
			    	sql=getSql("orderService.updateinfos").replace("###", "order_status=?, order_name =?,file_path=?, action_time=now(),order_desc=?,service_time=?,childtype=?,order_cost=?,task_id=? "); 
					batch.update(sql, new Object[]{"2",oname,urlPath,odesc,service,childtype,ocost,nowUpOper,order_Id});
					//获取下一步配置信息
					Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "5"),new Object[]{task_cfg_id});
					int thirdStep = batch.insert(getSql("task.insertStep"), new Object[]{task_id, str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"","",""});
					
					//更新当前步骤处理信息
					 sql = getSql("task.disposetTask.updateStep");
					batch.update(sql,new Object[]{"",0,thirdStep,oper,"",FlowTaskState.BREAK_TASK,step_id});
					
					sql = getSql("task.disposetTask.updateTask"); 
					//更新主表
					batch.update(sql.replace("#endtime#","null"),new Object[]{0,FlowTaskState.START_TASK,0,thirdStep,str.get(stepMap, "step_name"),task_id});
					
					returnMap.put("step_id", str.get(stepMap, "step_id"));
					returnMap.put("step_name",str.get(stepMap, "step_name"));
					returnMap.put("state", 2);
				}
				else
				{
					sql=getSql("orderService.updateinfos").replace("###", " order_name =?,file_path=?,action_time=now(),order_desc=?,childtype=?,service_time=?,task_id=? "); 
				    batch.update(sql, new Object[]{oname,urlPath,odesc,childtype,service,nowUpOper,order_Id});
					//更新主表
					sql = getSql("task.disposetTask.updateTask"); 
					batch.update(sql.replace("#endtime#","null"),new Object[]{0,FlowTaskState.START_TASK,0,step_id,"修改订单",task_id});
					returnMap.put("step_id", FlowTaskState.CREATE_TASK);
					returnMap.put("step_name", "修改订单");
					returnMap.put("state", 0); 
				}
			}
			else if(FlowStepState.END_STEP.equals(state))
			{
				String upurl = req.getAjaxValue(request, "upurl");//图片地址
				String urlPath="";
		    	Map<String,String >  newPath = ImageWork(upurl, Integer.parseInt(task_id), request);
				if ( "1".equals(str.get(newPath, "state")))
				{
					urlPath=str.get(newPath, "newPath");
				}
				else
				{
					throw new TaskException("图片上传失败");
				}
				//上传图片
				if (!"".equals(urlPath))
				{   String fileId="T"+task_id+"S"+step_id;
					String[] image=urlPath.split(",");
					for (int t = 0; t < image.length; t++)
					{
						String filename=image[t].substring(image[t].lastIndexOf("/")+1,image[t].indexOf("."));
					    batch.update(getSql("task.insertFile"), new Object[] {fileId,filename,image[t], 1});
					}
					   returnMap.put("file_id",fileId);
					//batch.update(getSql("task.disposetTask.updateStepfile"), new Object[] {fileId, step_id});
				}
				//获取下一步配置信息
				Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "4"),new Object[]{task_cfg_id});
				//插入下一步
				int stepId = batch.insert(getSql("task.insertStep"), new Object[]{task_id,str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"",AccountService.CUSTOMER_MANAGER,""});
				sql = getSql("task.disposetTask.updateTask"); 
				//更新主表
				batch.update(sql.replace("#endtime#","null"),new Object[]{0,FlowTaskState.START_TASK,0,stepId,str.get(stepMap, "step_name"),task_id});
				returnMap.put("step_id", stepId);
				returnMap.put("step_name",str.get(stepMap, "step_name"));
				returnMap.put("state", 0);
			}
		}
		else if("4".equals(cfg_step_id))
		{
			if(FlowStepState.ROLLBACK_STEP.equals(state))
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
				msgMap.put("typename", "维修服务");
				msgMap.put("ocode", str.get(rodermap, "order_code"));
				msgMap.put("code", code);
				msgMap.put("operName", operName);
		        SmsSendMessage.smsSendMessage(mobile, msgMap, SmsSendContants.TASK_WARN);
				String upurl = req.getAjaxValue(request, "upurl");//图片地址
				String urlPath="";
		    	Map<String,String >  newPath = ImageWork(upurl, Integer.parseInt(task_id), request);
				if ( "1".equals(str.get(newPath, "state")))
				{
					urlPath=str.get(newPath, "newPath");
				}
				else
				{
					throw new TaskException("图片上传失败");
				}
				//修改图片地址
				if (!"".equals(urlPath))
				{   String fileId="T"+task_id+"S"+step_id;
					String[] image=urlPath.split(",");
					for (int t = 0; t < image.length; t++)
					{
						String filename=image[t].substring(image[t].lastIndexOf("/")+1,image[t].indexOf("."));
					    batch.update(getSql("task.insertFile"), new Object[] {fileId,filename,image[t], 1});
					}
					   returnMap.put("file_id",fileId);
					//batch.update(getSql("task.disposetTask.updateStepfile"), new Object[] {fileId, step_id});
				}
			 	
				//获取下一步配置信息
			 	Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "2"),new Object[]{task_cfg_id});
				//插入下一步
				int stepId = batch.insert(getSql("task.insertStep"), new Object[]{task_id,str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"","",account});
				sql = getSql("task.disposetTask.updateTask"); 
				//手机推送
				String sqlu =getSql("orderService.insertPush"); 
			    batch.update(sqlu, new Object[]{"维修订单_"+str.get(rodermap, "order_code"),"您有新的任务，请点击进入处理。任务编码："+code+",订单编码："+str.get(rodermap, "order_code"),"任务处理","mobile/task/showDetailFlowDetail.do?step_id="+stepId+"&task_id="+task_id,mobile});
				
				//更新主表
				batch.update(sql.replace("#endtime#","null"),new Object[]{0,FlowTaskState.START_TASK,0,stepId,str.get(stepMap, "step_name"),task_id});
				returnMap.put("step_id", stepId);
				returnMap.put("step_name",str.get(stepMap, "step_name"));
				returnMap.put("state", 0);
			}
			else if(FlowStepState.END_STEP.equals(state))
			{   
				String upurl = req.getAjaxValue(request, "upurl");//图片地址
				String urlPath="";
		    	Map<String,String >  newPath = ImageWork(upurl, Integer.parseInt(task_id), request);
				if ( "1".equals(str.get(newPath, "state")))
				{
					urlPath=str.get(newPath, "newPath");
				}
				else
				{
					throw new TaskException("图片上传失败");
				}
				//修改图片地址
				if (!"".equals(urlPath))
				{   String fileId="T"+task_id+"S"+step_id;
					String[] image=urlPath.split(",");
					for (int t = 0; t < image.length; t++)
					{
						String filename=image[t].substring(image[t].lastIndexOf("/")+1,image[t].indexOf("."));
					    batch.update(getSql("task.insertFile"), new Object[] {fileId,filename,image[t], 1});
					}
					   returnMap.put("file_id",fileId);
				//	batch.update(getSql("task.disposetTask.updateStepfile"), new Object[] {fileId, step_id});
				}
				
				//关闭
				//自己处理的业务逻辑
				sql=getSql("orderService.updateinfos").replace("###", " order_status =? "); 
			    batch.update(sql, new Object[]{OrderService.DONE_STATE,order_Id});
				//获取下一步配置信息
				Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "6"),new Object[]{task_cfg_id});
				sql = getSql("task.disposetTask.updateTask"); 
				//更新主表
				batch.update(sql.replace("#endtime#","null"),new Object[]{0,FlowTaskState.OVER_TASK,0,str.get(stepMap, "step_id"),str.get(stepMap, "step_name"),task_id});
			    returnMap.put("step_id", 0);
			    returnMap.put("step_name",str.get(stepMap, "step_name"));
			    returnMap.put("state", 0);
			}else if(FlowStepState.FOLLOW_UP.equals(state))
			{
				//获取下一步配置信息
				Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "4"),new Object[]{task_cfg_id});
				//插入下一步                                                                                                                                                                                                                                                                                                                                                                                                       //指定到部门角色
				int stepId = batch.insert(getSql("task.insertStep"), new Object[]{task_id,str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"",AccountService.CUSTOMER_MANAGER,""});
				sql = getSql("task.disposetTask.updateTask"); 
				//更新主表
				batch.update(sql.replace("#endtime#","null"),new Object[]{0,FlowTaskState.START_TASK,0,stepId,str.get(stepMap, "step_name"),task_id});
				returnMap.put("step_id", stepId);
				returnMap.put("step_name",str.get(stepMap, "step_name"));
				returnMap.put("state", 0);
			}
		}
		else if("5".equals(cfg_step_id))
		{
			if(FlowStepState.END_STEP.equals(state))
			{  
				Map<String,String> orderMap =new HashMap<String,String>();
				orderMap.put("order_id",String.valueOf(order_Id));
			    int resf=financial.repealOrder(orderMap, batch);
			    if (resf!=1) 
			    {
			    	throw new TaskException("财务未找到");
			    }
				//自己处理的业务逻辑
				sql=getSql("orderService.updateinfos").replace("###", " order_cost=0, order_status =? "); 
			    batch.update(sql, new Object[]{OrderService.ACCEPT_WAITSTATE,order_Id});
				//获取下一步配置信息
				Map<String,Object> stepMap = batch.queryForMap(getSql("task.getStep").replace("##", "3"),new Object[]{task_cfg_id});
				//插入下一步
				int stepId = batch.insert(getSql("task.insertStep"), new Object[]{task_id,str.get(stepMap, "step_name"),step_id,str.get(stepMap, "step_id"),"","",oper});
				sql = getSql("task.disposetTask.updateTask"); 
				//更新主表
				batch.update(sql.replace("#endtime#","null"),new Object[]{0,FlowTaskState.RECOVER_TASK,0,stepId,str.get(stepMap, "step_name"),task_id});
				returnMap.put("step_id", str.get(stepMap, "step_id"));
				returnMap.put("step_name",str.get(stepMap, "step_name"));
				returnMap.put("state", 0);
			}
		}
		return returnMap;
	}
	
	@Override
	public Map<String, Object> showStepDetail(Batch batch,HttpServletRequest request, String step_id, String task_id, boolean iscl, String cfg_step_id) throws Exception {
		logger.debug("cfg_step_id:"+cfg_step_id);
		Map<String,Object> returnMap = super.showStepDetail(batch, request, step_id, task_id,iscl,cfg_step_id);
		List<String> params = new ArrayList<String>();
		String	sql=getSql("task.getResource");
    	String order_Id=batch.queryForString(sql, new Object[]{task_id});
		//查询出当前订单信息
		 sql = getSql("orderService.query.main");
		 sql += getSql("orderService.query.id");
		//获取当前订单Id
		params.add(order_Id);
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
	public Map<String, String> ImageWork(String images, int keyId, HttpServletRequest request)
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
					if (pathArray[j].contains(SystemConfig.getValue("FTP_HTTP")+ SystemConfig.getValue("FTP_TASK_HTTP_PATH"))) 
					{
						newPath +=pathArray[j].replace("upload/tmp/", "").replace(SystemConfig.getValue("FTP_HTTP")+ SystemConfig.getValue("FTP_TASK_HTTP_PATH"),"")+ ",";
					    continue;
					}
					   String tmpPath = String.valueOf(keyId)+ UUID.randomUUID().toString().replaceAll("-", "")+ ".png";
					   newPath += "/" + keyId +"/" + tmpPath+",";
					   boolean flag = ftp.uploadFile(request.getRealPath("/")+ pathArray[j], tmpPath,SystemConfig.getValue("FTP_TASK_HTTP") +keyId+"/");
					   
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
					if (pathArray[j].contains(SystemConfig.getValue("FTP_HTTP")+ SystemConfig.getValue("FTP_ORDER_HTTP_PATH"))) 
					{
						newPath +=pathArray[j].replace("upload/tmp/", "").replace(SystemConfig.getValue("FTP_HTTP")+ SystemConfig.getValue("FTP_ORDER_HTTP_PATH"),"")+ ",";
					    continue;
					}
					   String tmpPath = String.valueOf(keyId)+ UUID.randomUUID().toString().replaceAll("-", "")+ ".png";
					   newPath += "/" + keyId +"/" + tmpPath+",";
					   boolean flag = ftp.uploadFile(request.getRealPath("/")+ pathArray[j], tmpPath,SystemConfig.getValue("FTP_ORDER_PATH") +keyId+"/");
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
}
