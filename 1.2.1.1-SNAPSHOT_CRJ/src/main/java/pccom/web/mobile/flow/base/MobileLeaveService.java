package pccom.web.mobile.flow.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;
import pccom.common.util.Batch;
import pccom.web.flow.base.FlowBase;

@Service("miLeaveService")
public class MobileLeaveService extends FlowBase{

	/**
	 * 获取列表
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return 
	 * @return
	 */
	public Object getList(HttpServletRequest request, HttpServletResponse response) {
		String orderId = req.getAjaxValue(request, "orderId");
		String areeId = req.getAjaxValue(request, "areeId");
		String taskId = req.getAjaxValue(request, "taskId");
		String step_type = req.getAjaxValue(request, "step_type");
		String sql = getSql("certificateLeaveService.main");
		List<String> params = new ArrayList<String>();
		if (!"".equals(orderId)) {
			sql += getSql("certificateLeaveService.orderId");
			params.add(orderId);
		}
		if (!"".equals(taskId)) {
			sql += getSql("certificateLeaveService.taskId");
			params.add(taskId);
		}
		if (!"".equals(step_type)) {
			sql += getSql("certificateLeaveService.step_type");
			params.add(step_type);
		}
		return this.getMobileList(request, sql, params);
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
		String id = req.getAjaxValue(request, "id");
		String orderId = req.getAjaxValue(request, "orderId");
		String step_type = req.getAjaxValue(request, "step_type");
		String sql = getSql("certificateLeaveService.main");
		List<String> params = new ArrayList<String>();
		if (!"".equals(orderId)) {
			sql += getSql("certificateLeaveService.orderId");
			params.add(orderId);
		}
		if (!"".equals(id)) {
			sql += getSql("certificateLeaveService.id");
			params.add(id);
		}
		if (!"".equals(step_type)) {
			sql += getSql("certificateLeaveService.step_type");
			params.add(step_type);
		}
		Map<String, Object> returnMap = db.queryForMap(sql,params.toArray());
	//	returnMap.put("list", JSONArray.fromObject(db.queryForList(sql,params.toArray())));
		return returnMap;
	}

	/**
	 * 订单修改
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	public Object update(final HttpServletRequest request,final HttpServletResponse response)
	{
	   final String oper = this.getUser(request).getId();// 获取操作人
       @SuppressWarnings("unchecked")
	    Object i = db.doInTransaction(new Batch<Object>()
	    {
		   @Override
		   public Integer execute() throws Exception
		   {
			    String id=req.getAjaxValue(request, "id");
				String degree = req.getAjaxValue(request, "degree");// 租赁ID
				String starttime = req.getAjaxValue(request, "starttime");// 开始时间
				String endtime = req.getAjaxValue(request, "endtime");// 结束时间
				String name = req.getAjaxValue(request, "name");// 订单说明
				String type = req.getAjaxValue(request, "type");// 订单金额
				String cost = req.getAjaxValue(request, "cost");// 子类型
				String desc = req.getAjaxValue(request, "desc");// 图片路径
				String sql = getSql("certificateLeaveService.info.update");
				this.update(sql.replace("####", "name=?,type=?,cost=?,costdesc=?,starttime=?,endtime=?,degree=?"), new Object[] {name,type,cost,desc,starttime,endtime,degree,id});
				return 1;	
		   }
	    });
		return getReturnMap(i);
	}
	/**
	 * 删除
	 * */
	public Object delete(HttpServletRequest request,HttpServletResponse response)
	{
		String id = req.getAjaxValue(request, "id");
		String sqlhouse = getSql("certificateLeaveService.info.delete");
		return getReturnMap(db.update(sqlhouse, new Object[] {id}));
	}
	/**
	 * 新增
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	public Object createOrder(final HttpServletRequest request,final HttpServletResponse response) {
	// final String oper = this.getUser(request).getId();// 获取操作人
    @SuppressWarnings("unchecked")
	 Object i = db.doInTransaction(new Batch<Object>()
	 {
		 int keyId;
		 @Override
		public Integer execute() throws Exception
		{
			String orderId = req.getAjaxValue(request, "orderId");// 订单名称
			String taskId=req.getAjaxValue(request, "taskId");// 订单名称
			String degree = req.getAjaxValue(request, "degree");// 租赁ID
			String starttime = req.getAjaxValue(request, "starttime");// 开始时间
			String endtime = req.getAjaxValue(request, "endtime");// 结束时间
			String name = req.getAjaxValue(request, "name");// 订单说明
			String type = req.getAjaxValue(request, "type");// 订单金额
			String cost = req.getAjaxValue(request, "cost");// 子类型
			String desc = req.getAjaxValue(request, "desc");// 图片路径
			String step_type = req.getAjaxValue(request, "step_type");// 图片路径
			String sql = getSql("certificateLeaveService.info.insert");
			keyId =	this.insert(sql,new Object[] {name,type,cost,desc,starttime,endtime,degree,orderId,step_type,taskId});
			return keyId;	
		}
	});
		return getReturnMap(i);
	}
	
}
