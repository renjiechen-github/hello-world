package pccom.common.filter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.MDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;
import pccom.common.SQLconfig;
import pccom.common.util.Constants;
import pccom.common.util.DBHelperSpring;
import pccom.common.util.SignUtil;
import pccom.common.util.SpringHelper;
import pccom.common.util.StringHelper;
import pccom.web.beans.Login;
import pccom.web.beans.User;
import pccom.web.listener.SessionManage;

/**
 * 登陆统一检验
 * @author chang
 * @createDate 2014-11-14
 */
public class MobileLoginFilter implements Filter {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	private static Map<String,String> operIdOrgIdMap = new HashMap<String, String>();
	public void setFilterConfig(FilterConfig config) {
		this.config = config; 
	}

	public FilterConfig getFilterConfig() {
		return config; 
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//进行统一检验是否已经登陆过，如果没有登陆需要进行登陆操作
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse rep = (HttpServletResponse)response;
		String url = req.getRequestURI();
		Enumeration<String> en = request.getParameterNames();
		String param = "?1=1";
		boolean flag = true;
		while (en.hasMoreElements())
		{
			String name = (String) en.nextElement();
			String value = req.getParameter(name);
			param += "&"+name+"="+value;
//			logger.debug("name:"+name+" <=> value:"+value);
			//logger.debug("name:"+name+" <=> value:"+value);
//			logger.debug("name:"+name+" <=> value:"+value);
		}
//		logger.debug("------sadfasdfasdfdsfas------->"+url+param);
//		if(true)
//		{
//			chain.doFilter(req, rep);
//			return;
//		}
                MDC.put("oper_ip",getIP(req));
		if((url.indexOf("mobile/") > -1 && url.indexOf(".do")>-1))
		{
//			logger.debug("------sadfasdfasdfdsfas------->"+url+param);
			if(url.indexOf("getMaxVersionMark.do")>-1 || url.indexOf("checkLogin.do") > -1 || url.indexOf("updateChannelId.do") > -1 || url.indexOf("showFlowDetail.do") > -1 || url.indexOf("yetDisposeDitail.do") > -1 || url.indexOf("task/getInfo.do") > -1 || url.indexOf("miLeaveService/create.do") > -1 || url.indexOf("rankAgreement/getRankAgreementList.do") > -1 || url.indexOf("miLeaveService/getList.do") > -1 || url.indexOf("task/getMangerList.do") > -1 || url.indexOf("miLeaveService/getList.do") > -1 || url.indexOf("miLeaveService/delete.do") > -1 || url.indexOf("task/getFinancialList.do") > -1 || url.indexOf("task/getPayList.do") > -1 || url.indexOf("rankAgreement/getRankAgreementList.do") > -1 || url.indexOf("houseMgr/queryRankHouse.do") > -1 || url.indexOf("task/sys/queryItem.do") > -1)
			{
				chain.doFilter(req, rep);
				return;
			}
			String userName = getCookie(req,"username");
//			logger.debug("userName:"+userName);
//			String orgId = getCookie(req,"org_id");
//			logger.debug(orgId);
//			orgId = "1";
    		userName = "8857c12157182b3ad06d51f57e663450";
//			String value = getCookie(req,Constants.COOKIE_CLIENT_ID);
			Object obj = SessionManage.getSession("account"+userName);
//			logger.debug("obj:"+obj);
			String operator_id = StringHelper.get(operIdOrgIdMap, userName);
//			String org_id = StringHelper.get(operIdOrgIdMap, orgId);
			if(!"".equals(operator_id))
			{
				operIdOrgIdMap.put(operator_id, operator_id);
//				 operIdOrgIdMap.put(orgId, orgId);
			}
			if(obj == null)
			{
				// 初始化用户信息
				flag = initUserInfo(userName, req, rep);
			}
			else
			{
				Login login = (Login)obj;
				User user = login.getUser();
                                MDC.put("oper_id",user.getId());
//				logger.debug("aaa:"+JSONObject.fromObject(user));
				if(user.getId() == null || "".equals(user.getId()))
				{
					// 初始化用户信息
					flag = initUserInfo(userName, req, rep);
				}
				else
				{
					String currentorgId = user.getOrgId();
					String currentOper = user.getMobile();
					if(!md5(currentOper).equals(userName))
					{
						flag = initUserInfo(userName, req, rep);
					}
				}
			}
		}
		if(flag)
		{
			chain.doFilter(req, rep);
		}
		 
	}
	
