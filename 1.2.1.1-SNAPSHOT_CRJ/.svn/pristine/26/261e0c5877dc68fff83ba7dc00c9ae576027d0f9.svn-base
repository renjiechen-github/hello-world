package pccom.web.server;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.MDC;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.format.BorderLineStyle;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import pccom.common.SQLconfig;
import pccom.common.util.BatchSql;
import pccom.common.util.Constants;
import pccom.common.util.DBHelperSpring;
import pccom.common.util.DateHelper;
import pccom.common.util.FileHelper;
import pccom.common.util.HssfHelper;
import pccom.common.util.ProcHelper;
import pccom.common.util.RequestHelper;
import pccom.common.util.SignUtil;
import pccom.common.util.SmClient;
import pccom.common.util.StringHelper;
import pccom.common.util.XssfHelper;
import pccom.web.beans.Login;
import pccom.web.beans.User;
import pccom.web.listener.SessionManage;


/**
 * 服务组件基类
 * @author chang
 * @createDate Mar 8, 2013
 * @description
 */
@SuppressWarnings("unchecked")
public class BaseService {
	public DBHelperSpring db;// 数据操作
    public Constants cons ; 
	public final Logger logger = LoggerFactory.getLogger(this.getClass());
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
	 * 获取管理人员名单
	 * @return list
	 * @author liuf
	 * @date 2016年9月26日
	 */
	public List<?> queryManager(HttpServletRequest request,HttpServletResponse response)
	{
		List<Map<String, Object>> result = new ArrayList<>();
		String role_Id = req.getAjaxValue(request, "role_Id");
		String rentalLeaseOrderId = req.getAjaxValue(request, "rentalLeaseOrderId");
		String sql = getSql("systemconfig.queryManager.main").replace("####", "("+role_Id+")");
		List<Map<String, Object>> list = db.queryForList(sql);
		// 排序：按照姓氏首字母排列
    Collections.sort(list, new Comparator<Map<String, Object>>() {
      public int compare(Map<String, Object> o1, Map<String, Object> o2) {
          String name1=String.valueOf(o1.get("name"));
          String name2=String.valueOf(o2.get("name"));
          Collator instance = Collator.getInstance(Locale.CHINA);
          return instance.compare(name1, name2);
      }
    });
		
		if (rentalLeaseOrderId != null && !rentalLeaseOrderId.equals("") && !rentalLeaseOrderId.equals("null")) 
		{	
			// 根据出租合约查询处理人
			String sql1 = getSql("systemconfig.queryManager.rental").replace("####", rentalLeaseOrderId);
			List<Map<String, Object>> resultInfo = db.queryForList(sql1);
			result.addAll(resultInfo);
		} else {
			result.addAll(list);
		}
		
		// 遍历去重
		for (Map<String, Object> resultMap : result) {
			String userId = String.valueOf(resultMap.get("id"));
			for (Map<String, Object> listMap : list) {
				String id = String.valueOf(listMap.get("id"));
				if (userId.equals(id)) {
					continue;
				} else {
					result.add(listMap);
				}
			}
		}
		
		return result;
	}
	/**
	 * 检查用户是否包含某种角色
	 * @author 雷杨
	 * @param request
	 * @param role_id
	 * @return true 包含 false 不包含
	 */
	public boolean checkRole(HttpServletRequest request,String role_id){
		logger.debug("role_id:"+role_id);
		if("".equals(role_id)){
			return true;
		}
		User u = getUser(request);
		String role = u.getRoles();
		String[] roles = role.split(",");
		for(int i=0;i<roles.length;i++){
			if((","+role_id+",").indexOf(","+roles[i]+",") >= 0 ){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 检查用户是否包含某种角色
	 * @author 雷杨
	 * @param request
	 * @param role_id
	 * @return true 包含 false 不包含
	 */
	public boolean checkRole(User u,String role_id){
		logger.debug("role_id:"+role_id);
		if("".equals(role_id)){
			return true;
		}
		String role = u.getRoles();
		String[] roles = role.split(",");
		for(int i=0;i<roles.length;i++){
			if((","+role_id+",").indexOf(","+roles[i]+",") >= 0 ){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 检查用户是否包含当前组织
	 * 
	 * @author 雷杨
	 * @param request
	 * @param orgs   如1,2,3
	 * @return
	 */
	public boolean checkOrg(HttpServletRequest request,String orgs){
		if("".equals(orgs)){
			return true;
		}
		User u = getUser(request);
		String orgId = u.getOrgId();
		String[] orgIds = orgId.split(",");
		for(int i=0;i<orgIds.length;i++){
			if((","+orgs+",").indexOf(","+orgIds[i]+",") >= 0 ){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 检查用户是否包含当前组织
	 * 
	 * @author 雷杨
	 * @param request
	 * @param orgs   如1,2,3
	 * @return
	 */
	public boolean checkOrg(User u,String orgs){
		if("".equals(orgs)){
			return true;
		}
		String orgId = u.getOrgId();
		String[] orgIds = orgId.split(",");
		for(int i=0;i<orgIds.length;i++){
			if((","+orgs+",").indexOf(","+orgIds[i]+",") >= 0 ){
				return true;
			}
		}
		return false;
	}
	
	/**
     * 递归分解xml文件
     * @author 雷杨 2015-04-01
     * @param map
     * @param e
     * @return
     */
    public Map<String, String> elementMap(Map<String, String> map, List<Element> elementList) {
        for(Element e : elementList) {
            String name = e.getName();
            String text = e.getText().trim();
            if(!e.isTextOnly()) {
                map = elementMap(map, e.elements());
            }
            logger.debug(text + "--" + name + "--" + e.isTextOnly());
            map.put(name, text);
        }
        return map;
    }
    
    /**
     * 獲取sql語句
     * @author 雷杨
     * @param key
     * @return
     */
    public String getSql(String key){
    	return SQLconfig.getSql(key);
    }
	
	/**
	 * 扩展xstream，使其支持CDATA块
	 * @date 2013-05-19
	 */
	private static XStream xstream = new XStream(new XppDriver()
	                               {
		                               public HierarchicalStreamWriter createWriter (Writer out)
		                               {
			                               return new PrettyPrintWriter(out)
			                               {
				                               // 对所有xml节点的转换都增加CDATA标记
				                               boolean cdata = true;
				                               
				                               @SuppressWarnings ("unchecked")
				                               public void startNode (String name, Class clazz)
				                               {
					                               super.startNode(name, clazz);
				                               }
				                               
				                               protected void writeText (QuickWriter writer, String text)
				                               {
					                               if (cdata)
					                               {
						                               writer.write("<![CDATA[");
						                               writer.write(text);
						                               writer.write("]]>");
					                               }
					                               else
					                               {
						                               writer.write(text);
					                               }
				                               }
			                               };
		                               }
	                               });
	
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
	
	/**
	 * 获取用户的唯一标识，根据头文件来获取唯一标示
	 * @author 雷杨
	 * @param request
	 * @return
	 */
	public void setClientId(HttpServletRequest request,HttpServletResponse response){
		//初次访问给页面生成一个cookies记录用户唯一标示
		String ycqwjid = getCookie(request, Constants.COOKIE_CLIENT_ID);
		Cookie cookie = null;
		if(!"".equals(ycqwjid)){
			cookie = new Cookie(Constants.COOKIE_CLIENT_ID, ycqwjid);
		}else{
			cookie = new Cookie(Constants.COOKIE_CLIENT_ID, SignUtil.md5(new Random().nextInt()+""+System.currentTimeMillis()+request.getHeader("User-Agent")));
		}
		cookie.setPath("/");
		cookie.setMaxAge(60*60*24);
		response.addCookie(cookie);
	}
	
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
			user.setRoles("22,27,26.28,1,24,25");
			user.setId("82");
			user.setOrgId("1,2,3");
			user.setIs_mobile("1");
			request.getSession().setAttribute("username", user);
			return user;
		}else{
			Login login = (Login)o;
			return login.getUser();
		}
	}
	
	/**
	 * 检查用户是否已经绑定
	 * 
	 * @author 雷杨
	 * @return
	 */
	public int checkUserBind(HttpServletRequest request){
		//获取session中对应的用户
		
		return 1;
	}
	
	/**
	 * 插入历史表记录 表不存在则新建 表中对应的必须增加 his_oper_id,his_oper_date,ip字段 对应的表中对应的必须增加
	 * his_oper_id,his_oper_date,ip字段 历史表名必须是tableName_his格式，如：t_user_his
	 * @author 雷杨
	 * @date 2013-8-15
	 * @param tableName 要插入的表名：
	 * @param owner 数据库用户名
	 * @param id 需要插入历史表中的id值
	 * @param oper_content 操作简单说明
	 * @param params 参数，如果没有传入null
	 * @return
	 */
	public void insertTableLog (HttpServletRequest request, BatchSql batch, String tableName, String id,
	        String oper_content, Object[] oParams)
	{
		
		List<String> params = new ArrayList<String>();
		if(oParams == null || oParams.length == 0){
			this.insertTableLog(request, batch, tableName, id, oper_content, params);
		}else{
			for(int i=0;i<oParams.length;i++){
				params.add(String.valueOf(oParams[i]));
			}
			this.insertTableLog(request, batch, tableName, id, oper_content, params);
		}
	}
	
	/**
	 * 插入历史表记录 表不存在则新建 表中对应的必须增加 his_oper_id,his_oper_date,ip字段 对应的表中对应的必须增加
	 * his_oper_id,his_oper_date,ip字段 历史表名必须是tableName_his格式，如：t_user_his
	 * @author 雷杨
	 * @date 2013-8-15
	 * @param tableName 要插入的表名：
	 * @param owner 数据库用户名
	 * @param id 需要插入历史表中的id值
	 * @param oper_content 操作简单说明
	 * @param params 参数，如果没有传入null
	 * @return
	 */
	public void insertTableLog (HttpServletRequest request, BatchSql batch, String tableName, String id,
	        String oper_content, List<String> params)
	{
		String oper_id = getUser(request).getId();
		String ip = request.getRemoteAddr();
		String sql = "select a.COLUMN_NAME,a.DATA_TYPE,a.CHARACTER_MAXIMUM_LENGTH,a.NUMERIC_PRECISION,a.NUMERIC_SCALE from information_schema.COLUMNS a where UPPER(TABLE_NAME) = UPPER(?) and table_schema = database()";
		List<Map<String, Object>> list = db.queryForList(sql,new Object[]{tableName});
		String tableName_his = tableName + "_his";
		String columns = "";
		for (int i = 0; i < list.size(); i++)
		{
			if (i == list.size() - 1)
			{
				columns += str.get((Map) list.get(i), "COLUMN_NAME");
			}
			else
			{
				columns += str.get((Map) list.get(i), "COLUMN_NAME") + ",";
			}
		}
		// logger.debug("columns:"+columns);
		sql = "select a.COLUMN_NAME,a.DATA_TYPE,a.CHARACTER_MAXIMUM_LENGTH,a.NUMERIC_PRECISION,a.NUMERIC_SCALE from information_schema.COLUMNS a where UPPER(TABLE_NAME) = UPPER(?) and table_schema = database()";
		List<Map<String, Object>> his = db.queryForList(sql,new Object[]{tableName_his});
		int exc = his.size();
		if (exc == 0)
		{// 不存在进行新建表
			sql = "create table " + tableName_his +" as select '" + oper_id + "' his_oper_id ,now() his_oper_date,  '" + ip + "' ip," + "'" + oper_content + "' oper_content," + columns + " from "
			      + tableName + " a where 1=1 " + id;
			if (params != null)
			{
				db.update(sql, params.toArray());
			}
			else
			{
				db.update(sql);
			}
			sql = "alter table " + tableName_his + " modify his_oper_id varchar(255)";
			db.update(sql);
			sql = "alter table " + tableName_his + " modify oper_content varchar(255)";
			db.update(sql);
			sql = "alter table " + tableName_his + " modify ip varchar(255)";
			db.update(sql);
		}
		else
		{// 存在进行插入表数据信息
			// 检查历史表中的字段信息与需要插入历史表的字段信息的字段内容是否是一致，如果不一致进行调整
			List<Map<String, Object>> add_list = new ArrayList<Map<String, Object>>();
			for (int i = 0; i < list.size(); i++)
			{
				Map<String, Object> map = list.get(i);
				String column = str.get(map, "COLUMN_NAME");
				if (!this.checkHisCol(his, column))
				{
					add_list.add(map);
				}
			}
			for (int i = 0; i < add_list.size(); i++)
			{
				Map<String, Object> map = add_list.get(i);
				String column = str.get(map, "COLUMN_NAME");
				String data_type = str.get(map, "DATA_TYPE");
				String char_data_length = str.get(map, "CHARACTER_MAXIMUM_LENGTH");
				String numeric_precision = str.get(map, "NUMERIC_PRECISION");
				String numeric_scale = str.get(map, "NUMERIC_SCALE");
				if ("varchar".equals(data_type))
				{
					column += " varchar(" + char_data_length + ")";
				}
				else if("decimal".equals(data_type))
				{
					column += " decimal(" + numeric_precision + "," + numeric_scale + ")";
				}else{
					column += " " + data_type;
				}
				//alter table MyClass add passtest int(4) default '0';
				sql = "alter table " + tableName_his + " add " + column;
				db.update(sql);
			}
			List<String> param = new ArrayList<String>();
			param.add(oper_id);
			param.add(ip);
			param.add(oper_content);
			if (params != null)
			{
				param.addAll(params);
			}
			sql = "insert into " + tableName_his + "(his_oper_id,his_oper_date,ip,oper_content," + columns
			      + ") select ?,now(),?,?," + columns + " from " + tableName + " a where 1=1 " + id;
			logger.debug("新增数据sql:" + str.getSql(sql, param.toArray()));
			batch.addBatch(sql, param.toArray());
		}
	}
	
	/**
	 * 检查字段在list中是否存在
	 * @description
	 * @author 雷杨 Feb 25, 2014
	 * @param his
	 * @param column
	 * @return
	 */
	private boolean checkHisCol (List<Map<String, Object>> his, String column)
	{
		for (int j = 0; j < his.size(); j++)
		{
			String column_his = str.get((Map) his.get(j), "COLUMN_NAME");
			if (column.equals(column_his))
			{
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 短信接口调用类
	 * 
	 * @author 雷杨
	 * @param mobile
	 * @param content
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public boolean CertifySM(String mobile, String content,
			HttpServletResponse response) throws ServletException, IOException {
		if("1".equals(Constants.isSendMsm)){
			SmClient client = new SmClient();
			String result_mdsmssend = client.mdsmssend(mobile, content, "", "", "",
					"");
		}
		return true;
	}
	
	/**
	 * 短信接口调用类
	 * 
	 * @author 雷杨
	 * @param mobile
	 * @param content
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public boolean CertifySM(String mobile, String content) throws ServletException, IOException {
		if("1".equals(Constants.isSendMsm)){
			SmClient client = new SmClient();
			String result_mdsmssend = client.mdsmssend(mobile, content, "", "", "",
					"");
		}
		return true;
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
	 * 验证是否是当前网站访问
	 * @author 雷杨
	 * @param request
	 * @return
	 */
	public boolean checkReferer(HttpServletRequest request){
		try{
			String referer = request.getHeader("referer");
			//获取当前对应的url
			StringBuffer url = request.getRequestURL();
			String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();
			if(referer.indexOf(tempContextUrl) != 0){
				return false;
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	public String decode(String name){
		try {
			logger.debug(name+"------------------------------------------------------------------------------------");
			//return URLDecoder.decode(name,"utf-8");
			return URLEncoder.encode(name,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public String encode(String name){
		try {
			logger.debug(name+"------------------------------------------------------------------------------------");
			//return URLDecoder.decode(name,"utf-8");
			return URLDecoder.decode(name,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 获取真实ip
	 * @param request
	 * @return
	 * @Description:
	 */
	public String getIP(HttpServletRequest request){
		String ipAddress = null;   
	     //ipAddress = this.getRequest().getRemoteAddr();   
	     ipAddress = request.getHeader("x-forwarded-for");   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	      ipAddress = request.getHeader("Proxy-Client-IP");   
	     }   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	         ipAddress = request.getHeader("WL-Proxy-Client-IP");   
	     }   
	     if(ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {   
	      ipAddress = request.getRemoteAddr();   
	      if(ipAddress.equals("127.0.0.1")){   
	       //根据网卡取本机配置的IP   
	       InetAddress inet=null;   
	    try {   
	     inet = InetAddress.getLocalHost();   
	    } catch (UnknownHostException e) {   
	     e.printStackTrace();   
	    }   
	    ipAddress= inet.getHostAddress();   
	      }   
	            
	     }   
	     //对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割   
	     if(ipAddress!=null && ipAddress.length()>15){ //"***.***.***.***".length() = 15   
	         if(ipAddress.indexOf(",")>0){   
	             ipAddress = ipAddress.substring(0,ipAddress.indexOf(","));   
	         }   
	     }   
	     return ipAddress; 
	}
	
	/**
	 * 分页操作 针对没有默认排序的语句
	 * @author 雷杨
	 * @param request
	 * @param sql 主要的sql语句，可进行拼接，不能包含order by 语句与limik语句在后面
	 * @return
	 */
	public Object getPageList(HttpServletRequest request,String sql,Object[] objs){
		return getPageList(request, sql,objs,null);
	}
	
	/**
	 * 分页操作 针对没有默认排序的语句
	 * @author 雷杨
	 * @param request
	 * @param sql 主要的sql语句，可进行拼接，不能包含order by 语句与limik语句在后面
	 * @return
	 */
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,String sql,Object[] objs){
		return getPageList(request,response, sql,objs,null);
	}
	
	/**
	 * 分页操作 针对有默认排序的语句
	 * @author 雷杨
	 * @param request
	 * @param sql 主要的sql语句，可进行拼接，不能包含order by 语句与limik语句在后面
	 * @param defOrderBySqlKey  默认的排序 对应的key
	 * @return
	 */
	public Object getPageList(HttpServletRequest request,HttpServletResponse response,String sql,Object[] objs,String defOrderBySqlKey){
		String sSearch = req.getAjaxValue(request, "sSearch");//搜索条件内容
		String iDisplayLength = req.getAjaxValue(request, "iDisplayLength");//每页显示的条数
		String iDisplayStart = req.getAjaxValue(request, "iDisplayStart");//从第几行开始
//		String iSortCol = req.getAjaxValue(request, "iSortCol_0");//排序字段iSortingCols
		String iSortCol = req.getAjaxValue(request, "iSortingCols");//排序字段
		String sSortDir_0 = req.getAjaxValue(request, "sSortDir_0");//正续还是倒序
		String sizelength = req.getAjaxValue(request, "sizelength");//存在的数据总长度
		String isCxSeach = req.getAjaxValue(request, "isCxSeach");//是否需要重新计算长度
		String isDc = req.getAjaxValue(request, "isDc");
		String mDataProp = "";//排序对应的sql字段
		String pages = "";//分页语句
		logger.debug("iSortCol:"+iSortCol);
		//计算排序
		if(!"".equals(iSortCol) && iSortCol != null){
			String bSortable_ = req.getAjaxValue(request, "bSortable_"+iSortCol);//判断是否存在排序
			logger.debug("bSortable_:"+bSortable_);
			if("false".equals(bSortable_)){//没有排序
				if(defOrderBySqlKey != null){
					//使用默认的排序
                                        String oderby = getSql(defOrderBySqlKey);
					mDataProp += oderby;
                                        if("".equals(oderby)){
                                            if(defOrderBySqlKey.indexOf("order by") >= 0){
                                                mDataProp += defOrderBySqlKey;
                                            }
                                        }
				}
			}else{//该列上存在排序
				mDataProp += " order by  "+req.getAjaxValue(request, "mDataProp_"+iSortCol)+" "+sSortDir_0;
			}
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
	
	 public static void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
        final String userAgent = request.getHeader("USER-AGENT");
        response.reset();
        try {
            String finalFileName = null;
            if(userAgent.contains("MSIE")){//IE浏览器
                finalFileName = URLEncoder.encode(fileName,"UTF8");
            }else if(userAgent.contains("Mozilla")){//google,火狐浏览器
                finalFileName = new String(fileName.getBytes(), "ISO8859-1");
            }else{
                finalFileName = URLEncoder.encode(fileName,"UTF8");//其他浏览器
            }
//            response.setContentType("application/vnd.ms-excel");
//            response.setHeader("Content-Disposition", "attachment; filename=\"" + finalFileName + "\"");//这里设置一下让浏览器弹出下载提示框，而不是直接在浏览器中打开
            BufferedInputStream br = new BufferedInputStream(new FileInputStream(new File(request.getRealPath("/")+"/upload/excel/"+fileName)));  
            byte[] buf = new byte[1024];  
            int len = 0;  
            response.reset(); // 非常重要
            response.setContentType("application/vnd.ms-excel");  
            response.setHeader("Content-Disposition", "attachment; filename="  + fileName);  
            OutputStream out = response.getOutputStream();  
            while ((len = br.read(buf)) > 0)  
                out.write(buf, 0, len);  
            out.flush();  
            br.close();  
            out.close();  
        } catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
	/**
	 * 分页操作 针对有默认排序的语句
	 * @author 雷杨
	 * @param request
	 * @param sql 主要的sql语句，可进行拼接，不能包含order by 语句与limik语句在后面
	 * @param defOrderBySqlKey  默认的排序 对应的key
	 * @return
	 */
	public Object getPageList(HttpServletRequest request,String sql,Object[] objs,String defOrderBySqlKey){
		
		String sSearch = req.getAjaxValue(request, "sSearch");//搜索条件内容
		String iDisplayLength = req.getAjaxValue(request, "iDisplayLength");//每页显示的条数
		String iDisplayStart = req.getAjaxValue(request, "iDisplayStart");//从第几行开始
		logger.debug(iDisplayStart);
//		String iSortCol = req.getAjaxValue(request, "iSortCol_0");//排序字段
		String iSortCol = req.getAjaxValue(request, "iSortingCols");//排序字段
		String sSortDir_0 = req.getAjaxValue(request, "sSortDir_0");//正续还是倒序
		String sizelength = req.getAjaxValue(request, "sizelength");//存在的数据总长度
		String isCxSeach = req.getAjaxValue(request, "isCxSeach");//是否需要重新计算长度
		String mDataProp = "";//排序对应的sql字段
		String pages = "";//分页语句
		logger.debug("iSortCol------"+iSortCol);
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
		}
		//logger.debug("sSearch:"+sSearch);
		//获取总长度
		if("".equals(sizelength) || "1".equals(isCxSeach)){
			sizelength = db.queryForString("select count(1) from ("+sql+") ccc",objs);
		}
		//logger.debug("iDisplayLength:"+iDisplayLength+"--sizelength:"+sizelength);
		iDisplayLength = "".equals(iDisplayLength)?"5":iDisplayLength;
		//logger.debug("iDisplayLength:"+iDisplayLength+"--sizelength:"+sizelength);
		//添加分页操作
		if(!"NaN".equals(iDisplayLength) && Integer.valueOf(iDisplayLength) < Integer.valueOf(sizelength)){//当总长度大于分页数量可进行分页
			pages = " LIMIT "+iDisplayStart+", "+iDisplayLength;
		}
		
		//logger.debug(mDataProp);
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
		
		return returnMap;
	}
	
	/**
	 * 客户端取每次加载数据
	 * @param request
	 * @param sql
	 * @param params
	 * @return
	 * @author suntf
	 * @date 2016年9月6日
	 */
	public List<?> getMobileList(HttpServletRequest request, String sql, List<String> params)
	{
		String pageNumber = req.getValue(request, "pageNumber"); // 第几页
		String pageSize = req.getValue(request, "pageSize"); // 每页显示的数据
		String regx = "^\\d+$";
		Pattern pattern = Pattern.compile(regx);  
		Matcher matcher=pattern.matcher(pageNumber);
		if(!matcher.matches())
		{
			pageNumber = "0";
		}
		matcher=pattern.matcher(pageSize);
		if(!matcher.matches())
		{
			pageSize = "25";
		}
		BigInteger startPage = new BigInteger(pageNumber).multiply(new BigInteger(pageSize));
		sql = "select * from ("+sql+") a limit " + startPage + ", " + pageSize;
		return db.queryForList(sql, params);
	}
	
	/**
	 * 获取管家列表信息
	 * @return
	 * @author suntf
	 * @date 2016年8月10日
	 */
	public List<?> getManageList()
	{
		String sql = getSql("basehouse.houseInfo.getMangerList");
		return db.queryForList(sql);
	}
	
	
	/************************************ 导入文件公共方法 **********************************************/
	public int dealImportFile(HttpServletRequest request, MultipartFile file, String fileFileName, String dir, 
            String tempTable, String[] paramStr, String procName, String[][] procInParam)
    {
		return this.dealImportFile(request, file, fileFileName, dir, tempTable, paramStr, 
						procName, procInParam, "", SPLIT_SIGN, UPLOAD_TO_SERVER, EXCEL_BB);
    }

	public int dealImportFile(HttpServletRequest request, MultipartFile file, String fileFileName, String dir, 
            String tempTable, String procName, String[][] procInParam, String function_name)
    {
		return this.dealImportFile(request, file, fileFileName, dir, tempTable, null, 
						procName, procInParam, function_name, SPLIT_SIGN, UPLOAD_TO_SERVER, EXCEL_BB);
    }
	
    /** 
    * @Description: 处理导入文件
    * @author suntf 2016-07-15
    * @modify 
    * @param request
    * @param file              导入的文件
    * @param fileFileName  	   文件名
    * @param dir		   	   上传文件的文件夹名
    * @param tempTable         临时表名
    * @param paramStr          导入文件的表头参数
    * @param procName          存储过程名称
    * @param procInParam       存储过程的传入参数
    * @param function_name	   导入功能的名称
    * @param split_sign        导入文件的表头参数的分割符
    * @param upload_to_server  是否将导入的源文件上传保存到服务器  
    * @param excel_bb          excel版本
    * @return
    */ 
    public int dealImportFile(HttpServletRequest request, MultipartFile file, String fileFileName, String dir, 
            String tempTable, String[] paramStr, String procName, String[][] procInParam, String function_name, 
            String split_sign, int upload_to_server, String excel_bb)
    {
        String today = DateHelper.getToday("yyyyMMddHHmmss"); //当前时间
        String toDir = request.getSession().getServletContext().getRealPath("/") + "upload/" + dir + "/"; //上传文件保存地址
        String exportDir = request.getSession().getServletContext().getRealPath("/")+"upload/error/" + dir + "/";//导出文件保存的地址
        String tempName = fileFileName.substring(fileFileName.lastIndexOf(".") + 1).toLowerCase(); //上传的文件名
        fileFileName = fileFileName.substring(0, fileFileName.indexOf(".")) + today + "." + tempName; //保存的文件名 
        
        if(!"".equals(function_name))//导入功能名称不为空（导出字段的信息从配置表中取）
        {
        	paramStr = this.getTitleValueImp(function_name);
        }
        
        //判断上传文件夹和错误信息存放文件夹是否存在，若不存在则创建
        File fileToDir = new File(toDir);//上传文件夹
        if(!fileToDir.exists()  && !fileToDir.isDirectory())
        {
            fileToDir.mkdirs();
        }
        File fileExportDir = new File(exportDir);//错误信息存放文件夹
        if(!fileExportDir .exists()  && !fileExportDir.isDirectory())
        {
            fileExportDir.mkdirs();
        }
        int res = 1;
        if(upload_to_server == 1)
        {
        	res = new FileHelper().copyFile(file, toDir + fileFileName); //复制文件
        }
        if(res == 0) //上传失败
        {
            return 2;
        }
        else //上传成功
        {
        	int result = 0;
        	int column_level = 1;
        	if(!"".equals(function_name))
        	{
        		column_level = this.getColumnLevel(function_name);//获取字段层级
        	}
        	if(!"2003".equals(excel_bb))
        	{
        		result = this.importTempTableBaseWithNewExcel(file, tempTable, paramStr, split_sign, column_level); //导入文件的数据插入临时表(excel2003以上版本的通道)
        	}
        	else
        	{
        		result = this.importTempTableBase(file, tempTable, paramStr, split_sign, column_level); //导入文件的数据插入临时表(excel2003版本的通道)
//        		log.debug("结果："+result);
        	}
            if(result != 1)  //导入文件的数据插入临时表操作失败
            {
                return result ;
            }
            else     //导入文件的数据插入临时表操作成功
            {
                //调用存储过程 验证导入计划
                ProcHelper proc = db.getProcHelper(procName);
                for(int i = 0; i < procInParam.length; i++)
                {
                    String[] procInParam1 = procInParam[i];
                    proc.setVarcharParam(procInParam1[0]);
                    proc.setValue(procInParam1[0], procInParam1[1]);
                }
                proc.setVarcharOutParam("proc_result");
                Map<?,?> map = proc.execute();
                int exc = Integer.parseInt((String) map.get("proc_result"));
//                log.debug("存储过程执行结果：" + exc);
                
                if(exc == -1)  //存储过程执行失败
                {
                    //log.debug(tempTable);
                    return -4; 
                }
                else if(exc == 0) //存储过程执行成功 验证有失败数据提供下载
                {
                    //查询出成功记录，失败记录的条数
                    String sql = " select " 
                               + " sum(case when a.flag='成功' then 1 else 0 end) suc_cnt, " 
                               + " sum(case when a.flag='失败' then 1 else 0 end) fail_cnt " 
                               + " from " + tempTable + " a ";
                    Map<?,?> map1 = db.queryForMap(sql);
                    request.setAttribute("suc_cnt", StringHelper.get(map1, "suc_cnt"));
                    request.setAttribute("fail_cnt", StringHelper.get(map1, "fail_cnt"));
                    if(!"2003".equals(excel_bb))
                	{
                    	XSSFWorkbook xb = this.importValErrorBaseWithNewExcel(tempTable, paramStr, split_sign, function_name, column_level);
                        File outFile = new File(exportDir, "error_" + tempTable + ".xlsx");
                        try
                        {
                            FileOutputStream outStream = new FileOutputStream(outFile);
                            xb.write(outStream);
                            outStream.flush();
                            outStream.close();
                        }
                        catch(Exception e)
                        {
                            logger.error(e.toString());
                            e.printStackTrace();
                            //log.debug("导出验证后信息异常---->" + e.toString());
                        }
                        //保存失败记录文件路径
                        //logger.debug("upload/error/" + dir + "/error_" + tempTable + ".xlsx");
                        request.setAttribute("downloadurl", "upload/error/" + dir + "/error_" + tempTable + ".xlsx");
                	}
                    else
                    {
                    	HSSFWorkbook wb = this.importValErrorBase(tempTable, paramStr, split_sign, function_name, column_level);
                        File outFile = new File(exportDir, "error_" + tempTable + ".xls");
                        try
                        {
                            FileOutputStream outStream = new FileOutputStream(outFile);
                            wb.write(outStream);
                            outStream.flush();
                            outStream.close();
                        }
                        catch(Exception e)
                        {
                            logger.error(e.toString());
                            e.printStackTrace();
                            //log.debug("导出验证后信息异常---->" + e.toString());
                        }
                        //保存失败记录文件路径
                        request.setAttribute("downloadurl", "upload/error/" + dir + "/error_" + tempTable + ".xls");
                    }
                    return 0;
                }
                else
                {
                    String sql = " select count(1) from " + tempTable + " a where a.flag ='成功' ";
                    request.setAttribute("suc_cnt", db.queryForInt(sql));
                    return 1;
                }
            }
        }
    }
    
    /** 
	* @Description: 获取标题头字段名称
	* @author suntf 2016-07-14
	* @modify 
	* @param function_name
	* @return
	*/ 
	public String[] getTitleValueImp(String function_name)
	{
		String sql = " select column_name, column_value from yc_export_column_cfg " +
					 "  where status = 1 and column_value is not null and function_name = ? " +
					 "  order by orderby ";
		List list = db.queryForList(sql, new Object[]{function_name});
		String[] column = new String[list.size()];
		Map<?, ?> map = new HashMap();
		for(int i = 0; i < list.size(); i++)
		{
			map = (Map<?, ?>)list.get(i);
			column[i] = StringHelper.get(map, "column_name")+","+ StringHelper.get(map, "column_value");
		}
		return column;
	}
    
   /** 
	* @Description: 获取导出字段层级
	* @author suntf 2016-07-15
	* @modify 
	* @param function_name
	* @return
	*/ 
	private int getColumnLevel(String function_name) 
	{
		String sql = " select count(distinct column_level) from yc_export_column_cfg " +
		 			 "	where function_name = ? and status = 1 ";
		return db.queryForInt(sql, new Object[]{function_name});
	}

  /** 
	* @Description: 导出验证过后的数据（excel2003以上版本的通道） 
	* @author suntf 2013-10-24
	* @modify 
	* @param tempTable
	* @param paramStr
    * @param column_level 
    * @param function_name 
	* @param split_sign2
	* @return
	*/ 
	private XSSFWorkbook importValErrorBaseWithNewExcel(String tempTable,
			String[] paramStr, String split_sign, String function_name, int column_level) 
	{
		if(!"".equals(function_name))
		{
			XSSFWorkbook xb = new XSSFWorkbook();
    		XssfHelper xssfHelper = new XssfHelper();
    		List<?> rwcjkjList = null;
    		String sql = " select * from " + tempTable;
    		rwcjkjList = db.queryForList(sql);
    		//配置excel信息
    		xb = this.columnCfgBy2007(xb, function_name, 2);
    		String[][] title = this.getTitleValue(function_name, 2);
    		
    		xssfHelper.export3(xb, rwcjkjList, title, column_level, 0, 0);
            return xb;
		}
		else
		{
			int paramLength = paramStr.length;                  //参数数组的长度
	        String[][] title = new String[paramLength+2][2];    //excel的标题
	        String sqlStr = "";
	        int i=0;
	        for(; i<paramStr.length; i++)
	        {
	            String[] param = paramStr[i].split(split_sign);
	            title[i] = param;
	            sqlStr += param[1] + ", ";
	        }
	        title[i++] = new String[] {"描述","decription"};
	        title[i] = new String[] {"是否成功","flag"};
	        String sql = " SELECT " + sqlStr + " flag, decription from " + tempTable +" ";
	        //log.debug("导出验证过后的数据----->" + sql);
	        List<?> list = db.queryForList(sql);
	        XSSFWorkbook xb = new XssfHelper().export(list, title, "sheet");
	        return xb;
		}
	}

  /** 
	* @Description: 导入文件的数据插入临时表（excel2003以上版本的通道）
	* @author suntf 2013-10-24
	* @modify 
	* @param file
	* @param tempTable
	* @param paramStr
	* @param column_level 
	* @param split_sign2
	* @return  -11:文件上传错误，该文件不存在！ -12:Excel读取错误！ -13:Excel无数据！
	* @throws IOException 
	* @throws FileNotFoundException 
	*/ 
	private int importTempTableBaseWithNewExcel(MultipartFile file, String tempTable,
			String[] paramStr, String split_sign, int column_level)
	{
		int result = 0;
		BatchSql batchSql = new BatchSql();
		XssfHelper xssfHelper = new XssfHelper();
		XSSFWorkbook xb = null;
		try {
			xb = new XSSFWorkbook(file.getInputStream());
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
        String sql = "";
        sql = this.createTempTableBase(file,tempTable,paramStr, split_sign); //创建临时表
        batchSql.addBatch(sql);
        sql = this.insertTempDataBase(tempTable, paramStr, split_sign); //向临时表插入数据 sql语句
        XSSFSheet sheet = xb.getSheetAt(0);
        XSSFRow row = sheet.getRow(0);
		int rols = row.getPhysicalNumberOfCells();
		if(rols != paramStr.length)     //到excel文件中的列数不等于paramStr.length时，数据不完整
	    {
	        return -2;      //上传的excel文件中数据不完整，列数不正确，请按模板要求导入！
	    }
		short[] dataMap = new short[rols];
		for (int i = 0; i < rols; i++) 
		{
			dataMap[i] = Short.parseShort(Integer.toString(i));
		}
        result = xssfHelper.importExcelToTable(file, dataMap, sql, batchSql, column_level);
        if(result == 1)
        {
        	result = db.doInTransaction(batchSql);
        }
		return result;
	}

   /** 
    * @Description: 上传文件数据导入临时表 
    * @author suntf 2013-03-14
    * @modify 
    * @param file1
    * @param tempTable
    * @param paramStr
	* @param split_sign 
	* @param column_level 
	* @param excel_bb 
    * @return -1：excel中无数据 -2：解析excel 文件异常 -3：excel文件中数据不完整
    */ 
    public int importTempTableBase(MultipartFile file,String tempTable,String[] paramStr, String split_sign, int column_level)
    {
        BatchSql batchSql = new BatchSql();
        String sql = "";
        sql = this.createTempTableBase(file,tempTable,paramStr, split_sign); //创建临时表
        batchSql.addBatch(sql);
        sql = this.insertTempDataBase(tempTable, paramStr, split_sign); //向临时表插入数据 sql语句
        try 
        {
            Workbook wb = Workbook.getWorkbook(file.getInputStream()); //获取Excel工作簿
            Sheet sheet = wb.getSheet(0);
            int rows = sheet.getRows(); //获取行数
            if(rows<=column_level)
            {
                return -1;      //无数据
            }
            int cols = sheet.getColumns();  //获取列数
            if(cols != paramStr.length)     //到excel文件中的列数不等于paramStr.length时，数据不完整
            {
                return -2;      //上传的excel文件中数据不完整，列数不正确，请按模板要求导入！
            }
            for(int i=column_level; i<rows; i++)
            {
                Cell[] cells = sheet.getRow(i); //获取单元格数组
                String[] strCols = new String[cols];
                for(int j=0; j<cols; j++)
                {
                    try 
                    {
                        strCols[j] = cells[j].getContents().trim();     //获取每个单元格内的值
                    } 
                    catch (Exception e) 
                    {
                        strCols[j] = "";
                    }
                }
                batchSql.addBatch(sql, strCols);    //从excel中读取数据插入到临时表中
               // log.debug("向临时表中添加数据-->"+str.getSql(sql, strCols));
            }
            wb.close();
        } 
        catch (BiffException e) 
        {
            logger.error(e.toString());
            e.printStackTrace();
            return -3;  //解析excel 文件异常
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            return -3;  //解析excel 文件异常
        }
        return db.doInTransaction(batchSql);
    }

    /** 
    * @Description: 创建临时表
    * @author suntf 2013-03-14
    * @modify 
    * @param file1
    * @param tempTable
    * @param paramStr
    * @param split_sign 
    * @return
    */ 
    public String createTempTableBase(MultipartFile file,String tempTable,String[] paramStr, String split_sign)
    {
        String sqlStr = "";
        for(int i = 0;i < paramStr.length;i++)
        {
            String[] param = paramStr[i].split(split_sign);
            sqlStr += param[1] + " varchar(255), ";
        }
        String sql = " create table " + tempTable + " (" + sqlStr + "flag varchar(255),decription varchar(255))";
        //log.debug("创建临时表sql--->" + sql);
        return sql;
    }
    
   /** 
    * @Description: 向临时表插入数据的sql语句 
    * @author suntf 2013-03-14
    * @modify 
    * @param tempTable
    * @param paramStr
    * @param split_sign 
    * @return
    */ 
    public String insertTempDataBase(String tempTable, String[] paramStr, String split_sign)
    {
        String sqlStr1 = "";
        String sqlStr2 = "";
        for(int i=0; i<paramStr.length; i++)
        {
            String[] param = paramStr[i].split(split_sign);
            if(i == paramStr.length -1)
            {
                sqlStr1 += param[1];
                sqlStr2 += " ?";
            }
            else
            {
                sqlStr1 += param[1] + ", ";
                sqlStr2 += " ?,";
            }
        }
        String sql = " INSERT INTO "+tempTable+"  ("+sqlStr1+") VALUES ("+sqlStr2+")";
        //log.debug("插入临时表sql-->" + sql);
        return sql;
    }
    
   /** 
    * @Description: 导出验证过后的数据 
    * @author suntf 2013-03-14
    * @modify 
    * @param tempTable 临时表
    * @param paramStr 参数数组
    * @param split_sign 
    * @param column_level 
    * @param function_name 
    * @return
    */ 
    public HSSFWorkbook importValErrorBase(String tempTable, String[] paramStr, String split_sign, String function_name, int column_level)
    {
    	if(!"".equals(function_name))
    	{
    		HSSFWorkbook wb = new HSSFWorkbook();
    		HssfHelper hssfHelper = new HssfHelper();
    		List<?> rwcjkjList = null;
    		String sql = " select * from " + tempTable;
    		rwcjkjList = db.queryForList(sql);
    		//配置excel信息
    		wb = this.columnCfgBy2003(wb, function_name, 2);
    		String[][] title = this.getTitleValue(function_name, 2);
    		
    		hssfHelper.export3(wb, rwcjkjList, title, column_level, 0, 0);
            return wb;
    	}
    	else
    	{
    		int paramLength = paramStr.length;                  //参数数组的长度
            String[][] title = new String[paramLength+2][2];    //excel的标题
            String sqlStr = "";
            int i=0;
            for(; i<paramStr.length; i++)
            {
                String[] param = paramStr[i].split(split_sign);
                title[i] = param;
                sqlStr += param[1] + ", ";
            }
            title[i++] = new String[] {"描述","decription"};
            title[i] = new String[] {"是否成功","flag"};
            String sql = " SELECT " + sqlStr + " flag, decription from " + tempTable +" ";
            //log.debug("导出验证过后的数据----->" + sql);
            List<?> list = db.queryForList(sql);
            HSSFWorkbook wb = new HssfHelper().export(list, title, "sheet");
            return wb;
    	}
    }
    
    /** 
	* @Description: 获取标题头字段名称
	* @author suntf 2016-07-14
	* @modify 
	* @param function_name
	* @return
	*/ 
	public String[][] getTitleValue(String function_name, int flag)
	{
		String sql = " select column_name, column_value from yc_export_column_cfg " +
					 "  where status = 1 and column_value is not null and function_name = ? " +
					 "  order by orderby ";
		List list = db.queryForList(sql, new Object[]{function_name});
		String[][] column = null;
		if(flag == 1)
		{
			column = new String[list.size()][2];
		}
		else
		{
			column = new String[list.size()+2][2];
			column[list.size()] = new String[]{"flag", ""};
			column[list.size()+1] = new String[]{"decription", ""};
		}
		
		Map<?, ?> map = new HashMap();
		for(int i = 0; i < list.size(); i++)
		{
			map = (Map<?, ?>)list.get(i);
			column[i] = new String[]{StringHelper.get(map, "COLUMN_VALUE"), ""};
		}
		return column;
	}
	
	  /** 
		* @Description: 配置excel表头excel2003
		* @author suntf 2016-07-14
		* @modify 
		* @param wb
		* @param function_name：功能名称
		* @param flag：1 下载模板 2 导出错误文件
		* @return
		*/ 
		@SuppressWarnings("unchecked")
		public HSSFWorkbook columnCfgBy2003(HSSFWorkbook wb, String function_name, int flag) 
		{
			//取出excel标题头层数
			String sql = " select count(distinct column_level) from yc_export_column_cfg " +
						 "	where function_name = ? and status = 1 ";
			int level = db.queryForInt(sql, new Object[]{function_name});
			HssfHelper hssfHelper = new HssfHelper();
			//配置excel信息
			HSSFSheet sheet1 = wb.createSheet(function_name);
			wb.setSheetName(0, function_name);
			HSSFRow[] row = new HSSFRow[level];
			List list = new ArrayList();
			HSSFCell cell = null;
			Map<?, ?> map = new HashMap();
			String isleaf = "";//标题头下子标题个数，若是1，可能有两种情况，一种是自己本身，还有一种是子标题
			int isleaf_act = 0;//标题下是否有子标题，和isleaf字段合并判断是否确实有子标题
			int m = 0;//记录标题头总行数
			int k = 0;//标记标题头行数
			for(int i = 0; i< level; i++)
			{
				int l = 0;//标题头列数
				row[i] = sheet1.createRow((short) i);
				//取出excel的第i+1层标题以及其下层标题个数
//				sql = " select column_name, column_value, (select count(1) from yc_export_column_cfg b where a.id = b.superior_id and b.status = 1) subcnt, orderby, column_level " +
//					  "   from yc_export_column_cfg a " +
//					  "  where status = 1 and column_level = ? and function_name = ? " +
//					  "  union " +
//					  " select column_name, column_value, 0, orderby, column_level " +
//					  "		   from yc_export_column_cfg a " +
//					  "  where status = 1 and column_level < ? and column_value is not null and function_name = ? " +
//					  "	 order by orderby ";
				sql = "with temp as(" +
					  "		select column_id2, count(isleaf) isleaf " +
					  "       from ( select a.*, CONNECT_BY_ROOT(id) column_id2, CONNECT_BY_ISLEAF isleaf, level " +
					  "				   from njbg.yc_export_column_cfg a " +
					  "				  where function_name = ? and status = 1 " +
					  "				connect by prior a.id = a.superior_id " +
					  "				  start with a.column_level = ?) where isleaf = 1 group by column_id2) " +
					  " select id, column_name, column_value, a.isleaf, orderby, column_level from temp a, yc_export_column_cfg b " +
					  "  where a.column_id2 = b.id " +
					  "  union " +
					  " select id, column_name, column_value, 1, orderby, column_level " +
					  "   from yc_export_column_cfg a " +
					  "  where status = 1 and column_level < ? and column_value is not null and function_name = ? " +
					  "  order by orderby";
				list = db.queryForList(sql, new Object[]{function_name, i+1, i+1, function_name});
				
				//设置excel标题头
				for(int j = 0; j < list.size(); j++)
				{
					map = (Map<?, ?>)list.get(j);
					cell = hssfHelper.createHeadCell(wb, row[i], l);
					cell.setCellValue(StringHelper.get(map, "column_name"));
					isleaf = StringHelper.get(map, "isleaf");
					isleaf_act = this.getColumnIsLeaf(function_name, StringHelper.get(map, "id"));
					if(!"1".equals(isleaf) || isleaf_act > 0 )//其下有子标题
					{
						hssfHelper.merge(sheet1, k, l, k, l+Integer.parseInt(isleaf)-1);
						l = l+Integer.parseInt(isleaf);
					}
					else
					{
						hssfHelper.merge(sheet1, k, l, level-1, l);
						l = l + 1;
					}
				}
				k++;
				m = k;
			}
			if(flag == 2)//导出错误文件
			{
				cell = hssfHelper.createHeadCell(wb, row[0], list.size());
				cell.setCellValue("是否成功");
				hssfHelper.merge(sheet1, 0, list.size(), m-1, list.size());
				cell = hssfHelper.createHeadCell(wb, row[0], list.size()+1);
				cell.setCellValue("描述");
				hssfHelper.merge(sheet1, 0, list.size()+1, m-1, list.size()+1);
			}
			return wb;
		}
		
		/** 
		* @Description: 查看是否有子标题
		* @author suntf 2014-01-16
		* @modify 
		* @param function_name
		* @param string
		* @return
		*/ 
		private int getColumnIsLeaf(String function_name, String id) 
		{
			String sql = " select count(1) from yc_export_column_cfg " +
						 "  where superior_id = ? and function_name = ? ";
			return db.queryForInt(sql, new Object[]{id, function_name});
		}
	
    /**
     * @Description: 获取导入EXCEL零时表要用到的后缀: 序列（NJRY_IMP_SEQUENCE） 
     * 				 如：guzt_tmp_121333 中的 121333 即为 NJRY_IMP_SEQUENCE的值
     * @author
     * @modify 
     * @return
     */
    public String getImpSequence()
    {
    	if(!isImpSequenceExist()) // 如果该序列不存在自动创建一个
    	{
    		String sql = getSql("systemconfig.createTmpTable");
    		db.update(sql);
    	}
    	String sql = getSql("systemconfig.insertTmpTable");
    	int res = db.insert(sql, new Object[]{});
    	return String.valueOf(res == -2 ? DateHelper.getToday("yyMMddHHmmss")+new Random().nextInt(100):res);
    }
    
    /**
     * @Description: 判断导入EXCEL零时表后缀用到的序列（NJRY_IMP_SEQUENCE）是否已存在 
     * @author suntf 2016-07-11
     * @modify 
     * @return true:已存在  false:不存在
     */
    public boolean isImpSequenceExist()
    {
    	String sql = getSql("systemconfig.checkTmpTableExist");
    	if(db.queryForInt(sql) > 0 )
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    /**
     * @Description: 获取EXCEL导入零时表名称
     * @author suntf  2016-07-11
     * @modify 
     * @return
     */
    public String getImpTmpTableName()
    {
    	return "njyc_tmp_excel_"+ this.getImpSequence();
    }
    
    /** 
	* @Description: 配置excel表头excel2007
	* @author suntf 2016-07-14
	* @modify 
	* @param wb
	* @param function_name：功能名称
	* @param flag：1 下载模板 2 导出错误文件
	* @return
	*/ 
	@SuppressWarnings("unchecked")
	public XSSFWorkbook columnCfgBy2007(XSSFWorkbook xb, String function_name, int flag) 
	{
		//取出excel标题头层数
		String sql = " select count(distinct column_level) from yc_export_column_cfg " +
					 "	where function_name = ? and status = 1 ";
		int level = db.queryForInt(sql, new Object[]{function_name});
		XssfHelper xssfHelper = new XssfHelper();
		//配置excel信息
		XSSFSheet sheet1 = xb.createSheet(function_name);
		xb.setSheetName(0, function_name);
//		XSSFRow[] row = new XSSFRow[level];
//		List list = new ArrayList();
//		XSSFCell cell = null;
//		Map<?, ?> map = new HashMap();
//		String isleaf = "";//标题头下是否有子标题
//		int m = 0;//记录标题头总行数
//		int n = 0;//记录标题头总列数
//		int isleaf_act = 0;
//		for(int i = 0; i< level; i++)
//		{
//			int k = 0;//标记标题头行数
//			int l = 0;//标题头列数
//			row[i] = sheet1.createRow((short) i);
//			//取出excel的第i+1层标题以及其下层标题个数
////			sql = " select column_name, column_value, (select count(1) from yc_export_column_cfg b where a.id = b.superior_id and b.status = 1) subcnt, orderby, column_level " +
////				  "   from yc_export_column_cfg a " +
////				  "  where status = 1 and column_level = ? and function_name = ? " +
////				  "  union " +
////				  " select column_name, column_value, 0, orderby, column_level " +
////				  "		   from yc_export_column_cfg a " +
////				  "  where status = 1 and column_level < ? and column_value is not null and function_name = ? " +
////				  "	 order by orderby ";
//			sql = "with temp as(" +
//				  "		select column_id2, count(isleaf) isleaf " +
//				  "       from ( select a.*, CONNECT_BY_ROOT(id) column_id2, CONNECT_BY_ISLEAF isleaf, level " +
//				  "				   from njbg.yc_export_column_cfg a " +
//				  "				  where function_name = ? and status = 1 " +
//				  "				connect by prior a.id = a.superior_id " +
//				  "				  start with a.column_level = ?) where isleaf = 1 group by column_id2) " +
//				  " select id, column_name, column_value, a.isleaf, orderby, column_level from temp a, yc_export_column_cfg b " +
//				  "  where a.column_id2 = b.id " +
//				  "  union " +
//				  " select id, column_name, column_value, 1, orderby, column_level " +
//				  "   from yc_export_column_cfg a " +
//				  "  where status = 1 and column_level < ? and column_value is not null and function_name = ? " +
//				  "  order by orderby";
//			list = db.queryForList(sql, new Object[]{function_name, i+1, i+1, function_name});
//			
//			//设置excel标题头
//			for(int j = 0; j < list.size(); j++)
//			{
//				map = (Map<?, ?>)list.get(j);
//				cell = xssfHelper.createHeadCell(xb, row[i], l);
//				cell.setCellValue(StringHelper.get(map, "column_name"));
//				isleaf = StringHelper.get(map, "isleaf");
//				isleaf_act = this.getColumnIsLeaf(function_name, StringHelper.get(map, "id"));
//				if(!"1".equals(isleaf) || isleaf_act > 0)//其下有子标题
//				{
//					xssfHelper.merge(sheet1, k, l, k, l+Integer.parseInt(isleaf)-1);
//					l = l+Integer.parseInt(isleaf);
//				}
//				else
//				{
//					xssfHelper.merge(sheet1, k, l, level-1, l);
//					l = l + 1;
//				}
//			}
//			k++;
//			m = k;
//			n = l;
//		}
//		if(flag == 2)//导出错误文件
//		{
//			cell = xssfHelper.createHeadCell(xb, row[0], list.size());
//			cell.setCellValue("是否成功");
//			xssfHelper.merge(sheet1, 0, list.size(), m-1, list.size());
//			cell = xssfHelper.createHeadCell(xb, row[0], list.size()+1);
//			cell.setCellValue("描述");
//			xssfHelper.merge(sheet1, 0, list.size()+1, m-1, list.size()+1);
//		}
		return xb;
	}
	
    /**
     * 删除临时表
     * @author
     * @param tempTable
     */
    public void dropTable(String v_table)
    {
        String sql = getSql("systemconfig.droptable");
        int result = db.update(db.queryForString(sql, new Object[]{v_table}));
        if(result != 1) {
            logger.debug("临时表删除失败--->" + v_table);
        }
    }
	
    /**
     * 设置当前操作名称，如：发起支付审批操作，累加
     * @param conString
     */
    public void setOperContent(String conString){
    	Object operContent = MDC.get("oper_content");
    	if(operContent != null){
    		conString += "|"+String.valueOf(operContent);
    	}
    	MDC.put("oper_content", conString);
    }
    
}
