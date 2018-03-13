package pccom.web.flow.base;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;
import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;
import pccom.common.util.Batch;
import pccom.web.beans.SystemConfig;

@Service("checkHouseService")
public class CheckHouseService extends FlowBase{

	/**
	 * 获取列表
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public void getList(HttpServletRequest request, HttpServletResponse response) {
		String orderId = req.getAjaxValue(request, "orderId");
		String step_type = req.getAjaxValue(request, "step_type");
		String taskId = req.getAjaxValue(request, "taskId");
		
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
		getPageList(request,response,sql,params.toArray());
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
			String housedesc = req.getAjaxValue(request, "housedesc");// 验房说明
			String housepic=req.getAjaxValue(request, "housepic");// 房源图片
			String alipay = req.getAjaxValue(request, "alipay");// 支付宝账号
			String bank = req.getAjaxValue(request, "bank");// 银行卡
			String bankname = req.getAjaxValue(request, "bankname");//开户银行
			String waterdegree = req.getAjaxValue(request, "waterdegree");// 退租水度数
			String gasdegree = req.getAjaxValue(request, "gasdegree");// 燃气度数
			String electricdegree = req.getAjaxValue(request, "electricdegree");// 电度数
			String degreepic = req.getAjaxValue(request, "degreepic");// 度数附件
			String key = req.getAjaxValue(request, "key");// 回收钥匙
			String doorcard = req.getAjaxValue(request, "doorcard");// 门卡回收
			String reasons = req.getAjaxValue(request, "reasons");// 退租原因
			String otherdesc = req.getAjaxValue(request, "otherdesc");// 其他回收物品说明
			String task_id = req.getAjaxValue(request, "task_id");// 任务id
			String sql = getSql("checkhouse.insert");
			//housedesc,housepic,alipay,bank,bankname,waterdegree,gasdegree,electricdegree,degreepic,key,doorcard,otherdesc,reasons,task_id
			keyId =	this.insert(sql,new Object[] {housedesc,housepic,alipay,bank,bankname,waterdegree,gasdegree,electricdegree,degreepic,key,doorcard,otherdesc,reasons,task_id});
			return keyId;	
		}
	});
		return getReturnMap(i);
	}
	
}
