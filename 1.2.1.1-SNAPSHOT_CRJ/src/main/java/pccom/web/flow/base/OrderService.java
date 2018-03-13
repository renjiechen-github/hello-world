package pccom.web.flow.base;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.rpc.ServiceException;

import jxl.Workbook;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pccom.common.util.Batch;
import pccom.common.util.Constants;
import pccom.common.util.DateHelper;
import pccom.common.util.FtpUtil;
import pccom.common.util.StringHelper;
import pccom.web.beans.SystemConfig;
import pccom.web.flow.bean.TaskBean;
import pccom.web.interfaces.Financial;
import pccom.web.interfaces.OrderInterfaces;
import pccom.web.interfaces.RankHouse;

import com.ycdc.core.plugin.sms.SmsSendContants;
import com.ycdc.core.plugin.sms.SmsSendMessage;
import com.ycroom.util.aes.YCBizMsgCrypt;

/**
 * 订单类提供新增查询
 * 封装状态常亮
 * @author 刘飞
 */
@Service("orderService")
public class OrderService extends FlowBase{

	@Autowired
	private Financial financial;

	/**
	 * 订单状态
	 * 
	 * */
	public static final int ADD_WAITSTATE = 0; // 新增待处理
	public static final int PAY_WAITSTATE = 2; // 待支付
	public static final int RECIEVE_WAITSTATE = 3; // 待退款
	public static final int GOTO_WAITSTATE = 4; // 待派单
	public static final int ACCEPT_WAITSTATE = 6; // 待接单
	public static final int ACTION_WAITSTATE = 8; // 待执行
	public static final int COMPLETE_WAITSTATE = 10; // 待完成
	public static final int DONE_STATE = 200; // 已完成
	public static final int CLOSE_STATE = -1; // 已关闭
	
	/**
	 * 订单类型
	 * */
	public static final int RANK_ORDER = 0; // 
	public static final int WORK_ORDER = 1; // 预约维修
	public static final int CLEAN_ORDER = 2; // 保洁订单
	public static final int WRITE_ORDER  = 3; // 投诉订单
	public static final int OTHER_ORDER  = 4; // 其他
	public static final int OWNER_ORDER  = 5; //业主维修
	public static final int ADMISSION_ORDER  = 6; //入住服务
	public static final int LEASEORDER_ORDER  = 7; // 其他
	public static final int PRESS_RENTAL = 8; // 催租
	public static final int ROUTINE_CLEANING  = 9; // 催租
	
