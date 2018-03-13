package com.ycdc.core.base;

import java.io.Console;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.BorderLineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;

import com.alibaba.fastjson.JSON;
import com.room1000.workorder.dao.WorkOrderDtoMapper;
import com.room1000.workorder.dto.WorkOrderDto;
import com.room1000.workorder.dto.WorkOrderHisDto;
import com.room1000.workorder.factory.SubOrderFactory;
import com.room1000.workorder.service.ISubOrderService;
import com.ycdc.bean.CglibBean;
import com.ycdc.nbms.coupon.dao.CouponDao;
import com.ycdc.util.page.Page;
import com.ycdc.util.page.PageResultDealInterface;

import pccom.common.util.Constants;
import pccom.common.util.DBHelperSpring;
import pccom.common.util.RequestHelper;
import pccom.common.util.StringHelper;
import pccom.web.beans.Login;
import pccom.web.beans.User;
import pccom.web.listener.SessionManage;

/**
 * 服务组件基类
 * 
 * @author chang
 * @createDate Mar 8, 2013
 * @description
 */
public class BaseService {

	public final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	private static SimpleDateFormat sdf = null;
	private static Calendar cale = null;
	public DBHelperSpring db;// 数据操作
    public Constants cons ;
	public RequestHelper req = new RequestHelper();
	public final static int UPLOAD_TO_SERVER = 1;
	public final static String SPLIT_SIGN = ",";
	public final static String EXCEL_BB = "2003";
	public SqlSessionFactory sqlSessionFactory;
	