	/**
	 * md5加密
	 * 
	 * @param source
	 * @return
	 */
	public String md5(String source)
	{

		StringBuffer sb = new StringBuffer(32);

		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(source.getBytes("UTF-8"));

			for (int i = 0; i < array.length; i++)
			{
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.substring(1, 3));
			}
		}
		catch (Exception e)
		{
			return "";
		}
		return sb.toString();
	}
	
	/**
	 * 通过用户名和组织初始化用户信息
	 * @param userName
	 * @param orgId
	 * @author suntf
	 * @date 2016年9月8日
	 */
	public boolean initUserInfo(String userName, HttpServletRequest request, HttpServletResponse response)
	{
		DBHelperSpring db = (DBHelperSpring)SpringHelper.getBean("dbHelper");
		String sql = SQLconfig.getSql("user.mobileCheckUser");
//		logger.debug("00000000000====>"+StringHelper.getSql(sql, new Object[]{userName}));
		Map<String,Object> map = db.queryForMap(sql,new Object[]{userName});
//		logger.debug("map:"+map);
		User user = new User();
//		if(exc == 1)
//		{
			//进行判断是否是已经登录过的用户，存在登录用户就直接注销已经登录的用户
			String sessionName = SessionManage.getSessionName("account"+userName);
			if(!"".equals(sessionName))
			{
				//已经存在登录用户，直接强制退出先前登录的用户
				//直接移除登录信息，这样先前登录的信息就会直接失效掉了
				SessionManage.removeSession(request.getSession(),sessionName);
			}
			//校验通过，进行获取用户基本信息
			user.setId(StringHelper.get(map, "id"));
			user.setMobile(StringHelper.get(map, "mobile"));
			user.setG_id(StringHelper.get(map, "g_id"));
			user.setIs_mobile("1");
			user.setName(StringHelper.get(map, "name"));
			user.setOrgId(db.queryForString(SQLconfig.getSql("user.getOrgs"),new Object[]{user.getId()}));
			user.setHeadimgurl(StringHelper.get(map, "headerImage"));
			user.setRoles(db.queryForString(SQLconfig.getSql("user.getRole"),new Object[]{user.getId()}));
			user.setType(StringHelper.get(map, "type"));
			user.setRoleNames(StringHelper.get(db.queryForMap(SQLconfig.getSql("user.getRolesInfo"), new Object[]{user.getId()}), "roleNames"));
			Login login = new Login();
			login.setCookies(getCookie(request, Constants.COOKIE_CLIENT_ID));
			login.setMobile(userName);
			login.setUser(user);
                        
                        MDC.put("oper_id",user.getId());
                        
//			user.setOrgName(StringHelper.get(db.queryForMap(SQLconfig.getSql("user.getOrgInfo"),new Object[]{org_id}), "org_name"));
//			logger.debug(JSONObject.fromObject(user));
			//进行设置用户信息
			SessionManage.setSession(request.getSession(), userName , login);
//			logger.debug("======JSON========="+getCookie(request, Constants.COOKIE_CLIENT_ID)+":"+userName);
//			logger.debug("======JSONUSER========="+JSONObject.fromObject(SessionManage.getSession("account"+userName+getCookie(request, Constants.COOKIE_CLIENT_ID))));
			String menu_id = "";
			String power_id = "";
			String url = request.getRequestURI();
			if (user.getId() == null || "".equals(user.getId()))
			{
				logger.debug(url + "未登陆");
				result(-99999, response);
				return false;
			}
			if (url.lastIndexOf(".html") > 0)
			{// 访问的是菜单链接
				// 查询出菜单id
				menu_id = db.queryForString(SQLconfig.getSql("menu.getMenuId"), new Object[] { url.substring(1).trim()
						.replaceAll("//", "/") });
			}
			else
			{
				map = db.queryForMap(SQLconfig.getSql("menu.getPowerMenu"), new Object[] { url.substring(1).trim()
						.replaceAll("//", "/") });
				power_id = StringHelper.get(map, "power_id");
				menu_id = StringHelper.get(map, "menu_id");
			}
			Enumeration<String> e = request.getHeaderNames();
			String heads = "";
			while (e.hasMoreElements())
			{
				String name = e.nextElement();
				String value = request.getHeader(name);
				heads += name + ":" + value;
			}
			// 记录访问日志
			db.update(SQLconfig.getSql("systemconfig.visit"), new Object[] { getIP(request), user.getId(), heads, power_id,
					menu_id, user.getRoles(), url });
			String root = Constants.root;
			if ("/".equals(Constants.root))
			{
				root = "";
			}
//			logger.debug("url--:"+url.replace(root, "").substring(1));
			if(!checkPower(url.replace(root, "").substring(1), user.getRoles()))
			{
				logger.debug("------------没有权限-------------");
				//没有权限访问
				result(-99998,response);
				return false;
			}
			setClientId((HttpServletRequest)request, (HttpServletResponse)response);
			return true;
//		}
	}
	
	/**
	 * 校验是否有权限访问链接
	 * @author 雷杨
	 * @param url
	 * @param userid
	 * @return
	 */
	public boolean checkPower(String url, String roleids)
	{
		DBHelperSpring db = (DBHelperSpring) SpringHelper.getBean("dbHelper");
		// 先看链接是否在表中存在
		String getPowersql = SQLconfig.getSql("menu.getpower");
		String power_id = db.queryForString(getPowersql, new Object[] { url });
//		logger.debug("校验是否有权限访问链接1:" + StringHelper.getSql(getPowersql, new Object[] { url }));
		if ("".equals(power_id))
		{// 没有对应的链接信息可以直接访问
			return true;
		}
		else
		{
			String sql = SQLconfig.getSql("menu.checkPower").replace("##", roleids);
//			logger.debug("校验是否有权限访问链接2:" + StringHelper.getSql(sql, new Object[] { power_id }));
			if (db.queryForInt(sql, new Object[] { power_id }) == 0)
			{
				return false;
			}
			else
			{
				return true;
			}
		}
	}
	
	public String getIP(HttpServletRequest request)
	{
		String ipAddress = null;
		// ipAddress = this.getRequest().getRemoteAddr();
		ipAddress = request.getHeader("x-forwarded-for");
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
		{
			ipAddress = request.getHeader("Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
		{
			ipAddress = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress))
		{
			ipAddress = request.getRemoteAddr();
			if (ipAddress.equals("127.0.0.1"))
			{
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try
				{
					inet = InetAddress.getLocalHost();
				}
				catch (UnknownHostException e)
				{
					e.printStackTrace();
				}
				ipAddress = inet.getHostAddress();
			}

		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ipAddress != null && ipAddress.length() > 15)
		{ // "***.***.***.***".length() = 15
			if (ipAddress.indexOf(",") > 0)
			{
				ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
			}
		}
		return ipAddress;
	}
	
	public String getHeader(HttpServletRequest request,String name){
		try{
			return request.getHeader(name);
		}catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public void result(int state,HttpServletResponse response){
		try {
			response.setCharacterEncoding("UTF-8");
			JSONObject json = new JSONObject();
			json.put("state", state);
//			logger.debug("josn:"+json);
			response.getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
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
		logger.debug("cookies:"+cookies);
		if(cookies == null){
			return "";
		}
		for(int i=0;i<cookies.length;i++){
			logger.debug("getName():"+cookies[i].getName()+""+cookies[i].getValue());
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
		String username = getCookie(request, "username");
		Cookie cookie = new Cookie("username", username);
		cookie.setPath("/");
		cookie.setMaxAge(60*60*24);
		response.addCookie(cookie);
	}

	public void destroy() {
		config = null;
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		config = filterConfig;
	}

	public FilterConfig config;

}