	/**
	 * 服务获取列表
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public void getList(HttpServletRequest request, HttpServletResponse response) {
		String ordertype = req.getAjaxValue(request, "order_type");
		String order_name = req.getAjaxValue(request, "order_name");
		String	status = req.getAjaxValue(request, "status");
		String sql = getSql("orderService.query.main");
		List<String> params = new ArrayList<String>();
		if (!"".equals(ordertype)) {
			sql += getSql("orderService.query.order_type");
			params.add(ordertype);
		}
		if (!"".equals(status)) {
			sql += getSql("orderService.query.status").replace("o.order_status in(?)","o.order_status in("+status+")");
		}
		//and (o.order_name like '%看房%' or o.user_name like '%看房%' or o.user_mobile like '%看房%' or o.order_code like '%看房%')
		if (!"".equals(order_name)) {
			sql=sql.replaceAll("#name#", " and (o.order_name like ? or o.user_name like ? or o.user_mobile like ? or o.order_code like ?)");
			sql=sql.replaceAll("#step_name#", " AND t.step_name LIKE ? ");
			params.add("%" + order_name + "%");
			params.add("%" + order_name + "%");
			params.add("%" + order_name + "%");
			params.add("%" + order_name + "%");
			params.add("%" + order_name + "%");
		}else{
			sql=sql.replaceAll("#name#","");
			sql=sql.replaceAll("#step_name#","");
		}
		getPageList(request,response,sql.replaceAll("###", SystemConfig.getValue("FTP_HTTP")+ SystemConfig.getValue("FTP_ORDER_HTTP_PATH")),params.toArray(), "orderService.query.orderBy");
	}
	
	
	/**
	 * 服务获取列表
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public void shGetList(HttpServletRequest request, HttpServletResponse response) {
		logger.debug("开始时间-------------------------------------"+DateHelper.getToday("yy-MM-dd HH:mm:ss"));
		String ordertype = req.getAjaxValue(request, "order_type");
		String order_name = req.getAjaxValue(request, "order_name");
		String	status = req.getAjaxValue(request, "status");
		String sql = getSql("orderService.querysh.mainstart");
		List<String> params = new ArrayList<String>();
		if (!"".equals(status)) {
			sql += getSql("orderService.querysh.status").replace("o.order_status in(?)","o.order_status in("+status+")");
		}
		if (!"".equals(ordertype)) {
			sql += " and o.order_type ="+ordertype;
		}else
		{
			sql += " and o.order_type in (0,1,2,3,4,5,6,9)";
		}
		if (!"".equals(order_name)) {
			sql += getSql("orderService.querysh.order_name");
			params.add("%" + order_name + "%");
			params.add("%" + order_name + "%");
			params.add("%" + order_name + "%");
			params.add("%" + order_name + "%");
			params.add("%" + order_name + "%");
		}
		sql += getSql("orderService.querysh.mainend");
		getPageListSh(request,response,sql,params.toArray(),"orderService.querysh.orderBy");
		logger.debug("结束时间-------------------------------------"+DateHelper.getToday("yy-MM-dd HH:mm:ss"));
	}

	
	/**
	 * 分页操作 针对有默认排序的语句
	 * @author 刘飞
	 * @param request
	 * @param sql 主要的sql语句，可进行拼接，不能包含order by 语句与limik语句在后面
	 * @param defOrderBySqlKey  默认的排序 对应的key
	 * @return
	 */
	public Object getPageListSh(HttpServletRequest request,HttpServletResponse response,String sql,Object[] objs,String defOrderBySqlKey){
		String sSearch = req.getAjaxValue(request, "sSearch");//搜索条件内容
		String iDisplayLength = req.getAjaxValue(request, "iDisplayLength");//每页显示的条数
		String iDisplayStart = req.getAjaxValue(request, "iDisplayStart");//从第几行开始
		String iSortCol = req.getAjaxValue(request, "iSortCol_0");//排序字段
		String sSortDir_0 = req.getAjaxValue(request, "sSortDir_0");//正续还是倒序
		String sizelength = req.getAjaxValue(request, "sizelength");//存在的数据总长度
		String isCxSeach = req.getAjaxValue(request, "isCxSeach");//是否需要重新计算长度
		String isDc = req.getAjaxValue(request, "isDc");
		String mDataProp = "";//排序对应的sql字段
		String pages = "";//分页语句
		//logger.debug("sizelength:"+sizelength);
		//计算排序
		if(!"".equals(iSortCol) && iSortCol != null){
			String bSortable_ = req.getAjaxValue(request, "bSortable_"+iSortCol);//判断是否存在排序
			if("false".equals(bSortable_)){//没有排序
				if(defOrderBySqlKey != null){
					//使用默认的排序
					mDataProp += getSql(defOrderBySqlKey);
				}
			}else{//该列上存在排序
				mDataProp += " order by  "+req.getAjaxValue(request, "mDataProp_"+iSortCol)+" "+sSortDir_0;
			}
		}else{
			mDataProp += getSql(defOrderBySqlKey);
		}
		//logger.debug("sSearch:"+sSearch);
		//获取总长度
		logger.debug("iDisplayLength:"+iDisplayLength+"--sizelength:"+sizelength);
		//添加分页操作
		if("".equals(isDc)){
			if(("".equals(sizelength) || "1".equals(isCxSeach))){
				sizelength = "";
				//进行循环判断处理
				String sql1 = sql;
				//logger.debug("原始sql:"+sql);
				String[] sqlfrom = sql1.split("[fF][rR][oO][mM]");
				String sql2 = "";
				//logger.debug("原始sql1:"+sql1);
				for(int i=0;i<sqlfrom.length-1;i++){
					//logger.debug("--1-"+sqlfrom[i]);
					if(!"".equals(sizelength)){
						continue;
					}
					sql2 = "select 1  ";
					for(int j=i+1;j<sqlfrom.length ;j++){
						sql2 += " from "+sqlfrom[j];
					}
					logger.debug("sq1l2:"+sql2);
					String length = db.queryForString("select count(1) from ("+sql2+") ccc",objs);
					if(!"".equals(length)){
						sizelength = length;
					}
				}
				//sizelength = db.queryForString("select count(1) from ("+sql+") ccc",objs);
			}
			logger.debug("iDisplayLength:"+iDisplayLength+"--sizelength:"+sizelength);
			iDisplayLength = "".equals(iDisplayLength)?"5":iDisplayLength;
			if(!"".equals(iDisplayLength)&&!"NaN".equals(iDisplayLength) && Integer.valueOf(iDisplayLength) < Integer.valueOf(sizelength)){//当总长度大于分页数量可进行分页
				pages = " LIMIT "+iDisplayStart+", "+iDisplayLength;
			}
		}
		
		//查询出该用户对于的菜单信息
		List<Map<String,Object>> list = db.queryForList(sql+mDataProp+pages,objs);
		//开始进行分页操作
		
		Map<String,Object> returnMap = getReturnMap(1);
		returnMap.put("data", JSONArray.fromObject(list));
		returnMap.put("sEcho", req.getAjaxValue(request, "sEcho"));
		returnMap.put("iTotalRecords", sizelength);
		returnMap.put("iTotalDisplayRecords", sizelength);
		returnMap.put("iDisplayLength", iDisplayLength);
		returnMap.put("iDisplayStart", iDisplayStart);
		if("true".equals(isDc)){ 
			String coln = req.getAjaxValue(request, "colum");
//			logger.debug("coln:"+coln);
			JSONArray obj = JSONArray.fromObject(coln);
			//String[][] excelParams = {{"测试","aaa","1"},{"测试1","aaa1","1"}};
			String[][] excelParams = new String[obj.size()][3];
			for(int i=0;i<obj.size();i++){
				excelParams[i][0] = obj.getJSONObject(i).getString("title");
				excelParams[i][1] = obj.getJSONObject(i).getString("name");
			}
//			List<Map<String,Object>> expDatalist = new ArrayList<Map<String,Object>>();
//			Map<String,Object> map1 = new HashMap<String, Object>();
//			map1.put("aaa", "sasa");
//			map1.put("aaa1", "1sasa");
//			expDatalist.add(map1);
			String fileName = "";
			try{
				String expname = req.getAjaxValue(request, "expname");
				String istz = req.getAjaxValue(request, "istz_");
				logger.debug("expname:"+expname);
				WritableWorkbook book = null;
				String excle ="";
				if("".equals(istz)){
					// 打开文件
		            response.reset();
		            response.setContentType("application/msexcel");
		            response.setHeader("Content-disposition", "attachment;filename=" + java.net.URLEncoder.encode(expname+".xls", "UTF-8"));
		            response.setCharacterEncoding("UTF-8");
		            book = Workbook.createWorkbook(response.getOutputStream());
				}else{
					String os = System.getProperty("os.name");  
					if(os.toLowerCase().startsWith("win")){  
						fileName = expname+new Random().nextInt(10000000)+"";
					}else{
						fileName = new Random().nextInt(10000000)+"";
					}
					logger.debug(fileName);
					excle = "/upload/excel/"+fileName+".xls";
					logger.debug(excle);
					File file1 = new File(request.getRealPath("/")+excle);
					if(!file1.exists()){
						file1.createNewFile();
					}
					logger.debug(excle);
					//生成临时存储文件 
					book= Workbook.createWorkbook(file1);  
				}
				
	            WritableSheet sheet = book.createSheet(expname, 0);
	            
	            // 设置标题样式
	            WritableFont font1 = new WritableFont(WritableFont.createFont("宋体"), 10, WritableFont.BOLD);
	            WritableCellFormat cellFormat1 = new WritableCellFormat(font1);
	            cellFormat1.setBorder(jxl.format.Border.ALL, BorderLineStyle.THIN); // 设置边框
	            cellFormat1.setAlignment(jxl.format.Alignment.CENTRE);
	            cellFormat1.setBackground(jxl.format.Colour.GRAY_25); 
	            cellFormat1.setWrap(true);
	            cellFormat1.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
	            jxl.write.WritableCellFormat wcf1 = new jxl.write.WritableCellFormat(cellFormat1);
	            logger.debug("??????????????????111??导出???????????????????????????");
	            // 设置内容样式
	            WritableFont font2 = new WritableFont(WritableFont.createFont("宋体"), 10);
	            WritableCellFormat cellFormat2 = new WritableCellFormat(font2);
	            cellFormat2.setBorder(jxl.format.Border.ALL, BorderLineStyle.THIN);
	            cellFormat2.setAlignment(jxl.format.Alignment.CENTRE); 
	            cellFormat2.setWrap(true);
	            jxl.write.WritableCellFormat wcf2 = new jxl.write.WritableCellFormat(cellFormat2);
	             
	            // 设置首行的高度
	            sheet.setRowView(0, 600);
	            
	            // 设置列名及列宽
	            for(int i=0;i<excelParams.length;i++)
	            {
	                sheet.setColumnView(i, 20);
	                sheet.addCell(new Label(i, 0, excelParams[i][0], wcf1));
	            }
	            
	            // 写入数据
	            for (int i = 0; i < list.size(); i++)
	            {
	               Map<String, Object> map = list.get(i);
	               if(map != null)
	               {
	                   for(int j=0;j<excelParams.length;j++)
	                   {
	                       sheet.addCell(new Label(j, i + 1, StringHelper.get(map, excelParams[j][1]), wcf2));
	                   }
	               }
	            }
	            
	            book.write();
	            book.close();
	            if(!"".equals(istz)){
	            	try {
		            	Map<String,Object> returnMap_ = new HashMap<String,Object>();
		            	returnMap_.put("state", 1);
		            	returnMap_.put("name", excle);
						response.setCharacterEncoding("UTF-8");
						response.getWriter().print(JSONObject.fromObject(returnMap_));
					} catch (IOException e) {
						e.printStackTrace();
					}
	            }
//	            setFileDownloadHeader(request, response, fileName+".xls");
			}catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			try {
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(JSONObject.fromObject(returnMap));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * 订单修改
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	public Object updateOrder(final HttpServletRequest request,final HttpServletResponse response) {
	 final String oper = this.getUser(request).getId();// 获取操作人
    @SuppressWarnings("unchecked")
	 Object i = db.doInTransaction(new Batch<Object>()
	 {
		 @Override
		public Integer execute() throws Exception
		{
			String id = req.getAjaxValue(request, "id");//订单名称
		    String orderName = req.getAjaxValue(request, "orderName");//订单名称
			String correspondent = req.getAjaxValue(request, "correspondent");//租赁ID
			String oserviceTime = req.getAjaxValue(request, "oserviceTime");//预约时间
			String odesc = req.getAjaxValue(request, "odesc");//订单说明
			String ocost = req.getAjaxValue(request, "ocost");//订单金额
			String childtype = req.getAjaxValue(request, "childtype");//子类型
			String status = req.getAjaxValue(request, "status");//子类型
			String upurl = req.getAjaxValue(request, "upurl");//图片路径
			String sql= "";
			sql=getSql("orderService.queryocostbyorder");
		    String oldcost=this.queryForString(sql,new Object[]{id});
		    String nowUpOper=this.queryForString(getSql("orderService.getoper"),new Object[]{id});
		    if (nowUpOper!=null&&!"".equals(nowUpOper)&&!"0".equals(nowUpOper))
		    {
		    	nowUpOper=nowUpOper+"|"+oper;
			}else{
				nowUpOper="|"+oper;
			}
		    //如果订单金额大于0，等待支付
		    if (ocost!=null&&!"".equals(ocost)&&Float.parseFloat(ocost)>0&&Float.parseFloat(ocost)!=Float.parseFloat(oldcost))
		    {
				Map<String,String> orderMap =new HashMap<String,String>();
				orderMap.put("agreement_id", correspondent);
				orderMap.put("cost", ocost);
				orderMap.put("operid", oper);
				orderMap.put("order_id", id);
		        int resf=financial.orderRent(orderMap, this);//插入财务数据
		    	if (resf!=1) 
		    	{
		    		this.rollBack();
					return -3;
				}
		    	sql=getSql("orderService.updateinfo");
				 //修改订单
			    this.update(sql, new Object[] {"2", orderName, ocost,oserviceTime,odesc,childtype,nowUpOper,id});
			}
		    else
		    {
	    	     sql=getSql("orderService.updateinfo");
	    		 this.update(sql, new Object[] {status, orderName, ocost,oserviceTime,odesc,childtype,nowUpOper,id});
			}
		    //如果图片地址不为空
		    if (upurl!=null&&!"".equals(upurl))
		    {   //上传图片
		    	String urlPath="";
		    	Map<String,String >  newPath = ImageWork(upurl, Integer.parseInt(id), request);
				if ( "1".equals(str.get(newPath, "state")))
				{
					urlPath=str.get(newPath, "newPath");
				}
				else
				{
					this.rollBack();
					logger.debug("图片上传失败");
					throw new Exception("网络图片上传失败");
				}
				//修改图片地址
				if (!"".equals(urlPath))
				{  
					sql=getSql("orderService.updateUrl");
					this.update(sql, new Object[]{urlPath,id});
				}
			}
		 return 1;	
		}
	});
		return getReturnMap(i);
	}
	/**
	 * 订单新增
	 * 
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 * @throws ServiceException
	 */
	public Object createOrder(final HttpServletRequest request,final HttpServletResponse response) {
	 final String oper = this.getUser(request).getId();// 获取操作人
    @SuppressWarnings("unchecked")
	 Object i = db.doInTransaction(new Batch<Object>()
	 {
		 @Override
		public Integer execute() throws Exception
		{
		    String orderName = req.getAjaxValue(request, "orderName");//订单名称
			String correspondent = req.getAjaxValue(request, "correspondent");//租赁ID
			String ouserMobile = req.getAjaxValue(request, "ouserMobile");//用户手机
			String ouserName = req.getAjaxValue(request, "ouserName");//用户名
			String oserviceTime = req.getAjaxValue(request, "oserviceTime");//预约时间
			String odesc = req.getAjaxValue(request, "odesc");//订单说明
			String ocost = req.getAjaxValue(request, "ocost");//订单金额
			String otype=req.getAjaxValue(request, "otype");//订单类型
			String childtype = req.getAjaxValue(request, "childtype");//子类型
			String upurl = req.getAjaxValue(request, "upurl");//图片路径
			if ("7".equals(otype))
			{ 
				int rest= this.queryForInt(getSql("orderService.checkIsDo"),new Object [] {correspondent,otype});
				if (rest>0) 
				{
					return -4;
				}	 
				rest= this.queryForInt(getSql("orderService.checkIsDo2"),new Object [] {correspondent,otype});
				if (rest>0) 
				{
					return -4;
				}	
			}
			String sql= getSql("userInfo.checkUser");
			//查询用户是否存在
		    int res=this.queryForInt(sql,new Object[]{ouserMobile});
		    int userId;
		    if (res==0)
		    {
		    	//新增用户获取用户id
			  sql = getSql("userInfo.insert");
			  userId= this.insert(sql,new Object[]{ouserName,"3",ouserMobile,"","","","",""}); 
		    }
		    else
		    {
			   //获取用户Id
			  sql = getSql("userInfo.getId");
			  userId=this.queryForInt(sql,new Object[]{ouserMobile});
		    }
			sql=getSql("orderService.orderSave");
		    int keyId;
            if (ocost!=null&&!"".equals(ocost))
            {
            }
            else
            {
            	ocost="0";
            }
		     //新增订单获取id
		    keyId = this.insert(sql, new Object[] {otype, orderName,GOTO_WAITSTATE, userId, ouserName, ouserMobile,ocost,odesc, oserviceTime, oper,childtype,correspondent,0});
		    if (keyId==-2) {
		    	this.rollBack();
		    	return -2;
			}
		    StringBuffer sbCode = new StringBuffer();
		    
		    sbCode.append("O").append(DateHelper.getToday("yyyyMMdd")).append("U").append(userId).append("T").append(otype).append(keyId);
		    //修改订单编码
		    sql=getSql("orderService.updateCode");
		    this.update(sql,new Object[]{sbCode,keyId});
		    //如果订单金额大于0，等待支付
		    if (ocost!=null&&!"".equals(ocost)&&Float.parseFloat(ocost)>0)
		    {
				Map<String,String> orderMap =new HashMap<String,String>();
				orderMap.put("agreement_id", correspondent);
				orderMap.put("cost", ocost);
				orderMap.put("operid", oper);
				orderMap.put("order_id", String.valueOf(keyId));
		        int resf=financial.orderRent(orderMap, this);//插入财务数据
		    	if (resf!=1) 
		    	{
		    		this.rollBack();
					return -3;
				}
				//修改订单待支付
				sql=getSql("orderService.updateinfos").replace("###", " order_status =? "); 
			    this.update(sql, new Object[]{PAY_WAITSTATE,keyId});
			}
		    //如果图片地址不为空
		    if (upurl!=null&&!"".equals(upurl))
		    {   //上传图片
		    	String urlPath="";
		    	Map<String,String >  newPath = ImageWork(upurl, keyId, request);
				if ( "1".equals(str.get(newPath, "state")))
				{
					urlPath=str.get(newPath, "newPath");
				}
				else
				{
					this.rollBack();
					logger.debug("图片上传失败");
					throw new Exception("网络图片上传失败");
				}
				//修改图片地址
				if (!"".equals(urlPath))
				{  
					sql=getSql("orderService.updateUrl");
					this.update(sql, new Object[]{urlPath,keyId});
				}
			}
		    
		 return 1;	
		}
	});
		return getReturnMap(i);
	}
	public Object dispatch(final HttpServletRequest request,final HttpServletResponse response) 
	{
	 final String oper = this.getUser(request).getId();// 获取操作人
	 final String operName = this.getUser(request).getName();// 获取操作人
	 
     @SuppressWarnings("unchecked")
	 Object i = db.doInTransaction(new Batch<Object>()
	 {
		 @Override
		public Integer execute() throws Exception
		{
			String odesc = req.getAjaxValue(request, "odesc");//订单说明
			String oid = req.getAjaxValue(request, "id");//订单id
			String ocode = req.getAjaxValue(request, "ocode");//订单编码
			String otype=req.getAjaxValue(request, "otype");//订单类型
			String account = req.getAjaxValue(request, "account");//订单说明
			String	sql=getSql("account.getMobile");
			String mobile=this.queryForString(sql,new Object[]{account});//被指派人手机号
			String typename="";
			sql=getSql("orderService.getstatus");
			String status=this.queryForString(sql,new Object[] {oid});
			if (!"4".equals(status)&&!"200".equals(status))
			{
				logger.debug("当前状态："+status);
				this.rollBack();
				return -4;
				//throw new TaskException("当前状态不允许派单!");
			}
			if ("200".equals(status)&&"7".equals(otype))
			{
				logger.debug("当前状态："+status);
				this.rollBack();
				return -4;
			}
			//&&
		    //先发起流程信息
			TaskBean taskBean = new TaskBean();
			taskBean.setCreate_oper(oper);
			String tasktype="";
			switch (Integer.parseInt(otype)) 
			{
			case RANK_ORDER:
				tasktype="3";
				taskBean.setTask_cfg_id("3");
				taskBean.setName("看房订单_"+ocode);
				typename="看房订单_";
				break;
			case WORK_ORDER:
				tasktype="4";
				taskBean.setTask_cfg_id("4");
				taskBean.setName("维修订单_"+ocode);
				typename="维修订单_";
				break;
			case CLEAN_ORDER:
				tasktype="5";
				taskBean.setTask_cfg_id("5");
				taskBean.setName("保洁订单_"+ocode);
				typename="保洁订单_";
				break;
			case WRITE_ORDER:
				tasktype="6";
				taskBean.setTask_cfg_id("6");
				taskBean.setName("投诉订单_"+ocode);
				typename="投诉订单_";
				break;
			case OTHER_ORDER:
				tasktype="7";
				taskBean.setTask_cfg_id("7");
				taskBean.setName("其他订单_"+ocode);
				typename="其他订单_";
				break;
			case OWNER_ORDER:
				tasktype="13";
				taskBean.setTask_cfg_id("13");
				taskBean.setName("业主报修_"+ocode);
				typename="业主报修_";
				break;
			case ADMISSION_ORDER:
				tasktype="9";
				taskBean.setTask_cfg_id("9");
				taskBean.setName("入住服务_"+ocode);
				typename="入住服务_";
				break;
			case LEASEORDER_ORDER:
				tasktype="10";
				taskBean.setTask_cfg_id("10");
				taskBean.setName("退租订单_"+ocode);
				typename="退租订单_";
				break;
			case ROUTINE_CLEANING:
				tasktype="12";
				taskBean.setTask_cfg_id("12");
				taskBean.setName("例行保洁订单_"+ocode);
				typename="例行保洁订单_";
				break;
			}
			taskBean.setResource_id(oid);
			taskBean.setNowOper(account);
			taskBean.setRemark(odesc);
			Map<String,Object> returnMap = createFlow(request, this, taskBean);
			String code=str.get(returnMap, "code");
			Map<String,String> msgMap = new HashMap<String, String>();
			msgMap.put("typename", typename);
			msgMap.put("ocode", ocode);
			msgMap.put("code", code);
			msgMap.put("operName", operName);
			SmsSendMessage.smsSendMessage(mobile, msgMap, SmsSendContants.TASK_WARN);
			//修改订单为执行中
			String	sqlu =getSql("orderService.updateinfos").replace("###", " order_status =? "); 
			this.update(sqlu, new Object[]{ACCEPT_WAITSTATE,oid});
			sqlu =getSql("orderService.insertPush"); 
//			INSERT INTO yc_push_task(title,content,channel_id,client_type,client_title,url,operator_date) SELECT "订单类型待办名称", "短信内容", channel_id, client_type, "任务处理（写死）", 'mobile/task/showDetailFlowDetail.do?step_id='+ encodeURIComponent(step_id)+'&task_id='+task_id, NOW()  FROM yc_user_ts_tab a WHERE a.mobile = ?
			this.update(sqlu, new Object[]{taskBean.getName(),"您有新的任务，请点击进入处理。任务编码："+code+",订单编码："+ocode,"任务处理","mobile/task/showDetailFlowDetail.do?step_id="+str.get(returnMap, "stepTwoId")+"&task_id="+str.get(returnMap, "task_id"),mobile});
			return 1;	 
		}
	 });
     
     logger.debug("参数",i);
 	return getReturnMap(i);
	}
	
	//closeOrder
	
	/**
	 * 关闭订单
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public Object closeOrder(HttpServletRequest request,HttpServletResponse response)
	{
		String status = req.getAjaxValue(request, "status");//订单说明
		if (!"4".equals(status)&&!"200".equals(status))
		{
			logger.debug("当前状态："+status);
			return getReturnMap(-4);
		}
	    String id = req.getAjaxValue(request, "id");
	    String sql = getSql("orderService.updateinfos").replace("###", " order_status =?");
		return getReturnMap(db.update(sql, new Object[] {CLOSE_STATE, id }));
	}

	/**
	 * 撤销支付
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public Object repealOrder(final HttpServletRequest request,final HttpServletResponse response) 
	{
	 final String oper = this.getUser(request).getId();// 获取操作人
     @SuppressWarnings("unchecked")
	 Object i = db.doInTransaction(new Batch<Object>()
	 {
		 @Override
		public Integer execute() throws Exception
		{
		   String id = req.getAjaxValue(request, "id");
		   String sql = getSql("orderService.checktask");
		   int res= this.queryForInt(sql,new Object[]{id});
		   if (res>0) 
		   {
			    this.rollBack();
				logger.debug("该订单无法撤销支付，请在任务中撤销");
				return -4;
			//	throw new TaskException("该订单无法撤销支付，请在任务中撤销");
		   }
		   Map<String,String> orderMap =new HashMap<String,String>();
			orderMap.put("order_id",id);
		    int resf=financial.repealOrder(orderMap, this);
		    if (resf!=1) 
		    {
		    	throw new TaskException("财务未找到");
		    }
		     sql = getSql("orderService.updateinfos").replace("###", " order_cost=0, order_status =? ");
		    this.update(sql, new Object[] {GOTO_WAITSTATE,id});
			return 1;	 
		}
	 });
     return getReturnMap(i);
	}
	/**
	 * 订单状态数据报表
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public Object orderReport(HttpServletRequest request, HttpServletResponse response)
	{
		String sql = getSql("orderService.getReport");
		List<String> params = new ArrayList<String>();
		Map<String, Object> returnMap = getReturnMap("1");
		returnMap.put("list", JSONArray.fromObject(db.queryForList(sql,params.toArray())));
		return returnMap;
	}
	
	/**
	 * 订单查询当前财务中的租赁合约ID
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public Object getFinancial (HttpServletRequest request, HttpServletResponse response)
	{
		String id = req.getAjaxValue(request, "id");
		String sql =getSql("pressRental.getFinancial");
		Map<String, Object> returnMap = db.queryForMap(sql,new Object[] {id});
		return returnMap;
	}
	/**
	 * 订单类型数据报表
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 */
	public Object typeReport(HttpServletRequest request,
			HttpServletResponse response) {
		String sql = getSql("orderService.typeReport");
		List<String> params = new ArrayList<String>();
		Map<String, Object> returnMap = getReturnMap("1");
		returnMap.put("list", JSONArray.fromObject(db.queryForList(sql,params.toArray())));
		return returnMap;
	}
	
	/**
	 * 图片工作
	 * @author 刘飞
	 * @param request
	 * @param response
	 * @return
	 * */
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
					if (pathArray[j].contains(SystemConfig.getValue("FTP_HTTP")+ SystemConfig.getValue("FTP_ORDER_HTTP_PATH"))) 
					{
						newPath +="/"+pathArray[j].replace("upload/tmp/", "").replace(SystemConfig.getValue("FTP_HTTP")+ SystemConfig.getValue("FTP_ORDER_HTTP_PATH"),"")+ ",";
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
	
	
	/**
	 * 新增退租订单 ——外部接口
	 * @param request
	 * @param rankHouse
	 * @return 
	 */
	public Object interfacesCreateOrder(final HttpServletRequest request, final OrderInterfaces orderInterfaces, final RankHouse rankHouse) {
		final JSONObject result = YCBizMsgCrypt.decryptJsonObjectParams(request,Constants.TOKEN);//解密外部传值
		@SuppressWarnings("unchecked")
		Object obj = db.doInTransaction(new Batch(){
			@Override
			public Object execute() throws Exception {
				
				if(result == null)
				{
					return 0;
				}
				
				String correspondent = result.getString("correspondent");
				String oserviceTime =result.getString("oserviceTime");
				String childtype = result.getString("childtype");
				if (correspondent!=null&&!"".equals(correspondent.trim())&&oserviceTime!=null&&!"".equals(oserviceTime.trim())&&childtype!=null&&!"".equals(childtype.trim()))
				{
					Map params = new HashMap();
					params.put("correspondent", correspondent);
					params.put("oserviceTime", oserviceTime);
					params.put("childtype", childtype);
					int exc = orderInterfaces.orderCreate(params, this);
					return exc;
				} 
				else
				{
					return 0;
				}
			}
		});
		/*db.doInTransaction(new Batch<Object>()
		{
			@Override
			public Object execute() throws Exception
			{
				String correspondent = result.getString("correspondent");
				Map param = new HashMap<>();
				param.put("agreementId", correspondent);
				int res = rankHouse.updateRankAgreementStatus(param, this);
				if(res != 1) 
				{
					Map<String,String> exceptionMap = new HashMap<>();
					exceptionMap.put("function", "微信支付更新合约状态");
					exceptionMap.put("describe", "回调财务接口报错，返回"+res+"合约编码:"+correspondent);
					SmsSendMessage.smsSendMessage("15371688388", exceptionMap, 13);
				}
				return res;
			}
		});*/
		if(obj == null)
		{
			return 0;
		}
		return (int) obj;
	}
}