	public SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	@Resource(name="sqlSessionFactory")
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}

	@Resource(name="dbHelper")
	public void setDb(DBHelperSpring db) {
		this.db = db;
	}
	
	public DBHelperSpring getDb() {
		return db;
	}

	public StringHelper str = new StringHelper();

	/**
	 * 获取session中的用户信息
	 * @author 雷杨
	 * @param request
	 * @return
	 */
	public User getUser(HttpServletRequest request){
		String value = getCookie(request,Constants.COOKIE_CLIENT_ID);
		String username = getCookie(request,"username");
		Object o = SessionManage.getSession(username);
		
		//logger.debug(SessionManage.getSessionMap());
		if(o == null){
			User user = new User();
			/*user.setRoles("22,27,26.28,1");
			user.setId("82");
			user.setOrgId("1,2,3");
			user.setIs_mobile("1");
			request.getSession().setAttribute("user", user);*/
			return user;
		}else{
			Login login = (Login)o;
			return login.getUser();
		}
	}
	
	/**
	 * 获取cookie
	 * @author 雷杨
	 * @param request
	 * @param name
	 * @return
	 */
	public String getCookie(HttpServletRequest request,String name){
		Cookie[] cookies = request.getCookies();
		//logger.debug("----------"+request.getCookies());
		if(cookies == null){
			logger.debug("cookies:"+name+"wei kong ");
			return "";
		}
		for(int i=0;i<cookies.length;i++){
			if(name.equals(cookies[i].getName())){
				return cookies[i].getValue();
			}
		}
		return "";
	}
	
	
	public String getFmtDate(String pattern) {
		sdf = new SimpleDateFormat(pattern);
		cale = Calendar.getInstance();
		return sdf.format(cale.getTime());
	}

	public String getValue(HttpServletRequest request, String paramName) {
		String obj = request.getParameter(paramName);
		if (obj == null) {
			obj = "";
		}
		return obj.trim();
	}

	public String getAjaxValue(HttpServletRequest request, String paramName) {
		String obj = request.getParameter(paramName);
		if (obj == null) {
			obj = "";
		}
		try {
			obj = java.net.URLDecoder.decode(obj, "UTF-8");
		} catch (Exception e) {
			obj = "";
		}
		return obj.trim();
	}

	public String[] getValues(HttpServletRequest request, String paramName) {
		String[] value;
		value = request.getParameterValues(paramName);
		if (value == null)
			value = new String[] {};
		return value;
	}

	public String getValuesString(HttpServletRequest request, String paramName) {
		return getValuesString(request, paramName, ",");
	}

	public String getValuesString(HttpServletRequest request, String paramName,
			String delims) {
		String temp = "";
		String[] values = request.getParameterValues(paramName);
		if (values == null) {
			return "";
		} else {
			for (int i = 0; i < values.length; i++) {
				if (i == values.length - 1) {
					temp += values[i].trim();
				} else {
					temp += values[i].trim() + delims;
				}
			}
		}
		return temp;
	}

	public String[] getAjaxValues(HttpServletRequest request, String paramName) {
		String[] value, newValue;
		String tmp = "";
		value = request.getParameterValues(paramName);
		if (value == null)
			value = new String[] {};
		newValue = value;
		if (value.length > 0) {
			newValue = new String[value.length];

			try {
				for (int i = 0; i < value.length; i++) {
					tmp = value[i];
					tmp = java.net.URLDecoder.decode(tmp, "UTF-8");
					newValue[i] = tmp;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		}
		return newValue;
	}

	public String getAjaxValuesString(HttpServletRequest request,
			String paramName) {
		return getAjaxValuesString(request, paramName, ",");
	}

	public String getAjaxValuesString(HttpServletRequest request,
			String paramName, String delims) {
		String temp = "";
		String[] values = getAjaxValues(request, paramName);
		if (values == null) {
			return null;
		} else {
			for (int i = 0; i < values.length; i++) {
				if (i == values.length - 1) {
					temp += values[i].trim();
				} else {
					temp += values[i].trim() + delims;
				}
			}
		}
		return temp;
	}

	/**
	 * @description: ��ȡϵͳ��Ŀ¼��ַ
	 * @author ���� 2012-12-27
	 * @param request
	 * @return
	 */
	public String getRealPath(HttpServletRequest request) {
		return request.getSession().getServletContext().getRealPath("/");
	}

	/**
	 * ��ȡ��Ŀ¼����ʵ·��
	 * 
	 * @param request
	 * @return
	 */
	public String getWebRootRealPath() {
		String rootPath = this.getClass().getResource("").getPath();
		rootPath = rootPath.substring(0, rootPath.indexOf("WEB-INF") - 1);

		return rootPath;
	}

	/**
	 * 
	 * @param date
	 *            日期 接受 yyyy-MM-dd,yyyy-MM,yyyy
	 * @param 平移最小单位
	 *            - 向前 + 向后
	 * @param patten
	 * @return
	 */
	public static String[] getFmtDateList(String date, int move) {
		String[] dates = date.split("-");
		cale = Calendar.getInstance();
		int length = Math.abs(move);
		String[] ret = new String[length];
		switch (dates.length) {
		case 1:// yyyy
			sdf = new SimpleDateFormat("yyyy");
			cale.set(Integer.valueOf(dates[0]), 0, 1);
			for (int i = 0; i < length; i++) {
				ret[i] = sdf.format(cale.getTime());
				if (move < 0) {
					cale.set(Calendar.YEAR, cale.get(Calendar.YEAR) - 1);
				} else {
					cale.set(Calendar.YEAR, cale.get(Calendar.YEAR) + 1);
				}
			}
			break;
		case 2:// yyyy-MM
			sdf = new SimpleDateFormat("yyyy-MM");
			cale.set(Integer.valueOf(dates[0]), Integer.valueOf(dates[1]) - 1,
					1);
			for (int i = 0; i < length; i++) {
				ret[i] = sdf.format(cale.getTime());
				if (move < 0) {
					cale.set(Calendar.MONTH, cale.get(Calendar.MONTH) - 1);
				} else {
					cale.set(Calendar.MONTH, cale.get(Calendar.MONTH) + 1);
				}
				// logger.debug(cale.getTime());
			}
			break;
		case 3:// yyyy-MM-dd
			sdf = new SimpleDateFormat("yyyy-MM-dd");
			cale.set(Integer.valueOf(dates[0]), Integer.valueOf(dates[1]) - 1,
					Integer.valueOf(dates[2]));
			for (int i = 0; i < length; i++) {
				ret[i] = sdf.format(cale.getTime());
				if (move < 0) {
					cale.set(Calendar.DAY_OF_MONTH,
							cale.get(Calendar.DAY_OF_MONTH) - 1);
				} else {
					cale.set(Calendar.DAY_OF_MONTH,
							cale.get(Calendar.DAY_OF_MONTH) + 1);
				}
			}
			break;
		default:
			break;
		}
		if (move < 0) {
			ret = getArray(ret);
		}
		return ret;
	}

	public static void main(String[] args) {
		String[] ret = getFmtDateList("2016-04-04", 5);
		for (int i = 0; i < ret.length; i++) {
			//logger.debug(ret[i]);
		}
	}

	/**
	 * 处理数组反转
	 * 
	 * @param array
	 * @return
	 */
	public static String[] getArray(String[] array) {
		String t[] = new String[array.length]; // 开辟一个新的数组
		int count = t.length - 1;
		for (int x = 0; x < t.length; x++) {
			t[count] = array[x]; // 数组反转
			count--;
		}
		return t;
	}

	/**
	 * 获取返回的字符串
	 * @author 雷杨
	 * @param value
	 * @return
	 */
	public Map<String,Object> getReturnMap(Object value){
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("state", value);
		return returnMap;
	}
	
	public Map<String,Object> getReturnMap(Object value,Object msg){
		Map<String,Object> returnMap = new HashMap<String, Object>();
		returnMap.put("state", value);
		returnMap.put("msg", msg);
		return returnMap;
	}
	
	/**
	 * 固定的将page参数放到最后，如需要动态改变page放置位置，请调用下一个getPageList 
	 * @param request
	 * @param response
	 * @param obj
	 * @param methodName
	 * @param params
	 */
	public void getPageList(HttpServletRequest request,HttpServletResponse response,Object obj,String methodName,Object... params){
		Page<Map<String,Object>> page = new Page<Map<String,Object>>();
		if(params.length == 0){
			getPageList(request, response, page, obj, methodName, page);
		}else{
			Object[] paramsObj = new Object[params.length+1];
			System.arraycopy(params, 0, paramsObj, 0, params.length);
			paramsObj[params.length] = page;
			getPageList(request, response, page, obj, methodName, paramsObj);
		}
	}
	
	public void getPageMapList(HttpServletRequest request,HttpServletResponse response,Object obj,String methodName,PageResultDealInterface pageResultDealInterface){
		this.getPageMapList(request, response, obj, methodName, new HashMap<String,Object>(),pageResultDealInterface);
	}
	
	public void getPageMapList(HttpServletRequest request,HttpServletResponse response,Object obj,String methodName){
		this.getPageMapList(request, response, obj, methodName, new HashMap<String,Object>(),null);
	}
	
	public void getPageMapList(HttpServletRequest request,HttpServletResponse response,Object obj,String methodName,Map<String,Object> params){
		this.getPageMapList(request, response, obj, methodName, params,null);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @param obj  dao对象
	 * @param methodName dao对象中需要执行的方法
	 * @param agr  传入的参数 跟dao对象中需要执行的方法 参数对应
	 */
	public void getPageMapList(HttpServletRequest request,HttpServletResponse response,Object obj,String methodName,Map<String,Object> params,PageResultDealInterface pageResultDealInterface){
		Page<?> page = new Page();
		params.put("page_", page);
		String sSearch = req.getAjaxValue(request, "sSearch");//搜索条件内容
		String iDisplayLength = req.getAjaxValue(request, "iDisplayLength");//每页显示的条数
		String iDisplayStart = req.getAjaxValue(request, "iDisplayStart");//从第几行开始
		String iSortCol = req.getAjaxValue(request, "iSortCol_0");//排序字段
		String sSortDir_0 = req.getAjaxValue(request, "sSortDir_0");//正续还是倒序
		String sizelength = req.getAjaxValue(request, "sizelength");//存在的数据总长度
		String isCxSeach = req.getAjaxValue(request, "isCxSeach");//是否需要重新计算长度
		String isDc = req.getAjaxValue(request, "isDc");
		page.setSizelength(sizelength);
		page.setiDisplayLength(iDisplayLength);
		page.setiDisplayStart(iDisplayStart);
//		logger.debug("iSortCol:"+iSortCol);
		page.setsSortDir_0(sSortDir_0);
		page.setIsCxSeach(isCxSeach);
		//设置基本参数信息
		if(!"".equals(iSortCol) && iSortCol != null){
			page.setbSortable(req.getAjaxValue(request, "bSortable_"+iSortCol));
			page.setiSortCol(iSortCol);
			page.setmDataProp(req.getAjaxValue(request, "mDataProp_"+iSortCol));
		}
		if(!"".equals(isDc)){//导出操作
			page.setExp(true);
		}
		if(!page.isExp()){//不是导出
			List<Map<String, Object>> resultList = queryList(obj, methodName, params);
			if(pageResultDealInterface != null){
				resultList = pageResultDealInterface.deal(resultList);
			}
			Map<String,Object> returnMap = getReturnMap(1);
			returnMap.put("data", JSONArray.fromObject(resultList));
			returnMap.put("sEcho", req.getAjaxValue(request, "sEcho"));
			returnMap.put("iTotalRecords", page.getTotalRecord());
			returnMap.put("iTotalDisplayRecords", page.getTotalRecord());
			returnMap.put("iDisplayLength", iDisplayLength);
			returnMap.put("iDisplayStart", iDisplayStart);
			try {
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(JSONObject.fromObject(returnMap));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{//导出
			List<Map<String, Object>> list = queryList(obj, methodName, params);
			if(pageResultDealInterface != null){
				list = pageResultDealInterface.deal(list);
			}
			String coln = req.getAjaxValue(request, "colum");
			String typeKey = req.getAjaxValue(request, "type");
			JSONArray objJson = JSONArray.fromObject(coln);
			// 导出excel中添加历史步骤信息
			if (obj instanceof WorkOrderDtoMapper) {
				String subOrderStatus = "ABCPDEFGMLNQRSTUVWHI";
				switch (typeKey)
				{
				case "A": // 退租订单
					subOrderStatus = "ABVCPUDMQLRNSI";
					break;
				case "B": // 保洁订单
					subOrderStatus = "AKBVCPDHI";
					break;
				case "C": // 投诉订单
					subOrderStatus = "ABVCPDHI";
					break;
				case "D": // 看房订单
					subOrderStatus = "ABVCPDHI";
					break;
				case "E": // 入住问题订单
					subOrderStatus = "ABVCPDHI";
					break;
				case "F": // 其他订单
					subOrderStatus = "ABVCPDHI";
					break;
				case "G": // 业主维修订单
					subOrderStatus = "ABVCPDKHI";
					break;
				case "H": // 维修订单
					subOrderStatus = "ABVCPDKHI";
					break;
				case "I": // 例行保洁订单
					subOrderStatus = "ABVCPDHI";
					break;
				case "J": // 经纪人签约订单
					subOrderStatus = "ABVCPDHI";
					break;
				case "L": // 支付订单
					subOrderStatus = "ABVCPDHI";
					break;
				case "M": // 经纪人申请审批订单
					subOrderStatus = "ABVCPDHI";
					break;

				default:
					break;
				}
				Map<String, String> subOrderNames = new HashMap<String, String>(){{
					put("A","订单创建");
					put("B","指派订单");
					put("C","接单或重新指派");
					put("P","重新指派或接单");
					put("D","员工处理");
					put("E","客服回访");
					put("F","客服回访待追踪");
					put("G","客服回访重新指派");
					put("J","请求支付中");
					put("K","客户支付");
					put("M","租务审核");
					put("L","高管审批");
					put("N","财务审核");
					put("O","财务数据生成");
					put("Q","租务审核未通过");
					put("R","高管审批未通过");
					put("S","财务审核未通过");
					put("T","公司退款");
					put("U","房源释放");
					put("V","订单修改");
					put("W","订单审核");
					put("I","订单关闭");
					put("H","评价");
				}};
				for (int i = 0; i < subOrderStatus.length(); i++)
				{
					String type = subOrderStatus.substring(i, i+1).toUpperCase();
//					JSONObject tNameJson = new JSONObject();
//					tNameJson.put("title", subOrderNames.get(type));
//					tNameJson.put("name", type+"Name");
					JSONObject tDealerNameJson = new JSONObject();
					tDealerNameJson.put("title", subOrderNames.get(type)+"处理人");
					tDealerNameJson.put("name", type+"DealerName");
					JSONObject tDealTime = new JSONObject();
					tDealTime.put("title", subOrderNames.get(type)+"处理时间");
					tDealTime.put("name", type+"DealTime");
					JSONObject tDuration = new JSONObject();
					tDuration.put("title", subOrderNames.get(type)+"历时");
					tDuration.put("name", type+"Duration");
					if (!"I".equals(type)) {
						JSONObject tCommentsJson = new JSONObject();
						tCommentsJson.put("title", subOrderNames.get(type)+"备注");
						tCommentsJson.put("name", type+"Comments");
						objJson.add(tCommentsJson);
					}
//					objJson.add(tNameJson);
					objJson.add(tDealerNameJson);
					objJson.add(tDealTime);
					objJson.add(tDuration);
					
				}
				for (Iterator<Map<String, Object>> iterator = list.iterator(); iterator.hasNext();)
				{
					Map<String, Object> map = (Map<String, Object>) iterator.next();
					String types = String.valueOf(map.get("type"));
					if (types.equals("N"))
					{
						types = "A";
					}
					ISubOrderService subOrderService = SubOrderFactory.getSubOrderService(types);
					Object refId = map.get("ref_id");
					if (refId == null || "".equals(refId)) {
						continue;
					}
					List<Object> historyObjs = subOrderService.getHis(Long.valueOf(String.valueOf(refId)));
					String preType = "A";
					if (historyObjs != null && historyObjs.size() > 0) {
						for (int i = 0; i < subOrderStatus.length(); i++)
						{
							String type = subOrderStatus.substring(i, i+1).toUpperCase();
							map.put(type+"Name", subOrderNames.get(type));
							for (Iterator<Object> iterator2 = historyObjs.iterator(); iterator2.hasNext();)
							{
								Object hisObj = iterator2.next();
								Class<? extends Object> hisClass = hisObj.getClass();
								try
								{
									if (type.equals(((String)hisClass.getMethod("getState").invoke(hisObj)).toUpperCase())) {
										// 处理人姓名
										String dealerName = (String)hisClass.getMethod("getUpdatePersonName").invoke(hisObj);
										map.put(type+"DealerName", dealerName);
										// 处理时间点
										SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
										Date time = (Date)hisClass.getMethod("getUpdateDate").invoke(hisObj);
										String dealTime = sdf.format(time);
										map.put(type+"DealTime", dealTime);
										if (i > 0 ) {
											// 处理用时
											String preDealTime = (String)map.get(preType+"DealTime");
											if (preDealTime != null && !"".equals(preDealTime)) {
												long DValue = sdf.parse(dealTime).getTime() - sdf.parse(preDealTime).getTime();
												long hour = DValue/(3600*1000);
												long minute = (DValue - hour*3600*1000)/(60*1000);
												long seconds = (DValue - hour*3600*1000 - minute*60*1000)/1000;
												String dealDuration = String.valueOf(hour)+"小时"+String.valueOf(minute)+"分"+String.valueOf(seconds)+"秒";
												map.put(type+"Duration", dealDuration);
											}
										}
										String comments = "";
										// 处理备注
										if (hisClass.getMethod("getSubOrderValueList").invoke(hisObj) != null) {
											List subOrderValueList = (ArrayList<Map<String, Object>>)hisClass.getMethod("getSubOrderValueList").invoke(hisObj);
											for (Iterator<Map<String, Object>> iterator3 = subOrderValueList.iterator(); iterator3.hasNext();)
											{
												Object object = iterator3.next();
												if (((String)object.getClass().getMethod("getAttrPath").invoke(object)).endsWith(".COMMENTS") || 
														((String)object.getClass().getMethod("getAttrPath").invoke(object)).endsWith(".HOUSE_INSPECTION_COMMENTS")) {
													comments = (String)object.getClass().getMethod("getTextInput").invoke(object);
				                                    break;
				                                }
											}
											if (map.get(preType+"Comments") != null && "" != map.get(preType+"Comments")) {
												comments = map.get(preType+"Comments")+","+comments;
											}
											map.put(type+"Comments", comments);
										}
										preType = type;
									}
								} catch (Exception e)
								{
									// TODO: handle exception
									logger.error("",e);
								}
							}
						}
					}
				}
			}
			//String[][] excelParams = {{"测试","aaa","1"},{"测试1","aaa1","1"}};
			System.out.println(JSON.toJSONString(list));
			String[][] excelParams = new String[objJson.size()][3];
			for(int i=0;i<objJson.size();i++){
				excelParams[i][0] = objJson.getJSONObject(i).getString("title");
				excelParams[i][1] = objJson.getJSONObject(i).getString("name");
			}
			String fileName = "";
			try{
				String expname = req.getAjaxValue(request, "expname");
				String istz = req.getAjaxValue(request, "istz_");
//				logger.debug("expname:"+expname);
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
//					logger.debug(excle);
					File file1 = new File(request.getRealPath("/")+excle);
					if(!file1.exists()){
						file1.createNewFile();
					}
//					logger.debug(excle);
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
//	            logger.debug("??????????????????111??导出???????????????????????????");
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
//	            logger.debug("????????1111111111111111111111??????");
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
		}
		
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @param page
	 * @param obj  dao对象
	 * @param methodName dao对象中需要执行的方法
	 * @param agr  传入的参数 跟dao对象中需要执行的方法 参数对应
	 */
	private void getPageList(HttpServletRequest request,HttpServletResponse response,Page<?> page,Object obj,String methodName,Object... params){
		String sSearch = req.getAjaxValue(request, "sSearch");//搜索条件内容
		String iDisplayLength = req.getAjaxValue(request, "iDisplayLength");//每页显示的条数
		String iDisplayStart = req.getAjaxValue(request, "iDisplayStart");//从第几行开始
		String iSortCol = req.getAjaxValue(request, "iSortCol_0");//排序字段
		String sSortDir_0 = req.getAjaxValue(request, "sSortDir_0");//正续还是倒序
		String sizelength = req.getAjaxValue(request, "sizelength");//存在的数据总长度
		String isCxSeach = req.getAjaxValue(request, "isCxSeach");//是否需要重新计算长度
		String isDc = req.getAjaxValue(request, "isDc");
		page.setSizelength(sizelength);
		page.setiDisplayLength(iDisplayLength);
		page.setiDisplayStart(iDisplayStart);
//		logger.debug("iSortCol:"+iSortCol);
		//设置基本参数信息
		if(!"".equals(iSortCol) && iSortCol != null){
			page.setbSortable(req.getAjaxValue(request, "bSortable_"+iSortCol));
			page.setiSortCol(iSortCol);
			page.setmDataProp(req.getAjaxValue(request, "mDataProp_"+iSortCol));
		}
		if(!"".equals(isDc)){//导出操作
			page.setExp(true);
		}
		
		if(!page.isExp()){//不是导出
//			logger.debug("----:"+page.toString());
			List<Map<String, Object>> resultList = getMethodList(obj, methodName, params);
			Map<String,Object> returnMap = getReturnMap(1);
			returnMap.put("data", JSONArray.fromObject(resultList));
			returnMap.put("sEcho", req.getAjaxValue(request, "sEcho"));
			returnMap.put("iTotalRecords", page.getTotalRecord());
			returnMap.put("iTotalDisplayRecords", page.getTotalRecord());
			returnMap.put("iDisplayLength", iDisplayLength);
			returnMap.put("iDisplayStart", iDisplayStart);
			try {
				response.setCharacterEncoding("UTF-8");
				response.getWriter().print(JSONObject.fromObject(returnMap));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else{//导出
			List<Map<String, Object>> list = getMethodList(obj, methodName, params);
			String coln = req.getAjaxValue(request, "colum");
			JSONArray objJson = JSONArray.fromObject(coln);
			//String[][] excelParams = {{"测试","aaa","1"},{"测试1","aaa1","1"}};
			String[][] excelParams = new String[objJson.size()][3];
			for(int i=0;i<objJson.size();i++){
				excelParams[i][0] = objJson.getJSONObject(i).getString("title");
				excelParams[i][1] = objJson.getJSONObject(i).getString("name");
			}
			String fileName = "";
			try{
				String expname = req.getAjaxValue(request, "expname");
				String istz = req.getAjaxValue(request, "istz_");
//				logger.debug("expname:"+expname);
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
//					logger.debug(fileName);
					excle = "/upload/excel/"+fileName+".xls";
//					logger.debug(excle);
					File file1 = new File(request.getRealPath("/")+excle);
					if(!file1.exists()){
						file1.createNewFile();
					}
//					logger.debug(excle);
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
//	            logger.debug("??????????????????111??导出???????????????????????????");
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
//	            logger.debug("????????1111111111111111111111??????");
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
		}
		
	}
	
	/**
	 * 获取查询结果信息
	 * @param obj
	 * @param methodName
	 * @param agr
	 * @return
	 */
	public List<Map<String, Object>> getMethodList(Object obj,String methodName,Object... params){
		try {
			Class[] cc = new Class[params.length];
			for(int i=0;i<params.length;i++){
				cc[i] = params[i].getClass();
			}
			
			Object o = obj.getClass().getMethod(methodName,cc).invoke(obj,params);
			return (List<Map<String, Object>>)o;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<Map<String, Object>>();
	}
	
	/**
	 * 插入数据库并返回对应的 自增序列  需要抛出异常，使用在事物处理当中
	 * 
	 * @param obj dao对象
	 * @param methodName  需要执行的方法名称
	 * @param keyId 那个字段是自增序列
	 * @param paramsMap 需要传入的参数值 
	 * @return
	 * @throws Exception 
	 */
	public int insertExc(Object obj,String methodName,String keyId,Map paramsMap) throws Exception{
		/**
		 * 创建对应的javabean返回值实例
		 */
		HashMap propertyMap = new HashMap();
		propertyMap.put(keyId, Class.forName("java.lang.Integer"));
		propertyMap.put("map", Class.forName("java.util.Map"));
		CglibBean bean = new CglibBean(propertyMap);
		bean.setValue("map",paramsMap);
		Object object = bean.getObject();
		
		Object o = obj.getClass().getMethod(methodName,Object.class).invoke(obj,object);
		
//		Class c = obj.getClass();
//		Method[] method = c.getMethods();
//		for(Method m:method){
//			if(m.getName().equals(methodName)){
//				Object o = m.invoke(obj,object);
//			}
//		}
		logger.debug("结果",o);
//		
		Object result = bean.getValue(keyId);
		if(result == null){
			return -1;
		}else{
			return (int)bean.getValue(keyId);
		}
	}
	
	/**
	 * 插入数据库并返回对应的 自增序列  不需要进行抛出异常，用在对应的 不需要事物进行连接处理的
	 * 
	 * @param obj dao对象
	 * @param methodName  需要执行的方法名称
	 * @param keyId 那个字段是自增序列
	 * @param paramsMap 需要传入的参数值 
	 * @return
	 * @throws Exception 
	 */
	public int insert(Object obj,String methodName,String keyId,Map paramsMap){
		try{
			/**
			 * 创建对应的javabean返回值实例
			 */
			HashMap propertyMap = new HashMap();
			propertyMap.put(keyId, Class.forName("java.lang.Integer"));
			propertyMap.put("map", Class.forName("java.util.Map"));
			CglibBean bean = new CglibBean(propertyMap);
			bean.setValue("map",paramsMap);
			Object object = bean.getObject();
			
			Object o = obj.getClass().getMethod(methodName,Object.class).invoke(obj,object);
			
//			Class c = obj.getClass();
//			Method[] method = c.getMethods();
//			for(Method m:method){
//				if(m.getName().equals(methodName)){
//					Object o = m.invoke(obj,object);
//				}
//			}
//			
			Object result = bean.getValue(keyId);
			if(result == null){
				return -1;
			}else{
				return (int)bean.getValue(keyId);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 更新
	 * 
	 * @param obj dao对象
	 * @param methodName  需要执行的方法名称
	 * @param keyId 那个字段是自增序列
	 * @param paramsMap 需要传入的参数值 
	 * @return
	 * @throws Exception 
	 */
	public int update(Object obj,String methodName,Map paramsMap) {
		try{
			return updateExc(obj, methodName,  paramsMap);
		}catch(Exception e){
			e.printStackTrace();
		}
		return -1;
	}
	
	/**
	 * 更新
	 * 
	 * @param obj dao对象
	 * @param methodName  需要执行的方法名称
	 * @param keyId 那个字段是自增序列
	 * @param paramsMap 需要传入的参数值 
	 * @return
	 * @throws Exception 
	 */
	public int updateExc(Object obj,String methodName,Map paramsMap) throws Exception{
		/**
		 * 创建对应的javabean返回值实例
		 */
		HashMap propertyMap = new HashMap();
		propertyMap.put("map", Class.forName("java.util.Map"));
		CglibBean bean = new CglibBean(propertyMap);
		bean.setValue("map",paramsMap);
		Object object = bean.getObject();
		
//		Class c = obj.getClass();
		Object o = obj.getClass().getMethod(methodName,Object.class).invoke(obj,object);
//		Method[] method = c.getMethods();
//		Object o = null;
//		for(Method m:method){
//			if(m.getName().equals(methodName)){
//				o = m.invoke(obj,object);
//			}
//		}
		if(o != null){
			return (int)o;
		}
		return -1;
	}
	
	/**
	 * 执行数据库 查询语句
	 * @param obj
	 * @param methodName
	 * @param paramsMap
	 * @return
	 */
	public List<Map<String, Object>> queryList(Object obj,String methodName,Map paramsMap){
		try{
			return queryListExc(obj, methodName, paramsMap);
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ArrayList<Map<String, Object>>();
	}
	
	/**
	 * 执行数据库 查询语句
	 * @param obj
	 * @param methodName
	 * @param paramsMap
	 * @return
	 * @throws Exception 
	 */
	public List<Map<String, Object>> queryListExc(Object obj,String methodName,Map paramsMap) throws Exception{
		/**
		 * 创建对应的javabean返回值实例
		 */
		HashMap propertyMap = new HashMap();
		propertyMap.put("map", Class.forName("java.util.Map"));
		CglibBean bean = new CglibBean(propertyMap);
		bean.setValue("map",paramsMap);
		Object object = bean.getObject();
		
		Class c = obj.getClass();
//		Method[] method = c.getMethods();
		
		Object o = c.getMethod(methodName,Object.class).invoke(obj,object);
//		Object o = null;
//		for(Method m:method){
////			logger.debug(m.getName()+"--"+methodName);
//			if(m.getName().equals(methodName)){
//				o = m.invoke(obj,object);
//			}
//		}
		if(o == null){
			return new ArrayList<Map<String, Object>>();
		}else{
			return (List<Map<String, Object>>)o;
		}
	}
	
	/**
	 * 执行数据库 查询语句
	 * @param obj
	 * @param methodName
	 * @param paramsMap
	 * @return
	 */
	public Map<String, Object> queryMap(Object obj,String methodName,Map paramsMap){
		try{
			return queryMapExc(obj, methodName, paramsMap);
		}catch(Exception e){
			e.printStackTrace();
		}
		return new HashMap<String, Object>();
	}
	
	/**
	 * 执行数据库 查询语句
	 * @param obj
	 * @param methodName
	 * @param paramsMap
	 * @return
	 * @throws Exception 
	 */
	public Map<String, Object> queryMapExc(Object obj,String methodName,Map paramsMap) throws Exception{
		/**
		 * 创建对应的javabean返回值实例
		 */
		HashMap propertyMap = new HashMap();
		propertyMap.put("map", Class.forName("java.util.Map"));
		CglibBean bean = new CglibBean(propertyMap);
		bean.setValue("map",paramsMap);
		Object object = bean.getObject();
		
		Class c = obj.getClass();
		Object o = c.getMethod(methodName,Object.class).invoke(obj,object);
//		Method[] method = c.getMethods();
//		Object o = null;
//		for(Method m:method){
//			if(m.getName().equals(methodName)){
//				o = m.invoke(obj,object);
//			}
//		}
		if(o == null){
			return new HashMap<String, Object>();
		}else{
			return (Map<String, Object>)o;
		}
	}
	
	/**
	 * 执行数据库 查询语句
	 * @param obj
	 * @param methodName
	 * @param paramsMap
	 * @return
	 */
	public String queryString(Object obj,String methodName,Map paramsMap){
		try{
			return queryStringExc(obj, methodName, paramsMap);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 执行数据库 查询语句
	 * @param obj
	 * @param methodName
	 * @param paramsMap
	 * @return
	 * @throws Exception 
	 */
	public String queryStringExc(Object obj,String methodName,Map paramsMap) throws Exception{
		/**
		 * 创建对应的javabean返回值实例
		 */
		HashMap propertyMap = new HashMap();
		propertyMap.put("map", Class.forName("java.util.Map"));
		CglibBean bean = new CglibBean(propertyMap);
		bean.setValue("map",paramsMap);
		Object object = bean.getObject();
		
		Class c = obj.getClass();
		Object o = c.getMethod(methodName,Object.class).invoke(obj,object);
//		Method[] method = c.getMethods();
//		Object o = null;
//		for(Method m:method){
//			if(m.getName().equals(methodName)){
//				o = m.invoke(obj,object);
//			}
//		}
		if(o == null){
			return "";
		}else{
			return (String)o;
		}
	}
	
	
	
}
